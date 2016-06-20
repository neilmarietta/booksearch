package com.neilmarietta.booksearch.presentation.presenter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.neilmarietta.booksearch.contract.BookContract;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

public class BookPresenter extends BasePresenter<BookContract.View> {

    private static final String KEY_CURRENT_BOOK = "current.book";

    private Application mContext;
    private Book mBook;

    @Inject
    public BookPresenter(Application context) {
        mContext = context;
    }

    public void setBook(Book book) {
        mBook = book;
    }

    @Override
    public void attachView(@NonNull BookContract.View view, Bundle savedInstanceState) {
        super.attachView(view, savedInstanceState);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
        else
            initialize();

        addViewSubscription(onPreviewButtonClicked());
    }

    @Override
    public void detachView() {
        super.detachView();
        // nothing for now
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable(KEY_CURRENT_BOOK, mBook);
        super.onSaveInstanceState(bundle);
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        getMvpView().renderBook(mBook = savedInstanceState.getParcelable(KEY_CURRENT_BOOK));
    }

    public void initialize() {
        getMvpView().renderBook(mBook);
    }

    private Subscription onPreviewButtonClicked() {
        return getMvpView().onPreviewButtonClicked()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        viewPreviewLink();
                    }
                });
    }

    private void viewPreviewLink() {
        // Open book preview in browser
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mBook.getVolumeInfo().getPreviewLink()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
