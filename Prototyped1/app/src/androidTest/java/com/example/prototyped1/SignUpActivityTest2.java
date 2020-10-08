package com.example.prototyped1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterTest;

import androidx.core.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

public class SignUpActivityTest2 {


    @Rule
    public ActivityTestRule<SignUpActivity> signUpActivityTestRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    private String firstName = "Employee";
    private String lastName = "Test";
    private String email = "employee@email.com";
    private String password = "employee";

    @Before
    public void setUp() {
    }

    @Test
    public void checkFieldsExist() throws InterruptedException {
        onView(withId(R.id.FirstName)).perform(typeText(firstName));
        onView(withId(R.id.LastName)).perform(typeText(lastName));
        onView(withId(R.id.Email)).perform(typeText(email));
        onView(withId(R.id.Password)).perform(typeText(password));
        onView(withId(R.id.btnSEmployee)).perform(click());
        onView(withId(R.id.SignUp)).perform(click());
        Thread.sleep(20000);
    }

    @After
    public void tearDown() {
    }
}