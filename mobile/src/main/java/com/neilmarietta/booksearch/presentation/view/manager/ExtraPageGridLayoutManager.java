package com.neilmarietta.booksearch.presentation.view.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * GridLayoutManager that always lays out 1 extra page of items (while smooth scrolling and otherwise too).<br>
 * <br>
 * Note: Laying out invisible elements will eventually come with performance cost.
 */
public class ExtraPageGridLayoutManager extends GridLayoutManager {

    private OrientationHelper mOrientationHelper;

    public ExtraPageGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupOrientationHelper();
    }

    public ExtraPageGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        setupOrientationHelper();
    }

    public ExtraPageGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        setupOrientationHelper();
    }

    private void setupOrientationHelper() {
        mOrientationHelper = OrientationHelper.createOrientationHelper(this, getOrientation());
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        // Lays out 1 extra page of items while smooth scrolling and otherwise too.
        return mOrientationHelper.getTotalSpace();
    }
}
