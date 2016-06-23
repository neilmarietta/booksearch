package com.neilmarietta.booksearch.presentation;

import android.os.Bundle;

import com.neilmarietta.booksearch.interactor.UseCase;

import java.util.HashSet;
import java.util.Set;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private T mMvpView;
    private CompositeSubscription mSubscriptions;
    private Set<UseCase> mUseCases = new HashSet<>();

    @Override
    public void attachView(T mvpView, Bundle savedInstanceState) {
        mMvpView = mvpView;
        mSubscriptions = new CompositeSubscription();

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
        else
            initialize();
    }

    public void initialize() {
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void detachView() {
        mMvpView = null;
        mSubscriptions.clear();
        for (UseCase usecase : mUseCases)
            usecase.unsubscribe();
    }

    public void add(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    public void remove(Subscription subscription) {
        mSubscriptions.remove(subscription);
    }

    public void add(UseCase useCase) {
        mUseCases.add(useCase);
    }

    public void remove(UseCase useCase) {
        mUseCases.remove(useCase);
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }
}