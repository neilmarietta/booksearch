package com.neilmarietta.booksearch.presentation.view.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener
        implements OnLoadNextPageListener {

    private static final int VISIBLE_THRESHOLD = 5;

    private LinearLayoutManager mLayoutManager;

    private boolean mInVisibleThreshold = false;

    public EndlessRecyclerViewOnScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = mLayoutManager.getItemCount();
        if (totalItemCount < 1) return;

        int visibleItemCount = recyclerView.getChildCount();
        int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        boolean inVisibleThreshold = lastVisibleItem >= totalItemCount - VISIBLE_THRESHOLD;

        if (inVisibleThreshold) {
            if (!mInVisibleThreshold) {
                mInVisibleThreshold = true;
                onLoadNextPage();
            }
        } else {
            mInVisibleThreshold = false;
        }
    }
}
