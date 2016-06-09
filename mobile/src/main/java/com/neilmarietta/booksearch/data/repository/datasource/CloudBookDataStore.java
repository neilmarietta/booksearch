package com.neilmarietta.booksearch.data.repository.datasource;

import com.neilmarietta.booksearch.data.repository.net.GoogleBooksRestApi;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;

import retrofit2.Retrofit;
import rx.Observable;

public class CloudBookDataStore implements BookDataStore {

    private final GoogleBooksRestApi mRestService;

    public CloudBookDataStore(Retrofit adapter) {
        mRestService = adapter.create(GoogleBooksRestApi.class);
    }

    @Override
    public Observable<BookSearchResult> getBookSearchResult(String text, int maxResults, int startIndex) {
        return mRestService.getBookSearchResult(text, maxResults, startIndex);
    }

    @Override
    public Observable<Book> getBook(String volumeId) {
        return mRestService.getBook(volumeId);
    }
}
