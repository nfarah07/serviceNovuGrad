package com.example.prototyped1.LayoutImplementations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.prototyped1.EmployeeServiceSelectActivity;
import com.example.prototyped1.R;

import java.util.ArrayList;

public class layout_selected_services extends AppCompatActivity {

    ArrayList<String> servicesOfferedNames;

    public layout_selected_services(EmployeeServiceSelectActivity employeeServiceSelectActivity, ArrayList<String> servicesOfferedNames) {
        //super(employeeServiceSelectActivity, R.layout.activity_layout_selected_services, servicesOfferedNames);
        setContentView(R.layout.activity_layout_selected_services);
    }

    protected void onCreate(EmployeeServiceSelectActivity context, ArrayList<String> servicesOfferedNames) {

    }
}