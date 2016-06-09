package com.neilmarietta.booksearch.data.provider;

import android.content.SearchRecentSuggestionsProvider;

public class BookSearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "com.neilmarietta.booksearch.suggestion.authority";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public BookSearchRecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
