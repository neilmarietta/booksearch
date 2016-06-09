package com.neilmarietta.booksearch.data.repository;

import com.neilmarietta.booksearch.data.repository.datasource.BookDataStore;
import com.neilmarietta.booksearch.data.repository.datasource.BookDataStoreFactory;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;

import javax.inject.Inject;

import rx.Observable;

public class BookRepository implements BookDataStore {

    private final BookDataStoreFactory mBookDataStoreFactory;

    @Inject
    public BookRepository(BookDataStoreFactory bookDataStoreFactory) {
        mBookDataStoreFactory = bookDataStoreFactory;
    }

    @Override
    public Observable<BookSearchResult> getBookSearchResult(String query, int maxResults, int startIndex) {
        return mBookDataStoreFactory.create().getBookSearchResult(query, maxResults, startIndex);
    }

    @Override
    public Observable<Book> getBook(String volumeId) {
        return mBookDataStoreFactory.create(volumeId).getBook(volumeId);
    }
}
