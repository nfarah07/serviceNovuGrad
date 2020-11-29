package com.example.prototyped1.AdministratorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.prototyped1.R;

// In Services List, add service redirects to this activity
// Edit Text : Service Name
// Edit Text : Service Price
// Button : Add Required Info
// ListView of current required infos
// Buttons : Create Service , Cancel

public class AdminServiceReqInfoChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service_req_info_choice);
    }
}