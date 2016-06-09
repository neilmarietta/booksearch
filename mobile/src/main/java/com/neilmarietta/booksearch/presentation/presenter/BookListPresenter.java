package com.neilmarietta.booksearch.presentation.presenter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.neilmarietta.booksearch.contract.BookListContract;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;
import com.neilmarietta.booksearch.interactor.BookSearchUseCase;
import com.neilmarietta.booksearch.presentation.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class BookListPresenter extends BasePresenter<BookListContract.View> implements BookListContract.OnUserActionListener {

    private BookSearchUseCase mBookSearchUseCase;

    private static final String KEY_CURRENT_TEXT = "current.text";
    private static final String KEY_CURRENT_PAGE = "current.page";
    private static final String KEY_CURRENT_ITEMS_BY_PAGE = "current.items.by.page";
    private static final String KEY_CURRENT_BOOKS = "current.books";

    private String mCurrentText;
    private int mCurrentPage = 1;
    private int mCurrentItemsByPage = 20;

    @Inject
    public BookListPresenter(BookSearchUseCase bookSearchUseCase) {
        mBookSearchUseCase = bookSearchUseCase;
    }

    @Override
    public void attachView(@NonNull BookListContract.View view, Bundle savedInstanceState) {
        super.attachView(view, savedInstanceState);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
        else
            initialize();
    }

    @Override
    public void detachView() {
        super.detachView();
        mBookSearchUseCase.unsubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(KEY_CURRENT_TEXT, mCurrentText);
        bundle.putInt(KEY_CURRENT_PAGE, mCurrentPage);
        bundle.putInt(KEY_CURRENT_ITEMS_BY_PAGE, mCurrentItemsByPage);
        bundle.putParcelableArrayList(KEY_CURRENT_BOOKS, (ArrayList<Book>) getMvpView().getBooks());
        super.onSaveInstanceState(bundle);
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        mCurrentText = savedInstanceState.getString(KEY_CURRENT_TEXT);
        mCurrentPage = savedInstanceState.getInt(KEY_CURRENT_PAGE);
        mCurrentItemsByPage = savedInstanceState.getInt(KEY_CURRENT_ITEMS_BY_PAGE);
        getMvpView().addBooks(savedInstanceState.<Book>getParcelableArrayList(KEY_CURRENT_BOOKS));
    }

    public void initialize() {
        onSearchNewBooks("android");
    }

    @Override
    public void onSearchNewBooks(String text) {
        mCurrentPage = 1;
        mCurrentText = text;
        getMvpView().clearBooks();
        getMvpView().showLoading();
        searchBooks();
    }

    @Override
    public void onLoadNextBooksPage() {
        getMvpView().showLoading();
        searchBooks();
    }

    @Override
    public void onRetryButtonClicked() {
        searchBooks();
    }

    private void searchBooks() {
        mBookSearchUseCase.searchBooks(
                mCurrentText,
                mCurrentItemsByPage,
                (mCurrentPage - 1) * mCurrentItemsByPage,
                new FlickrPhotoListSubscriber());
    }

    private final class FlickrPhotoListSubscriber extends Subscriber<BookSearchResult> {

        @Override
        public void onCompleted() {
            BookListPresenter.this.getMvpView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            BookListPresenter.this.getMvpView().hideLoading();
            BookListPresenter.this.getMvpView().showError(e.getMessage());
        }

        @Override
        public void onNext(BookSearchResult bookSearchResult) {
            List<Book> books = bookSearchResult.getItems();
            if (books != null) {
                mCurrentPage++;
                BookListPresenter.this.getMvpView().addBooks(books);
            }
        }
    }
}
