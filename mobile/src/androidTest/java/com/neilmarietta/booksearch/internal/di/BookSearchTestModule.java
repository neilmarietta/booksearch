package com.neilmarietta.booksearch.internal.di;

import android.provider.SearchRecentSuggestions;

import com.neilmarietta.booksearch.data.repository.BookRepository;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;
import com.neilmarietta.booksearch.entity.ImageLinks;
import com.neilmarietta.booksearch.entity.VolumeInfo;
import com.neilmarietta.booksearch.interactor.BookSearchUseCase;
import com.neilmarietta.booksearch.internal.di.module.BookSearchModule;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookSearchTestModule extends BookSearchModule {

    public static final int MOCKED_BOOK_RESULT_ITEM_COUNT = 20;

    public static BookSearchResult getMockedBookSearchResult() {
        VolumeInfo volumeInfo = VolumeInfo.create(
                "Professional Android 4 Application Development",
                "Provides information on using Android 3 to build and enhance mobile applications, covering such topics as creating user interfaces, using intents, databases, creating and controlling services, creating app widgets, playing audio and video, telphony, and using sensors. Original.",
                817, 3.5f, 22,
                ImageLinks.create(
                        "http://books.google.ch/books/content?id=bmJIl_wPgQsC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                        "http://books.google.ch/books/content?id=bmJIl_wPgQsC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                        null, null, null, null),
                "http://books.google.ch/books?id=bmJIl_wPgQsC&printsec=frontcover&dq=android&hl=&cd=54&source=gbs_api");

        List<Book> items = new ArrayList<>();
        for (int i = 0; i < MOCKED_BOOK_RESULT_ITEM_COUNT; i++)
            items.add(Book.create(
                    "books#volume", "bmJIl_wPgQsC", "fRhB7XyRdXU",
                    "https://www.googleapis.com/books/v1/volumes/bmJIl_wPgQsC", volumeInfo));

        return BookSearchResult.create("books#volumes", 200, items);
    }

    public static BookRepository provideMockedBookRepository() {
        BookRepository bookRepository = mock(BookRepository.class);

        when(bookRepository.getBookSearchResult(anyString(), anyInt(), anyInt()))
                .thenReturn(Observable.just(getMockedBookSearchResult()));

        return bookRepository;
    }

    @Override
    public BookSearchUseCase provideBookSearchUseCase(BookRepository bookRepository, SearchRecentSuggestions searchRecentSuggestions) {
        return new BookSearchUseCase(provideMockedBookRepository(), searchRecentSuggestions);
    }
}