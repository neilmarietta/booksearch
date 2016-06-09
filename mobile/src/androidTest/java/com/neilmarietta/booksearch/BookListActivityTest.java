package com.neilmarietta.booksearch;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.neilmarietta.booksearch.presentation.view.activity.BookListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookListActivityTest {

    @Rule
    public ActivityTestRule<BookListActivity> mActivityRule = new ActivityTestRule<>(BookListActivity.class);

    @Test
    public void anyTest() {
    }
}
