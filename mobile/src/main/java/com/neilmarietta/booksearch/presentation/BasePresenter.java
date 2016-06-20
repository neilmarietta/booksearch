package com.neilmarietta.booksearch.presentation;

import android.os.Bundle;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private T mMvpView;
    private CompositeSubscription mViewSubscriptions;

    @Override
    public void attachView(T mvpView, Bundle savedInstanceState) {
        mMvpView = mvpView;
        mViewSubscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mMvpView = null;
        mViewSubscriptions.clear();
    }

    public void addViewSubscription(Subscription subscription) {
        mViewSubscriptions.add(subscription);
    }

    public void removeViewSubscription(Subscription subscription) {
        mViewSubscriptions.remove(subscription);
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