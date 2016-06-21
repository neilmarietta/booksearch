package com.neilmarietta.booksearch.internal.di.component;

import com.neilmarietta.booksearch.internal.di.module.ApiConnectionModule;
import com.neilmarietta.booksearch.internal.di.module.ApplicationModule;
import com.neilmarietta.booksearch.internal.di.module.BookSearchModule;
import com.neilmarietta.booksearch.presentation.view.fragment.BookFragment;
import com.neilmarietta.booksearch.presentation.view.fragment.BookListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiConnectionModule.class,
        BookSearchModule.class
})
public interface ApplicationComponent {
    void inject(BookListFragment fragment);
    void inject(BookFragment fragment);
}
