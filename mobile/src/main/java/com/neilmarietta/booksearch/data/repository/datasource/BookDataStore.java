package com.neilmarietta.booksearch.data.repository.datasource;

import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;

import rx.Observable;

public interface BookDataStore {

    Observable<BookSearchResult> getBookSearchResult(String text, int maxResults, int startIndex);

    Observable<Book> getBook(String volumeId);
}
