package com.neilmarietta.booksearch.internal.di.module;

import android.app.Application;
import android.provider.SearchRecentSuggestions;

import com.neilmarietta.booksearch.data.provider.BookSearchRecentSuggestionsProvider;
import com.neilmarietta.booksearch.data.repository.BookRepository;
import com.neilmarietta.booksearch.interactor.BookSearchUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BookSearchModule {

    @Provides
    @Singleton
    SearchRecentSuggestions provideSearchRecentSuggestions(Application application) {
        return new SearchRecentSuggestions(application,
                BookSearchRecentSuggestionsProvider.AUTHORITY, BookSearchRecentSuggestionsProvider.MODE);
    }

    @Provides
    @Singleton
    public BookSearchUseCase provideBookSearchUseCase(BookRepository bookRepository, SearchRecentSuggestions searchRecentSuggestions) {
        return new BookSearchUseCase(bookRepository, searchRecentSuggestions);
    }
}