package com.example.prototyped1;

import android.app.Activity;
import android.util.Log;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.example.prototyped1.CustomerActivities.CustomerMainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;

public class UnitTestCase1 {

    @Rule //This is where the test starts
    public ActivityTestRule<SignUpActivity> signUpActivityTestRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    private String email = "customer@email.com";
    private String password = "customer";

    Activity currentActivity = null;

    @Test
    public void testRateABranchIntentOpens() throws InterruptedException {


        onView(withId(R.id.AlreadyUser)).perform(click());
        onView(withId(R.id.Email)).perform(typeText(email));
        onView(withId(R.id.Password)).perform(typeText(password));
        onView(withId(R.id.Login)).perform(pressBackUnconditionally());
        onView(withId(R.id.Login)).perform(click());

        getActivityInstance();

        while(!currentActivity.getClass().getName().equals(CustomerMainActivity.class.getName())){
            Thread.sleep(1000);
            getActivityInstance();
        }
        onView(withId(R.id.btnRateBranch)).perform(click());
    }

    /**
     * A method that gets the current activity.
     * Used from https://testyour.app/blog/get-current-activity
     * @return
     */
    public Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity activity: resumedActivities){
                    Log.d("Your current activity: ", activity.getClass().getName());
                    currentActivity = activity;
                    break;
                }
            }
        });

        return currentActivity;
    }

}
