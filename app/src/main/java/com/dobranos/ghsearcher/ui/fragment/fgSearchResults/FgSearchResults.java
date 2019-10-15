package com.dobranos.ghsearcher.ui.fragment.fgSearchResults;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dobranos.ghsearcher.R;
import com.dobranos.ghsearcher.di.module.DiActMainModule;
import com.dobranos.ghsearcher.model.data.common.IServiceProvider;
import com.dobranos.ghsearcher.model.data.common.IUser;
import com.dobranos.ghsearcher.model.logic.db.DbBookmarkProvider;
import com.dobranos.ghsearcher.model.logic.db.DbServiceProvider;
import com.dobranos.ghsearcher.model.logic.gitHub.GitHubServiceProvider;
import com.dobranos.ghsearcher.ui.common.InfiniteScrollListener;
import com.dobranos.ghsearcher.ui.fragment.FgBase;
import com.dobranos.ghsearcher.utils.ViewUtil;
import com.pnikosis.materialishprogress.ProgressWheel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;
import java.util.List;

public class FgSearchResults extends FgBase
{
    @Inject DiActMainModule.Menu menu;
    @Inject DiActMainModule.Behavior behavior;

    @Inject GitHubServiceProvider networkProvider;
    @Inject DbServiceProvider storageProvider;
    @Inject DbBookmarkProvider bookmarkProvider;

    private IServiceProvider getServiceProvider() { return useStorage ? storageProvider : networkProvider; }

    private View fgView;

    private int page;
    private boolean lastPage;

    private TextView tvEmpty;

    private ProgressWheel vLoading;
    private RecyclerView recyclerView;
    private FgSearchResultsAdapter recyclerViewAdapter;
    private InfiniteScrollListener recyclerViewScroll;

    @Override
    public void onResume()
    {
        super.onResume();

        behavior.setScrollingBehavior(true);
        context.setTitle(isBookmarks ? R.string.fg_search_results_bookmarks : R.string.common_empty);
        setHasOptionsMenu(true);
        setBackArrowInsteadHamburger(isBookmarks ? true : false);

        if(isBookmarks)
            recyclerViewAdapter.invalidateBy(bookmarkProvider.getKnownUserIds());
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString("search_query", searchQuery);
        outState.putBoolean("is_bookmarks", isBookmarks);
        outState.putBoolean("use_storage", useStorage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle isState)
    {
        if (isState != null)
        {
            searchQuery = isState.getString("search_query");
            isBookmarks = isState.getBoolean("is_bookmarks");
            useStorage = isState.getBoolean("use_storage");
        }

        fgView = inflater.inflate(R.layout.fg_search_results, container, false);

        tvEmpty = fgView.findViewById(R.id.tv_empty);
        ViewUtil.toGone(tvEmpty);

        vLoading = fgView.findViewById(R.id.v_loading);

        recyclerView = fgView.findViewById(R.id.rv_items);
//        recyclerView.setHasFixedSize(true);

        LinearLayoutManager lm;
        recyclerView.setLayoutManager(lm = new GridLayoutManager(recyclerView.getContext(), getActivity().getResources().getBoolean(R.bool.layout_land) ? 3 : 2, RecyclerView.VERTICAL, false));
        recyclerView.addOnScrollListener(recyclerViewScroll = new InfiniteScrollListener(lm, new InfiniteScrollListener.OnLoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                if (recyclerViewAdapter.getItemCount() < 100)
                {
                    lastPage = true;
                    recyclerView.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            recyclerViewScroll.setLoaded();
                            recyclerViewScroll.setLoadingFinished();
                        }
                    });
                    return;
                }

                getServiceProvider().searchUsers(searchQuery, page++, 100)
                    .subscribeWith(new Observer<List<IUser>>()
                    {
                        @Override public void onSubscribe(Disposable d) { ViewUtil.toVisible(vLoading); }
                        @Override public void onComplete() { ViewUtil.toGone(vLoading); }

                        @Override
                        public void onNext(List<IUser> users)
                        {
                            recyclerViewAdapter.addData(users);
                            recyclerViewScroll.setLoaded();

                            if (users.size() < 100 || lastPage)
                                recyclerViewScroll.setLoadingFinished();
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            ViewUtil.toGone(vLoading);

                            handleError(e);
                        }
                    });
            }
        }).withThreshold(5).setLoaded());

        recyclerViewAdapter = new FgSearchResultsAdapter(getActivity(), R.layout.fg_search_results_item, getServiceProvider());
        recyclerView.setAdapter(recyclerViewAdapter);

        return fgView;
    }

    private String searchQuery;
    public FgSearchResults withQuery(String query)
    {
        this.searchQuery = query;
        return this;
    }

    private boolean isBookmarks;
    public FgSearchResults withMarkAsBookmarks()
    {
        this.isBookmarks = true;
        return this;
    }

    private boolean useStorage;
    private IServiceProvider desiredProvider;
    public FgSearchResults withServiceProvider(IServiceProvider provider)
    {
        this.desiredProvider = provider;
        return this;
    }

    @Override
    public void onCreateOptionsMenu(Menu m, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(m, inflater);

        menu.miSearch.setVisible(isBookmarks ? false : true);
        menu.miBookmarks.setVisible(isBookmarks ? false : true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(searchQuery == null)
        {
            ViewUtil.toVisible(tvEmpty);
            return;
        }

        getServiceProvider().searchUsers(searchQuery, page++, 100)
            .subscribeWith(new Observer<List<IUser>>()
            {
                Boolean empty;

                @Override public void onSubscribe(Disposable d) { behavior.showLoading(true); }
                @Override public void onComplete()
                {
                    behavior.showLoading(false);

                    if (empty == null || empty)
                        ViewUtil.toVisible(tvEmpty);
                    else
                        ViewUtil.toGone(tvEmpty);
                }

                @Override
                public void onNext(List<IUser> users)
                {
                    if(empty == null)
                        empty = users.size() == 0;

                    recyclerViewAdapter.addData(users);
                }

                @Override
                public void onError(Throwable e)
                {
                    behavior.showLoading(false);

                    handleError(e);
                }
            });
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof InjectorProvider)
        {
            ((InjectorProvider) context).inject(this);

            // todo: facepalm
            useStorage = desiredProvider == storageProvider;
        }
        else
        {
            throw new IllegalStateException("You should provide InjectorProvider");
        }
    }

    public interface InjectorProvider
    {
        void inject(FgSearchResults fragment);
    }
}