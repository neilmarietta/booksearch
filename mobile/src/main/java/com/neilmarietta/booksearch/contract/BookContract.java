package com.neilmarietta.booksearch.contract;

import android.support.annotation.NonNull;

import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.MvpView;

import rx.Observable;

public class BookContract {

    public interface View extends MvpView {

        @NonNull Observable<?> onPreviewButtonClicked();

        void renderBook(Book book);
    }
}
