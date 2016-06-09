package com.neilmarietta.booksearch.internal.di.component;

import com.neilmarietta.booksearch.internal.di.PerActivity;
import com.neilmarietta.booksearch.internal.di.module.BookSearchModule;
import com.neilmarietta.booksearch.presentation.view.fragment.BookListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = {ApiConnectionComponent.class}, modules = {BookSearchModule.class})
public interface BookSearchComponent {
    void inject(BookListFragment fragment);
}
