package com.neilmarietta.booksearch.contract;

import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.MvpLoadView;

import java.util.List;

public class BookListContract {

    public interface View extends MvpLoadView {

        void clearBooks();

        void searchBooks(String text);

        void addBooks(List<Book> books);

        List<Book> getBooks();
    }

    public interface OnUserActionListener {

        void onSearchNewBooks(String text);

        void onLoadNextBooksPage();

        void onRetryButtonClicked();
    }
}
