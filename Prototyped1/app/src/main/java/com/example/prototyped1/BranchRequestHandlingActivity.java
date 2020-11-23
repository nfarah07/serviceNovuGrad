package com.example.prototyped1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BranchRequestHandlingActivity extends AppCompatActivity {

    private ListView serviceRequestsList;
    DatabaseReference databaseRequests;
    List<ServiceRequest> serviceRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_request_handling);
        serviceRequestsList = (ListView) findViewById(R.id.branchRequestsList);
        databaseRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        serviceRequests = new ArrayList<>();

        serviceRequestsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //get the service request you selected
                ServiceRequest pickedRequest = serviceRequests.get(position);
                //open new activity that holds the response values from service request
                return true;
            }
        });
    }

    protected void onStart() {
        super.onStart();
        databaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceRequests.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ServiceRequest request = postSnapshot.getValue(ServiceRequest.class);
                    serviceRequests.add(request);
                }
                EmployeeRequestList requestsAdapter = new EmployeeRequestList(BranchRequestHandlingActivity.this, serviceRequests);
                serviceRequestsList.setAdapter(requestsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}