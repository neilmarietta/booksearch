package com.neilmarietta.booksearch.presentation.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.neilmarietta.booksearch.BookSearchApplication;
import com.neilmarietta.booksearch.R;
import com.neilmarietta.booksearch.contract.BookListContract;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.presenter.BookListPresenter;
import com.neilmarietta.booksearch.presentation.view.adapter.BookAdapter;
import com.neilmarietta.booksearch.presentation.view.listener.EndlessRecyclerViewOnScrollListener;
import com.neilmarietta.booksearch.presentation.view.manager.ExtraPageGridLayoutManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookListFragment extends Fragment implements BookListContract.View {

    @Inject BookListPresenter mListPresenter;

    @Bind(R.id.rv_books) RecyclerView mBookRecycledView;
    @Bind(R.id.rl_progress) RelativeLayout mProgressRelativeLayout;

    private BookAdapter mBookAdapter;
    private ExtraPageGridLayoutManager mGridLayoutManager;

    private MenuItem mSearchItem;
    private SearchView mSearchView;
    private Snackbar mCurrentSnackBar;

    public BookListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        BookSearchApplication.from(getContext()).getApplicationComponent().inject(this);

        mBookAdapter = new BookAdapter(getContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save Presenter state (will be restored onViewCreated -> attachView)
        mListPresenter.onSaveInstanceState(outState);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        setupAdapter();
        return view;
    }

    private void setupRecyclerView() {
        mGridLayoutManager = new ExtraPageGridLayoutManager(getContext(), 2);
        mBookRecycledView.setLayoutManager(mGridLayoutManager);
        mBookRecycledView.setAdapter(mBookAdapter);
        mBookRecycledView.setHasFixedSize(true);
        mBookRecycledView.addOnScrollListener(new EndlessRecyclerViewOnScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadNextPage() {
                mListPresenter.onLoadNextBooksPage();
            }
        });
    }

    private void setupAdapter() {
        mBookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Book book) {
                mListPresenter.onBookClicked(book);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListPresenter.attachView(this, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        mListPresenter.detachView();
        super.onDestroy();
    }

    /**
     * Entry point to trigger a new search.
     */
    public void onSearchNewBooks(CharSequence query) {
        mListPresenter.onSearchNewBooks(query);
        MenuItemCompat.collapseActionView(mSearchItem);
    }

    @Override
    public void clearBooks() {
        mBookAdapter.clear();
    }

    @Override
    public List<Book> getBooks() {
        return mBookAdapter.getBooksCopy();
    }

    @Override
    public void addBooks(@NonNull List<Book> books) {
        mBookAdapter.addBooks(books);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_book_list_menu, menu);
        setupSearchView(menu.findItem(R.id.menu_search));
    }

    private void setupSearchView(final MenuItem searchItem) {
        mSearchItem = searchItem;
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null)
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            mSearchView.setIconifiedByDefault(false);
            // Handle query submit from SearchView input method
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    onSearchNewBooks(query);
                    return true;
                }
            });
        }
    }

    @Override
    public void showLoading() {
        if (mCurrentSnackBar != null) mCurrentSnackBar.dismiss();
        mProgressRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mCurrentSnackBar != null) mCurrentSnackBar.dismiss();
        mProgressRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        mCurrentSnackBar = Snackbar.make(mBookRecycledView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListPresenter.onRetryButtonClicked();
                    }
                });
        mCurrentSnackBar.show();
    }
}
