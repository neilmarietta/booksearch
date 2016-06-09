package com.neilmarietta.booksearch.interactor;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class UseCase {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private rx.Scheduler mSubscribeOn = null;
    private rx.Scheduler mObserveOn = null;

    @SuppressWarnings("unchecked")
    public final void execute(Observable observable, Subscriber subscriber) {
        checkSchedulers();
        mCompositeSubscription
                .add(observable
                        .subscribeOn(mSubscribeOn)
                        .observeOn(mObserveOn)
                        .subscribe(subscriber));
    }

    private void checkSchedulers() {
        // Add default schedulers
        if (mSubscribeOn == null)
            mSubscribeOn = Schedulers.newThread();
        if (mObserveOn == null)
            mObserveOn = AndroidSchedulers.mainThread();
    }

    public void setSubscribeOn(rx.Scheduler scheduler) {
        mSubscribeOn = scheduler;
    }

    public void setObserveOn(rx.Scheduler scheduler) {
        mObserveOn = scheduler;
    }

    public void unsubscribe() {
        mCompositeSubscription.clear();
    }
}
