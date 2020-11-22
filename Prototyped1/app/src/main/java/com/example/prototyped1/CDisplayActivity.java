package com.example.prototyped1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.prototyped1.Customer;
import com.example.prototyped1.Employee;
import com.example.prototyped1.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CDisplayActivity extends AppCompatActivity {
    private UserAccount user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (UserAccount) getIntent().getSerializableExtra("USER_INFO");
//        getIntent().getSerializableExtra("")
        String userFirstName = user.getNameFirst();
        setContentView(R.layout.activity_customer_display);
        TextView message = (TextView) findViewById(R.id.messageDisplayID);
        if (user instanceof Customer){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a Customer");

        }
        if(user instanceof Employee){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a BranchEmployee");

            Map<String, Integer> hours = new HashMap<>();

            hours.put("Monday,Start", 0);
            hours.put("Monday,End", 0);
            hours.put("Tuesday,Start", 0);
            hours.put("Tuesday,End", 0);
            hours.put("Wednesday,Start", 0);
            hours.put("Wednesday,End", 0);
            hours.put("Thursday,Start", 0);
            hours.put("Thursday,End", 0);
            hours.put("Friday,Start", 0);
            hours.put("Friday,End", 0);
            hours.put("Saturday,Start", 0);
            hours.put("Saturday,End", 0);
            hours.put("Sunday,Start", 0);
            hours.put("Sunday,End", 0);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference newHourReference = database.getReference("Employees/" + user.getID() + "/OpenHours");
            newHourReference.setValue(hours);

            // TODO insert the code for the mandatory dialog box here

            Intent intent = new Intent(getApplicationContext(), BranchMainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
