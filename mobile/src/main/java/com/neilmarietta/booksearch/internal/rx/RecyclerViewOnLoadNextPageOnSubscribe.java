package com.neilmarietta.booksearch.internal.rx;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neilmarietta.booksearch.presentation.view.listener.EndlessRecyclerViewOnScrollListener;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

final class RecyclerViewOnLoadNextPageOnSubscribe implements Observable.OnSubscribe<Void> {
    final RecyclerView recyclerView;
    final LinearLayoutManager manager;

    public RecyclerViewOnLoadNextPageOnSubscribe(RecyclerView recyclerView, LinearLayoutManager manager) {
        this.recyclerView = recyclerView;
        this.manager = manager;
    }

    @Override
    public void call(final Subscriber subscriber) {
        checkUiThread();

        final RecyclerView.OnScrollListener listener = new EndlessRecyclerViewOnScrollListener(manager) {
            @Override
            public void onLoadNextPage() {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };

        recyclerView.addOnScrollListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                recyclerView.removeOnScrollListener(listener);
            }
        });
    }
}
