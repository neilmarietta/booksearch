package com.neilmarietta.booksearch.contract;

import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.MvpLoadView;

import java.util.List;

public class BookListContract {

    public interface OnUserActionListener {

        void onSearchNewBooks(CharSequence query);

        void onLoadNextBooksPage();

        void onRetryButtonClicked();

        void onBookClicked(Book book);
    }

    public interface View extends MvpLoadView {

        void clearBooks();

        void addBooks(List<Book> books);

        List<Book> getBooks();
    }
}
