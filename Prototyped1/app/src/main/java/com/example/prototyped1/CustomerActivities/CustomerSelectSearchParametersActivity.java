package com.example.prototyped1.CustomerActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prototyped1.R;

public class CustomerSelectSearchParametersActivity extends AppCompatActivity implements View.OnClickListener {

    private Button selectSearchByBranchAddress;
    private Button selectSearchByServiceOffered;
    private Button selectSearchByBranchHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_select_search_parameters);
        this.selectSearchByBranchAddress = (Button) findViewById(R.id.selectSearchByBranchAddress);
        this.selectSearchByServiceOffered = (Button) findViewById(R.id.selectSearchByServiceOffered);
        this.selectSearchByBranchHours = (Button) findViewById(R.id.selectSearchByBranchHours);

        this.selectSearchByBranchAddress.setOnClickListener(this);
        this.selectSearchByServiceOffered.setOnClickListener(this);
        this.selectSearchByBranchHours.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        Button holder = (Button) view;
        switch (holder.getText().toString()){
            case "Search Branches By Address":
                Intent addressIntent = new Intent(getApplicationContext(), CustomerSearchBranchByAddressActivity.class);
//                intent.putExtra("CUSTOMER", fromCD);
                startActivity(addressIntent);
                break;
            case "Search Branches By Service Offered":
                Intent serviceIntent = new Intent(getApplicationContext(), CustomerSearchBranchByServiceOfferedActivity.class);
//                intent.putExtra("CUSTOMER", fromCD);
                startActivity(serviceIntent);
                break;
            case "Search Branches by Open Hours":
                Intent branchIntent = new Intent(getApplicationContext(), CustomerSearchBranchByOpenHoursActivity.class);
//                intent.putExtra("CUSTOMER", fromCD);
                startActivity(branchIntent);
                break;



        }
    }
}