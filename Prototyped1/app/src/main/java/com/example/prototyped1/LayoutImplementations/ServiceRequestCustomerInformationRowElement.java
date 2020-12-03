package com.example.prototyped1.LayoutImplementations;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototyped1.R;

public class ServiceRequestCustomerInformationRowElement extends LinearLayout {
    private Activity activity;
    //    List<Service> services;
    TextView editTextInformationName;

    public ServiceRequestCustomerInformationRowElement(Activity activity) {
        super(activity);
        this.activity = activity;
//        this.services = services;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(activity).inflate(R.layout.service_request_customer_information_row_element_linear_layout, this, true);
        init();
    }

    public ServiceRequestCustomerInformationRowElement(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
        this.activity = activity;
//        this.services = services;
    }

    private void init(){
        editTextInformationName = (TextView) findViewById(R.id.searchByBranchAddressTitle);
    }


}