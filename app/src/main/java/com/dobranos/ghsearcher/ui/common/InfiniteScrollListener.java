package com.dobranos.ghsearcher.ui.common;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InfiniteScrollListener extends RecyclerView.OnScrollListener
{
    private final static int VISIBLE_THRESHOLD = 2;
    private LinearLayoutManager linearLayoutManager;
    private boolean loading; // LOAD MORE Progress dialog
    private OnLoadMoreListener listener;
    private boolean pauseListening = false;

    private boolean END_OF_FEED_ADDED = false;
    private int NUM_LOAD_ITEMS = 10;

    public InfiniteScrollListener(LinearLayoutManager linearLayoutManager, OnLoadMoreListener listener)
    {
        this.linearLayoutManager = linearLayoutManager;
        this.listener = listener;
    }

    private int threshold = VISIBLE_THRESHOLD;
    public InfiniteScrollListener withThreshold(int value)
    {
        threshold = value;
        return this;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        super.onScrolled(recyclerView, dx, dy);
        if (dx == 0 && dy == 0)
            return;
        int totalItemCount = linearLayoutManager.getItemCount();
        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!loading && totalItemCount <= lastVisibleItem + threshold && totalItemCount != 0 && !END_OF_FEED_ADDED && !pauseListening)
        {
            if (listener != null)
            {
                listener.onLoadMore();
            }
            loading = true;
        }
    }

    public InfiniteScrollListener setLoaded()
    {
        loading = false;
        return this;
    }

    public interface OnLoadMoreListener
    {
        void onLoadMore();
    }

    public InfiniteScrollListener setLoadingFinished()
    {
        this.END_OF_FEED_ADDED = true;
        return this;
    }

    public void pauseScrollListener(boolean pauseListening)
    {
        this.pauseListening = pauseListening;
    }
}