package com.neilmarietta.booksearch.interactor;

import com.neilmarietta.booksearch.data.repository.BookRepository;
import com.neilmarietta.booksearch.entity.BookSearchResult;

import rx.Observable;
import rx.Subscriber;

public class BookSearchUseCase extends UseCase {

    private final BookRepository mBookRepository;

    public BookSearchUseCase(BookRepository photoSearchResultRepository) {
        mBookRepository = photoSearchResultRepository;
    }

    public void searchBooks(String text, int maxResults, int startIndex, Subscriber subscriber) {
        execute(searchBooks(text, maxResults, startIndex), subscriber);
    }

    private Observable<BookSearchResult> searchBooks(String text, int maxResults, int startIndex) {
        return mBookRepository.getBookSearchResult(text, maxResults, startIndex);
    }
}
