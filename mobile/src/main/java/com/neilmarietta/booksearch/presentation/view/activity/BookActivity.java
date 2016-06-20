package com.neilmarietta.booksearch.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neilmarietta.booksearch.R;
import com.neilmarietta.booksearch.entity.Book;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK = "book";

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        // Show and handle Back arrow on Toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // Set Book title on CollapsingToolbar
        Book book = getIntent().getExtras().getParcelable(EXTRA_BOOK);
        if (book != null && book.volumeInfo() != null)
            mCollapsingToolbarLayout.setTitle(book.volumeInfo().title());
    }
}
