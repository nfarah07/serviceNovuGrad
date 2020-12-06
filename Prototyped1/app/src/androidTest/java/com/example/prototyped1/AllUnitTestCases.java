package com.example.prototyped1;

import android.app.Activity;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.example.prototyped1.CustomerActivities.CustomerMainActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class AllUnitTestCases {

    @Rule //This is where the test starts
    public ActivityTestRule<SignUpActivity> signUpActivityTestRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    private final String email = "customer@email.com";
    private final String password = "customer";
    private final String branchAddress = "34 Louis Lane";

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

    @Test
    public void testRatingABranch() throws InterruptedException {


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
        Thread.sleep(5000); // Wait for list to load

        onView(withId(R.id.listOfEmployees)).perform(longClick());
        onView(withId(R.id.branchRatingBar)).perform(click());
        onView(withId(R.id.submitRatingBtn)).perform(click());
        Thread.sleep(1000);
    }

    @Test
    public void testMakeAServiceRequestIntentOpen() throws InterruptedException {


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
        onView(withId(R.id.makeServiceRequestButton)).perform(click());
    }

    @Test
    public void testSearchForABranchIntentOpens() throws InterruptedException {


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
        onView(withId(R.id.searchForABranchButton)).perform(click());
    }

    @Test
    public void testSearchByBranchAddressIntentOpens() throws InterruptedException {


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
        onView(withId(R.id.searchForABranchButton)).perform(click());
        onView(withId(R.id.selectSearchByBranchAddress)).perform(click());
    }

    @Test
    public void testSearchByOpenHoursIntentOpens() throws InterruptedException {


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
        onView(withId(R.id.searchForABranchButton)).perform(click());
        onView(withId(R.id.selectSearchByBranchHours)).perform(click());
    }

    @Test
    public void testSearchByServicesOfferedIntentOpens() throws InterruptedException {


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
        onView(withId(R.id.searchForABranchButton)).perform(click());
        onView(withId(R.id.selectSearchByServiceOffered)).perform(click());
        onView(withId(R.id.spinnerAllServices)).perform(click());
    }

    @Test
    public void testSearchByBranchAddressTextInput() throws InterruptedException {


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
        onView(withId(R.id.searchForABranchButton)).perform(click());
        onView(withId(R.id.selectSearchByBranchAddress)).perform(click());
        onView(withId(R.id.editTextBranchAddress)).perform(typeText(branchAddress));
        onView(withId(R.id.editTextBranchAddress)).perform(pressBackUnconditionally());
    }

    @Test
    public void testBackButton() throws InterruptedException {


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
        onView(withId(R.id.searchForABranchButton)).perform(click());
        onView(withId(R.id.selectSearchByBranchAddress)).perform(click());
        onView(withId(R.id.editTextBranchAddress)).perform(pressBackUnconditionally());
        onView(withId(R.id.selectSearchByBranchAddress)).perform(pressBackUnconditionally());
    }

    @Test
    public void testMakeServiceRequestSpinners() throws InterruptedException {


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
        onView(withId(R.id.makeServiceRequestButton)).perform(click());
        onView(withId(R.id.branchServiceSpinner)).perform(click());
        Espresso.pressBackUnconditionally();
        onView(withId(R.id.branchNameSpinner)).perform(click());
        Espresso.pressBackUnconditionally();
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
