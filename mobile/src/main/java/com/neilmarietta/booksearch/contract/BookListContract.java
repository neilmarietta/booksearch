package com.neilmarietta.booksearch.contract;

import android.support.annotation.NonNull;

import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.MvpLoadView;

import java.util.List;

import rx.Observable;

public class BookListContract {

    public interface View extends MvpLoadView {

        @NonNull Observable<CharSequence> onSearchNewBooks();

        @NonNull Observable<Void> onLoadNextBooksPage();

        @NonNull Observable<Void> onRetryButtonClicked();

        @NonNull Observable<Book> onBookClicked();

        void clearBooks();

        void addBooks(List<Book> books);

        List<Book> getBooks();
    }
}
