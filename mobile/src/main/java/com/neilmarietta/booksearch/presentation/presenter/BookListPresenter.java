package com.neilmarietta.booksearch.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.neilmarietta.booksearch.contract.BookListContract;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;
import com.neilmarietta.booksearch.interactor.BookSearchUseCase;
import com.neilmarietta.booksearch.presentation.BasePresenter;
import com.neilmarietta.booksearch.presentation.view.activity.BookActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class BookListPresenter extends BasePresenter<BookListContract.View>
        implements BookListContract.OnUserActionListener {

    private BookSearchUseCase mBookSearchUseCase;

    private static final String KEY_CURRENT_TEXT = "current.text";
    private static final String KEY_CURRENT_PAGE = "current.page";
    private static final String KEY_CURRENT_ITEMS_BY_PAGE = "current.items.by.page";
    private static final String KEY_CURRENT_BOOKS = "current.books";

    private CharSequence mCurrentText = "android";
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

    private void initialize() {
        onSearchNewBooks(mCurrentText);
    }

    @Override
    public void detachView() {
        super.detachView();
        mBookSearchUseCase.unsubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putCharSequence(KEY_CURRENT_TEXT, mCurrentText);
        bundle.putInt(KEY_CURRENT_PAGE, mCurrentPage);
        bundle.putInt(KEY_CURRENT_ITEMS_BY_PAGE, mCurrentItemsByPage);
        bundle.putParcelableArrayList(KEY_CURRENT_BOOKS, (ArrayList<Book>) getMvpView().getBooks());
        super.onSaveInstanceState(bundle);
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        mCurrentText = savedInstanceState.getCharSequence(KEY_CURRENT_TEXT);
        mCurrentPage = savedInstanceState.getInt(KEY_CURRENT_PAGE);
        mCurrentItemsByPage = savedInstanceState.getInt(KEY_CURRENT_ITEMS_BY_PAGE);
        getMvpView().clearBooks(); // View may retain state
        getMvpView().addBooks(savedInstanceState.<Book>getParcelableArrayList(KEY_CURRENT_BOOKS));
    }

    private void setupNewSearch(CharSequence text) {
        mCurrentPage = 1;
        mCurrentText = text;
    }

    @Override
    public void onSearchNewBooks(CharSequence query) {
        setupNewSearch(query);
        getMvpView().clearBooks();
        searchBooks();
    }

    @Override
    public void onLoadNextBooksPage() {
        searchBooks();
    }

    @Override
    public void onRetryButtonClicked() {
        searchBooks();
    }

    @Override
    public void onBookClicked(Book book) {
        viewBook(book);
    }

    protected void viewBook(Book book) {
        // TODO: Move this in another class (Action Dispatcher?) and Test
        Context context = getMvpView().getContext();
        Intent intent = new Intent(context, BookActivity.class);
        intent.putExtra(BookActivity.EXTRA_BOOK, book);
        context.startActivity(intent);
    }

    private void searchBooks() {
        getMvpView().showLoading();
        mBookSearchUseCase.searchBooks(
                mCurrentText,
                mCurrentItemsByPage,
                (mCurrentPage - 1) * mCurrentItemsByPage,
                new BookSearchSubscriber());
    }

    private final class BookSearchSubscriber extends Subscriber<BookSearchResult> {

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
            List<Book> books = bookSearchResult.items();
            if (books != null) {
                mCurrentPage++;
                BookListPresenter.this.getMvpView().addBooks(books);
            }
        }
    }
}
