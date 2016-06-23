package com.neilmarietta.booksearch.presentation.presenter;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.neilmarietta.booksearch.contract.BookContract;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.BasePresenter;

import javax.inject.Inject;

public class BookPresenter extends BasePresenter<BookContract.View>
        implements BookContract.OnUserActionListener {

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
    public void initialize() {
        getMvpView().renderBook(mBook);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable(KEY_CURRENT_BOOK, mBook);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        getMvpView().renderBook(mBook = savedInstanceState.getParcelable(KEY_CURRENT_BOOK));
    }

    @Override
    public void onPreviewButtonClicked() {
        viewPreviewLink();
    }

    private void viewPreviewLink() {
        // Open book preview in browser
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mBook.volumeInfo().previewLink()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
