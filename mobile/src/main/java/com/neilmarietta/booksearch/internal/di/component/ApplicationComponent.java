package com.neilmarietta.booksearch.internal.di.component;

import com.neilmarietta.booksearch.internal.di.PerApplication;
import com.neilmarietta.booksearch.internal.di.module.ApplicationModule;
import com.neilmarietta.booksearch.presentation.view.fragment.BookFragment;
import com.neilmarietta.booksearch.presentation.view.fragment.BookListFragment;

import dagger.Component;

@PerApplication
@Component(dependencies = {ApiConnectionComponent.class}, modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(BookListFragment fragment);
    void inject(BookFragment fragment);
}
