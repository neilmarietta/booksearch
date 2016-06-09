package com.neilmarietta.booksearch.data.repository.net;

import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GoogleBooksRestApi {

    // No API Key needed for Volume list/get
    // More info : https://developers.google.com/books/docs/v1/using#PerformingSearch

    @GET("/books/v1/volumes")
    Observable<BookSearchResult> getBookSearchResult(
            @Query("q") String query,
            @Query("maxResults") int maxResults,
            @Query("startIndex") int startIndex);

    @GET("/books/v1/volumes/{id}")
    Observable<Book> getBook(@Path("id") String volumeId);
}
