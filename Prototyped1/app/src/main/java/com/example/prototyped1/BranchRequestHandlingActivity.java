package com.example.prototyped1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.ServiceRequest;
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
    private Employee currentBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_request_handling);
        currentBranch = (Employee) getIntent().getSerializableExtra("BRANCH");
        serviceRequestsList = (ListView) findViewById(R.id.branchRequestsList);
        TextView btnReturn = (TextView) findViewById(R.id.returnBranchMain);
        databaseRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        serviceRequests = new ArrayList<>();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BranchMainActivity.class);   //Application Context and Activity
                intent.putExtra("EMPLOYEE", currentBranch);
                startActivity(intent);
                finish();
            }
        });

        serviceRequestsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //get the service request you selected
                ServiceRequest pickedRequest = serviceRequests.get(position);
                //TODO: open new activity that holds the response values from service request
                return true;
            }
        });
    }

    //populate listView again when changes were made
    protected void onStart() {
        super.onStart();
        databaseRequests.orderByChild("associatedBranch").equalTo(currentBranch.getID()).addValueEventListener(new ValueEventListener() {
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