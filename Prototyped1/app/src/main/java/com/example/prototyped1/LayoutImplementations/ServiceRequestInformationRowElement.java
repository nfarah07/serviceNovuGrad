package com.example.prototyped1.LayoutImplementations;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.prototyped1.R;
import com.example.prototyped1.ClassFiles.Service;

public class ServiceRequestInformationRowElement extends LinearLayout {
    private Activity activity;
    //    List<Service> services;
    EditText editTextInformationName;

    public ServiceRequestInformationRowElement(Activity activity) {
        super(activity);
        this.activity = activity;
//        this.services = services;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(activity).inflate(R.layout.service_request_information_row_element, this, true);
        init();
    }

    public ServiceRequestInformationRowElement(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
        this.activity = activity;
//        this.services = services;
    }

    private void init(){
        editTextInformationName = (EditText) findViewById(R.id.editTextInformationName);
    }


}