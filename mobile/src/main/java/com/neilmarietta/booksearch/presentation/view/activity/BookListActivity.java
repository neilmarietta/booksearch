package com.neilmarietta.booksearch.presentation.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.neilmarietta.booksearch.R;
import com.neilmarietta.booksearch.presentation.view.fragment.BookListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view) NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // Handle query submit SearchRecentSuggestionsProvider
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            BookListFragment fragment = (BookListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content_book_list_fragment);
            if (fragment != null)
                fragment.onSearchNewBooks(query);

            // Avoid search onConfigurationChanged
            //setIntent(intent); Do not save if it came from a SEARCH Intent
        } else {
            setIntent(intent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ((drawer != null) && drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_linkedin) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/neilmarietta")));
        } else if (id == R.id.nav_github) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/neilmarietta")));
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
