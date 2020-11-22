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

public class ServiceCreationElement extends LinearLayout {
    private Activity activity;
//    List<Service> services;
    EditText editTextInformationName;
    Spinner informationTypeSpinner;

    public ServiceCreationElement(Activity activity) {
        super(activity);
        this.activity = activity;
//        this.services = services;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(activity).inflate(R.layout.linear_layout_service_information_creation_element, this, true);
        init();
    }

    public ServiceCreationElement(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
        this.activity = activity;
//        this.services = services;
    }

    private void init(){
        editTextInformationName = (EditText) findViewById(R.id.editTextInformationName);
        informationTypeSpinner = (Spinner) findViewById(R.id.informationTypeSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, Service.SERVICE_INFO_DATA_TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        informationTypeSpinner.setAdapter(adapter);
    }


}