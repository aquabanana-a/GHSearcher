package com.dobranos.ghsearcher.model.logic.db;

import com.dobranos.ghsearcher.di.Injector;
import com.dobranos.ghsearcher.model.data.common.IRepository;
import com.dobranos.ghsearcher.model.data.common.IServiceProvider;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.data.db.Db;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DbServiceProvider implements IServiceProvider
{
    @Inject Db database;

    public DbServiceProvider()
    {
        Injector.getSingletonComponent()
            .inject(this);
    }

    public Observable<List<IUser>> searchUsers(final String query, final long page, final long pageSize)
    {
        return database.getUserDao().getAllPaged(page * pageSize, (page + 1) * pageSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toObservable()
            .map(users ->
            {
                List<IUser> ret = new ArrayList<>();
                for (IUser u : users)
                    ret.add(u);
                return ret;
            });
    }

    public Observable<IUser> getUser(final String login)
    {
        return database.getUserDao().getByLogin(login)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toObservable()
            .map(user -> user);
    }

    public Observable<List<IRepository>> getUserRepositories(final String login)
    {
        return database.getRepositoryDao().getByUserLogin(login)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toObservable()
            .map(repos ->
            {
                List<IRepository> ret = new ArrayList<>();
                for (IRepository r : repos)
                    ret.add(r);
                return ret;
            });
    }
}