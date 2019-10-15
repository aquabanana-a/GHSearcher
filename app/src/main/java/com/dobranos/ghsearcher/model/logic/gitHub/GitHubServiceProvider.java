package com.dobranos.ghsearcher.model.logic.gitHub;

import com.dobranos.ghsearcher.di.Injector;
import com.dobranos.ghsearcher.model.data.common.IRepository;
import com.dobranos.ghsearcher.model.data.common.IServiceProvider;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.data.gitHub.GitHubRepository;
import com.dobranos.ghsearcher.model.logic.gitHub.api.GitHubRepositoriesApi;
import com.dobranos.ghsearcher.model.logic.gitHub.api.GitHubSearchApi;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GitHubServiceProvider implements IServiceProvider
{
    @Inject GitHubService api;

    public GitHubServiceProvider()
    {
        Injector.getSingletonComponent()
            .inject(this);
    }

    private HashMap<String, List<IUser>> searchMemCache = new HashMap<>();
    private Observable<List<IUser>> getSearchMemCache(String query, long page, long pageSize)
    {
        return Observable.create(subscriber ->
        {
            List<IUser> us = searchMemCache.get(query);
            int idxFrom = (int)(page * pageSize);
            int idxTo = (int)((page + 1) * pageSize);

            if (us != null && us.size() >= idxTo)
                subscriber.onNext(us.subList(idxFrom, idxTo));
            subscriber.onComplete();
        });
    }

    public Observable<List<IUser>> searchUsers(final String query, final long page, final long pageSize)
    {
        return Observable.concat(getSearchMemCache(query, page, pageSize), searchUsersImpl(query, page, pageSize))
            .filter(u -> u != null && u.size() != 0)
            .take(1);
    }

    public Observable<List<IUser>> searchUsersImpl(final String query, final long page, final long pageSize)
    {
        return api.getSearchApi()
            .searchUsers(query, GitHubSearchApi.SORT_DEFAULT, GitHubSearchApi.ORDER_DEFAULT, page, pageSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext(response ->
            {
                List<IUser> cache = searchMemCache.get(query);
                if (cache == null)
                    searchMemCache.put(query, cache = new ArrayList<>());
                cache.addAll(response.getItems());
            })
            .map(response ->
            {
                List<IUser> ret = new ArrayList<>();
                for (IUser u : response.getItems())
                    ret.add(u);
                return ret;
            });
    }

    private HashMap<String, IUser> userMemCache = new HashMap<>();
    private Observable<IUser> getUserMemCache(String login)
    {
        return Observable.create(subscriber ->
        {
            IUser u = userMemCache.get(login);
            if (u != null)
                subscriber.onNext(u);
            subscriber.onComplete();
        });
    }

    public Observable<IUser> getUser(final String login)
    {
        return Observable.concat(getUserMemCache(login), getUserImpl(login))
            .filter(u -> u != null)
            .take(1);
    }

    private Observable<IUser> getUserImpl(final String login)
    {
        return api.getUsersApi()
            .getUser(login)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext(u ->
            {
                if (!userMemCache.containsKey(login))
                    userMemCache.put(login, u);
            })
            .map(user -> user);
    }

    private HashMap<String, Pager> pagerRepos = new HashMap<>();
    private class Pager
    {
        public long page;
        public boolean lastPage;

        public Pager clear() { page = 0; lastPage = false; return this; }
    }

    private HashMap<String, List<IRepository>> repoMemCache = new HashMap<>();

    public Observable<List<IRepository>> getUserRepositories(final String login)
    {
        List<IRepository> cache = repoMemCache.get(login);
        if(cache != null && cache.size() > 0)
            return Observable.just(cache);

        return getUserRepositoriesImpl(login);
    }

    private Observable<List<IRepository>> getUserRepositoriesImpl(final String login)
    {
        Pager p = pagerRepos.get(login);
        if (p == null)
            pagerRepos.put(login, p = new Pager());

        final List<IRepository> cacheBuffer = new ArrayList<>();
        final Pager pager = p;

        return api.getRepositoriesApi()
            .getUserRepositories(login, GitHubRepositoriesApi.TYPE_DEFAULT, GitHubRepositoriesApi.SORT_UPDATED, GitHubRepositoriesApi.DIRECTION_DESC, pager.page, GitHubRepositoriesApi.PAGE_SIZE_MAX)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext(repos -> cacheBuffer.addAll(repos))
            .concatMap((Function<List<GitHubRepository>, ObservableSource<List<IRepository>>>) repos ->
            {
                List<IRepository> ret = new ArrayList<>();
                for (IRepository r : repos)
                    ret.add(r);

                if(ret.size() < GitHubRepositoriesApi.PAGE_SIZE_MAX)
                {
                    pager.clear();

                    List<IRepository> cache = repoMemCache.get(login);
                    if (cache == null)
                        repoMemCache.put(login, cache = new ArrayList<>());
                    cache.addAll(cacheBuffer);

                    return Observable.just(ret);
                }
                else
                    pager.page++;

                return Observable.just(ret)
                    .concatWith(getUserRepositoriesImpl(login));
            });
    }
}