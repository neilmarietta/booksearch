package com.neilmarietta.booksearch;

import com.neilmarietta.booksearch.internal.di.BookSearchTestModule;
import com.neilmarietta.booksearch.internal.di.component.DaggerApplicationComponent;

public class BookSearchTestApplication extends BookSearchApplication {

    @Override
    public DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .bookSearchModule(new BookSearchTestModule());
    }
}
