package com.neilmarietta.booksearch.internal.di.module;

import android.app.Application;
import android.provider.SearchRecentSuggestions;

import com.neilmarietta.booksearch.data.provider.BookSearchRecentSuggestionsProvider;
import com.neilmarietta.booksearch.data.repository.BookRepository;
import com.neilmarietta.booksearch.interactor.BookSearchUseCase;
import com.neilmarietta.booksearch.internal.di.PerApplication;

import dagger.Module;
import dagger.Provides;

/**
 * A module for Android-specific dependencies which require an {@link android.app.Application} to create.
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @PerApplication
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @PerApplication
    SearchRecentSuggestions provideSearchRecentSuggestions() {
        return new SearchRecentSuggestions(mApplication,
                BookSearchRecentSuggestionsProvider.AUTHORITY, BookSearchRecentSuggestionsProvider.MODE);
    }

    @Provides
    @PerApplication
    BookSearchUseCase provideBookSearchUseCase(BookRepository bookRepository, SearchRecentSuggestions searchRecentSuggestions) {
        return new BookSearchUseCase(bookRepository, searchRecentSuggestions);
    }
}