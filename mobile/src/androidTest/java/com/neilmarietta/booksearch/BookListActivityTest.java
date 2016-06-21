package com.neilmarietta.booksearch;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.neilmarietta.booksearch.assertion.RecyclerViewItemCountAssertion;
import com.neilmarietta.booksearch.internal.di.BookSearchTestModule;
import com.neilmarietta.booksearch.presentation.view.activity.BookListActivity;
import com.neilmarietta.booksearch.util.ActivityTestRuleUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookListActivityTest {

    @Rule
    public ActivityTestRule<BookListActivity> mActivityRule = new ActivityTestRule<>(BookListActivity.class);

    private static final int PAGE_ITEMS_COUNT = BookSearchTestModule.MOCKED_BOOK_RESULT_ITEM_COUNT;

    @Test
    public void activityReCreated() {
        onView(withId(R.id.rv_books))
                .check(new RecyclerViewItemCountAssertion(PAGE_ITEMS_COUNT));

        onView(withId(R.id.rv_books)).perform(RecyclerViewActions.scrollToPosition(PAGE_ITEMS_COUNT - 1))
                .check(new RecyclerViewItemCountAssertion(PAGE_ITEMS_COUNT * 2));

        ActivityTestRuleUtil.recreate(mActivityRule);

        onView(withId(R.id.rv_books))
                .check(new RecyclerViewItemCountAssertion(PAGE_ITEMS_COUNT * 2));

        ActivityTestRuleUtil.rotateScreen(mActivityRule);

        onView(withId(R.id.rv_books))
                .check(new RecyclerViewItemCountAssertion(PAGE_ITEMS_COUNT * 2));
    }

    @Test
    public void openBook() {
        onView(withId(R.id.rv_books))
                .check(new RecyclerViewItemCountAssertion(PAGE_ITEMS_COUNT));

        onView(withId(R.id.rv_books)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_description)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_cover)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_preview)).check(matches(isDisplayed()));
    }
}
