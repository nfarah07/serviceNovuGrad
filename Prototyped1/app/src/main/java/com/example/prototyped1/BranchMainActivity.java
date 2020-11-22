package com.example.prototyped1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class BranchMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_main);


    }


    public void onServicesOffered(View view){
        Employee tmp = new Employee("BigNe", "wTest", "newtes@hotmail.ca", "1234567", "FWcEDlygrMhDSAaSyjDghRS4gT33" ); // Hardcoded for testing with an employee
        Intent intent = new Intent(getApplicationContext(), EmployeeServiceSelectActivity.class);
        intent.putExtra("USER_INFO", tmp);
        startActivity(intent);
        finish();
    }



        /*
    public void onServiceRequests(View view){
        Intent intent = new Intent(getApplicationContext(), BranchServiceRequests.class);
        startActivity(intent);
        finish();
    }

     */

    /*
    public void onHoursOpen(View view){
        Intent intent = new Intent(getApplicationContext(), BranchChangeHour.class);
        startActivity(intent);
        finish();
    }
    */
}