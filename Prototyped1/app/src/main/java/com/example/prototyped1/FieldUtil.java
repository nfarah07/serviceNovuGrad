package com.example.prototyped1;

import android.content.Context;
import android.widget.Toast;

public class FieldUtil {

    public static boolean fieldsAreValid(String firstName, String lastName, String pwd, Context c) {
        // check name length
        if (firstName.length() < 2) {
            Toast.makeText(c, "First name should be at least 3 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if (lastName.length() < 2) {
            Toast.makeText(c, "Last name should be at least 2 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        // check email pattern(no clue how to )

        // check password length and match
        if (!validPWD(pwd)) {
            Toast.makeText(c, "Password must be at least 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static boolean validPWD(String pwd) {
       return (pwd.length() > 6);
    }
}
