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

    /*
    public void onServicesOffered(View view){
        Intent intent = new Intent(getApplicationContext(), BranchServicesOffered.class);
        startActivity(intent);
        finish();
    }

     */

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