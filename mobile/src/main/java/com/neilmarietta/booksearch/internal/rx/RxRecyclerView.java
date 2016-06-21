package com.neilmarietta.booksearch.internal.rx;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rx.Observable;

import static com.jakewharton.rxbinding.internal.Preconditions.checkNotNull;

public class RxRecyclerView {

    @CheckResult
    @NonNull
    public static Observable<Void> loadNextPage(@NonNull RecyclerView view, @NonNull LinearLayoutManager manager) {
        checkNotNull(view, "view == null");
        return Observable.create(new RecyclerViewOnLoadNextPageOnSubscribe(view, manager));
    }
}
