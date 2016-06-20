package com.neilmarietta.booksearch;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.neilmarietta.booksearch.assertion.RecyclerViewItemCountAssertion;
import com.neilmarietta.booksearch.presentation.view.activity.BookListActivity;
import com.neilmarietta.booksearch.util.ActivityTestRuleUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookListActivityTest {

    @Rule
    public ActivityTestRule<BookListActivity> mActivityRule = new ActivityTestRule<>(BookListActivity.class);

    @Test
    public void activityReCreated() {
        onView(withId(R.id.rv_books))
                .check(new RecyclerViewItemCountAssertion(20));

        onView(withId(R.id.rv_books)).perform(RecyclerViewActions.scrollToPosition(19))
                .check(new RecyclerViewItemCountAssertion(40));

        ActivityTestRuleUtil.recreate(mActivityRule);

        onView(withId(R.id.rv_books))
                .check(new RecyclerViewItemCountAssertion(40));

        ActivityTestRuleUtil.rotateScreen(mActivityRule);

        onView(withId(R.id.rv_books))
                .check(new RecyclerViewItemCountAssertion(40));
    }
}
