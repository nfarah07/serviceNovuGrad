package com.example.prototyped1;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

public class SignUpActivityTest3 {

    @Rule
    public ActivityTestRule<SignUpActivity> signUpActivityTestRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    private String firstName = "Dave";
    private String lastName = "Jones";
    private String email = "dave@email.com";
    private String password = "SafePassword";

    @Before
    public void setUp() {
    }

    @Test
    public void checkFieldsExist() throws InterruptedException {
        onView(withId(R.id.FirstName)).perform(typeText(firstName));
        onView(withId(R.id.LastName)).perform(typeText(lastName));
        onView(withId(R.id.Email)).perform(typeText(email));
        onView(withId(R.id.Password)).perform(typeText(password));
        onView(withId(R.id.btnScustomer)).perform(click());
        onView(withId(R.id.SignUp)).perform(click());
        Thread.sleep(20000);
    }

    @After
    public void tearDown() {
    }
}