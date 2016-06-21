package com.neilmarietta.booksearch.interactor;

import android.provider.SearchRecentSuggestions;

import com.neilmarietta.booksearch.data.repository.BookRepository;
import com.neilmarietta.booksearch.entity.BookSearchResult;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class BookSearchUseCase extends UseCase {

    private final BookRepository mBookRepository;
    private final SearchRecentSuggestions mSearchRecentSuggestions;

    public BookSearchUseCase(BookRepository photoSearchResultRepository,
                             SearchRecentSuggestions searchRecentSuggestions) {
        mBookRepository = photoSearchResultRepository;
        mSearchRecentSuggestions = searchRecentSuggestions;
    }

    public void searchBooks(CharSequence text, int maxResults, int startIndex, Subscriber subscriber) {
        execute(searchBooks(text, maxResults, startIndex), subscriber);
    }

    private Observable<BookSearchResult> searchBooks(final CharSequence text, int maxResults, int startIndex) {
        return mBookRepository.getBookSearchResult(String.valueOf(text), maxResults, startIndex)
                .doOnNext(new Action1<BookSearchResult>() {
                    @Override
                    public void call(BookSearchResult bookSearchResult) {
                        mSearchRecentSuggestions.saveRecentQuery(String.valueOf(text), null);
                    }
                });
    }
}
