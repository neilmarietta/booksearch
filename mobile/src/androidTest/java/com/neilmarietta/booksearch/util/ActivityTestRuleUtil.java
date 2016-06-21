package com.neilmarietta.booksearch.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

public class ActivityTestRuleUtil {

    public static void recreate(final ActivityTestRule activityTestRule) {
        try {
            activityTestRule.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityTestRule.getActivity().recreate();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void rotateScreen(ActivityTestRule activityTestRule) {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityTestRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
