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

public class AdminLogIn {

    @Rule
    public ActivityTestRule<SignUpActivity> signUpActivityTestRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    private String email = "admin";
    private String password = "admin";

    @Before
    public void setUp() {
    }

    @Test
    public void checkFieldsExist() throws InterruptedException {
        onView((withId(R.id.AlreadyUser))).perform(click());
        onView(withId(R.id.Email)).perform(typeText(email));
        onView(withId(R.id.Password)).perform(typeText(password));
        onView(withId(R.id.Login)).perform(click());
    }

    @After
    public void tearDown() {
    }
}