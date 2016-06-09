package com.neilmarietta.booksearch.presentation;

import android.os.Bundle;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView, Bundle savedInstanceState) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
    }
}