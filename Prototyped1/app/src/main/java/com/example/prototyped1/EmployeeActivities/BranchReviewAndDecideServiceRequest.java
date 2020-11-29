package com.example.prototyped1.EmployeeActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototyped1.ClassFiles.ServiceRequest;
import com.example.prototyped1.LayoutImplementations.ServiceRequestInformationRowElement;
import com.example.prototyped1.LayoutImplementations.ServiceRequiredInformationRowElement;
import com.example.prototyped1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//This activity changes the information required to be presented by clients when applying to a service
//this ability is only available to admins until future implementations
public class BranchReviewAndDecideServiceRequest extends AppCompatActivity {

    DatabaseReference ref; //Used to connect to Firebase

    private LinearLayout serviceRequestInformationList;
    private TextView editServiceRequiredInformationTitle;
    private String serviceId;
    private ServiceRequest serviceRequest;
    private Map<String,Object> informationHolder = new HashMap<String,Object>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_review_and_decide_service_request);

        //(name, id, tmpPrice, form, documents)
        this.serviceRequest = (ServiceRequest) getIntent().getSerializableExtra("REQUEST");
        this.serviceId = (String) getIntent().getSerializableExtra("ServiceID");
        
        //Set the title of the service edit page to be the name of the service
        editServiceRequiredInformationTitle = (TextView) findViewById(R.id.decideServiceRequestTitle);

        ref = FirebaseDatabase.getInstance().getReference().child("ServiceRequests"); //Get List of Services

        //Get list to view information regarding service
        serviceRequestInformationList = (LinearLayout) findViewById(R.id.serviceRequestInformationList);

        //Get a reference to the service that is being edited
        ref = FirebaseDatabase.getInstance().getReference().child("ServiceRequests").child(this.serviceRequest.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //For some reason this had to be done, I tried using Map<String, Object> and troubleshooted but nothing works
                //TODO Fix how to send maps to the database
                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};
                informationHolder = dataSnapshot.child("FormResponses").getValue(genericTypeIndicator);
                if(informationHolder != null) populateList();//If the map isn't empty or non-existent, pull from the database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String errorMessage = "The read failed: " + databaseError.getCode();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
        ref = FirebaseDatabase.getInstance().getReference().child("ServiceRequests");

    }

    protected void onStart() {
        super.onStart();

    }

    private void populateList(){
        for(String name : this.informationHolder.keySet()){
            ServiceRequestInformationRowElement holder = new ServiceRequestInformationRowElement(this);
            holder.setOrientation(LinearLayout.HORIZONTAL);

            TextView inputHolder = (TextView) holder.getChildAt(0);
            inputHolder.setText(name);

            TextView informationHolder = new TextView(getApplicationContext());
            informationHolder.setTextColor(Color.WHITE);

            String stringHolder = (String) this.informationHolder.get(name);
            informationHolder.setText(stringHolder);

            holder.addView(informationHolder);


            this.serviceRequestInformationList.addView(holder);


        }
    }

    public void approveRequest(View view){
//        ServiceRequiredInformationRowElement holder = new ServiceRequiredInformationRowElement(this);
//        this.serviceRequestInformationList.addView(holder);
        this.serviceRequest.setRequestStatus("approved");
        ref.child(this.serviceRequest.getId()).setValue(this.serviceRequest);
        ref.child(this.serviceRequest.getId()).child("FormResponses").setValue(this.informationHolder);
        Toast.makeText(getApplicationContext(), "Request Approved", Toast.LENGTH_LONG).show();
        finish();
    }

    public void rejectRequest(View v){
        this.serviceRequest.setRequestStatus("rejected");
        ref.child(this.serviceRequest.getId()).setValue(this.serviceRequest);
        ref.child(this.serviceRequest.getId()).child("FormResponses").setValue(this.informationHolder);
        Toast.makeText(getApplicationContext(), "Request Rejected", Toast.LENGTH_LONG).show();
        finish();
    }
}