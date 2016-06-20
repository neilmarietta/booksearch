package com.neilmarietta.booksearch.contract;

import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.MvpView;

public class BookContract {

    public interface OnUserActionListener {

        void onPreviewButtonClicked();
    }

    public interface View extends MvpView {

        void renderBook(Book book);
    }
}
