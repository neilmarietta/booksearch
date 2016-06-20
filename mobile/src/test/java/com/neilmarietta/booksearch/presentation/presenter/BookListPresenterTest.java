package com.neilmarietta.booksearch.presentation.presenter;

import android.provider.SearchRecentSuggestions;

import com.neilmarietta.booksearch.contract.BookListContract;
import com.neilmarietta.booksearch.data.repository.BookRepository;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.BookSearchResult;
import com.neilmarietta.booksearch.interactor.BookSearchUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BookListPresenterTest {

    private BookSearchUseCase mBookSearchUseCase;
    private BookListPresenter mBookListPresenter;

    @Mock BookRepository mBookRepository;
    @Mock SearchRecentSuggestions mSearchRecentSuggestions;
    @Mock BookListContract.View mBookListView;

    private PublishSubject<CharSequence> mOnSearchNewBooks;
    private PublishSubject<Void> mOnLoadNextBooksPage;
    private PublishSubject<Void> mOnRetryButtonClicked;
    private PublishSubject<Book> mBookClicked;

    @Before
    public void setup() {
        initMocks(this);

        prepareMockViewObservables();

        mBookSearchUseCase = new BookSearchUseCase(mBookRepository, mSearchRecentSuggestions);
        mBookSearchUseCase.setSubscribeOn(Schedulers.immediate());
        mBookSearchUseCase.setObserveOn(Schedulers.immediate());

        mBookListPresenter = new BookListPresenter(mBookSearchUseCase);
    }

    // TODO: Avoid mocking View Observables that way
    // If this method forgot to mock a View Observable, the entire test will fail
    private void prepareMockViewObservables() {
        mOnSearchNewBooks = PublishSubject.create();
        when(mBookListView.onSearchNewBooks()).thenReturn(mOnSearchNewBooks);

        mOnLoadNextBooksPage = PublishSubject.create();
        when(mBookListView.onLoadNextBooksPage()).thenReturn(mOnLoadNextBooksPage);

        mOnRetryButtonClicked = PublishSubject.create();
        when(mBookListView.onRetryButtonClicked()).thenReturn(mOnRetryButtonClicked);

        mBookClicked = PublishSubject.create();
        when(mBookListView.onBookClicked()).thenReturn(mBookClicked);
    }

    private BookSearchResult prepareMockResult() {
        BookSearchResult result = new BookSearchResult();
        result.setKind("books#volumes");
        List<Book> items = new ArrayList<>();
        items.add(new Book());
        items.add(new Book());
        items.add(new Book());
        result.setItems(items);
        result.setTotalItems(items.size());

        when(mBookRepository.getBookSearchResult(anyString(), anyInt(), anyInt()))
                .thenReturn(Observable.just(result));

        return result;
    }

    private void prepareMockError() {
        when(mBookRepository.getBookSearchResult(anyString(), anyInt(), anyInt()))
                .thenReturn(Observable.<BookSearchResult>error(new Throwable("error")));
    }

    @After
    public void tearDown() {
        mBookSearchUseCase.unsubscribe();
    }

    @Test
    public void initialSearch() {
        BookSearchResult result = prepareMockResult();

        mBookListPresenter.attachView(mBookListView, null);

        verify(mBookListView).showLoading();
        verify(mBookListView).hideLoading();
        verify(mBookListView).addBooks(result.getItems());

        mBookListPresenter.detachView();
    }

    @Test
    public void newSearch() {
        BookSearchResult result = prepareMockResult();

        mBookListPresenter.attachView(mBookListView, null);

        mOnSearchNewBooks.onNext("any");

        verify(mBookListView, times(2)).clearBooks();
        verify(mBookListView, times(2)).showLoading();
        verify(mBookListView, times(2)).hideLoading();
        verify(mBookListView, times(2)).addBooks(result.getItems());

        mBookListPresenter.detachView();
    }

    @Test
    public void nextPage() {
        BookSearchResult result = prepareMockResult();

        mBookListPresenter.attachView(mBookListView, null);

        mOnLoadNextBooksPage.onNext(null);

        verify(mBookListView, times(2)).showLoading();
        verify(mBookListView, times(2)).hideLoading();
        verify(mBookListView, times(2)).addBooks(result.getItems());

        mBookListPresenter.detachView();
    }

    @Test
    public void retryLoad() {
        prepareMockError();

        mBookListPresenter.attachView(mBookListView, null);

        verify(mBookListView).showLoading();
        verify(mBookListView).hideLoading();
        verify(mBookListView).showError("error");

        BookSearchResult result = prepareMockResult();

        mOnRetryButtonClicked.onNext(null);

        verify(mBookListView, times(2)).showLoading();
        verify(mBookListView, times(2)).hideLoading();
        verify(mBookListView).addBooks(result.getItems());

        mBookListPresenter.detachView();
    }

    @Test
    public void viewBook() {
        BookSearchResult result = prepareMockResult();
        Book book =  new Book();

        mBookListPresenter.attachView(mBookListView, null);
        // TODO: Test this call
        //mBookClicked.onNext(book);
        //verify(mBookListPresenter).viewBook(book);

        mBookListPresenter.detachView();
    }
}
