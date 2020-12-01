package com.example.prototyped1.CustomerActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Customer;
import com.example.prototyped1.R;

public class CustomerMainActivity extends AppCompatActivity {

    private Customer fromCD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        //get customer from CDisplayActivity
        fromCD = (Customer) getIntent().getSerializableExtra("CUSTOMER");
        String userfirstname = fromCD.getNameFirst();
        String userlastname = fromCD.getNameLast();
        TextView message = (TextView) findViewById(R.id.messageDisplayCustomerName);
        message.setText(userfirstname+" "+userlastname);
    }

    public void onRateBranch(View view) {
        Intent intent = new Intent(getApplicationContext(), CustomerRatingBranchesActivity.class);
        intent.putExtra("CUSTOMER", fromCD);
        startActivity(intent);
    }
}