package com.neilmarietta.booksearch.internal.di.module;

import com.neilmarietta.booksearch.data.repository.BookRepository;
import com.neilmarietta.booksearch.interactor.BookSearchUseCase;
import com.neilmarietta.booksearch.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class BookSearchModule {

    @Provides
    @PerActivity
    BookSearchUseCase provideBookSearchUseCase(BookRepository bookRepository) {
        return new BookSearchUseCase(bookRepository);
    }
}
