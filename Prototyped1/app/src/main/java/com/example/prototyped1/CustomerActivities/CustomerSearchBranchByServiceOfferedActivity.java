package com.example.prototyped1.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Customer;
import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.LayoutImplementations.CustomerSearchBranchesList;
import com.example.prototyped1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchBranchByServiceOfferedActivity extends AppCompatActivity {

    // instance variables
    private ListView listViewBranches;
    private DatabaseReference refBranches;
    private DatabaseReference refServices;
    private List<Employee> branchResults;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_branch_by_service_offered);
        final Customer fromCD = (Customer) getIntent().getSerializableExtra("CUSTOMER");
        listViewBranches = (ListView) findViewById(R.id.listViewBranchesOfferService);
        spinner = (Spinner) findViewById(R.id.spinnerAllServices);
        refBranches = FirebaseDatabase.getInstance().getReference("Employees");
        refServices = FirebaseDatabase.getInstance().getReference("Services");
        branchResults = new ArrayList<>();

        // populate spinner of all services
        refServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> allServicesNames = new ArrayList<String>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String serviceName = snapshot.getValue(Service.class).getName();
                    allServicesNames.add(serviceName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomerSearchBranchByServiceOfferedActivity.this,
                        android.R.layout.simple_spinner_item, allServicesNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(0, true);
                View v = spinner.getSelectedView();
                ((TextView)v).setTextColor(Color.WHITE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        // when Customer clicks on spinner item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Change the selected item's text color
                ((TextView) view).setTextColor(Color.WHITE);
                searchByService(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        listViewBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Employee selectedBranch = branchResults.get(position);
                Intent intent = new Intent(getApplicationContext(), CustomerCreateServiceRequestActivity.class);
                intent.putExtra("EMPLOYEE_FOR_REQUEST", selectedBranch);
                intent.putExtra("CUSTOMER", fromCD);
                startActivity(intent);
                return true;
            }
        });
    }

    public void searchByService(final String serviceName) {
        // add Value Event Listener for all the employees
        refBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // clear current branch list
                branchResults.clear();
                // loop through every employee
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Employee branch = postSnapshot.getValue(Employee.class);
                    for (DataSnapshot postpostSnapshot : postSnapshot.child("Offered").getChildren()){
                        // get name of service offered
                        String service = postpostSnapshot.getKey();
                        if(service.equals(serviceName)) {
                            // add branch if it offers that service
                            branchResults.add(branch);
                        }
                    }
                }
                // populate list view with branches that offer service
                CustomerSearchBranchesList adapter = new CustomerSearchBranchesList(CustomerSearchBranchByServiceOfferedActivity.this, branchResults);
                listViewBranches.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}