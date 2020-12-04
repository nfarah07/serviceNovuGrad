package com.example.prototyped1.LayoutImplementations;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerSearchByServiceOffered extends LinearLayout {

    private Activity activity;
    //    List<Service> services;
    private TextView searchByBranchAddressTitle;
    private Spinner editServiceSearchedSpinner;
    private ListView listOfBranches;
    private DatabaseReference databaseEmployeesReference;
    public ArrayAdapter<String> adapter;
    private final ArrayList<Employee> employeeList =  new ArrayList<Employee>();
    private final ArrayList<String> employeeInfo = new ArrayList<String>();
    private final ArrayList<String> servicesOfferedIDs = new ArrayList<String>();
    private final ArrayList<String> servicesOfferedNames = new ArrayList<String>();
    private Activity activityHolder;


    public CustomerSearchByServiceOffered(Activity activity) {
        super(activity);
        this.activity = activity;

        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(activity).inflate(R.layout.layout_customer_search_by_service_offered, this, true);

        this.listOfBranches = (ListView) findViewById(R.id.listOfServiceBranches);
        this.editServiceSearchedSpinner = (Spinner) findViewById(R.id.editServiceSearchedSpinner);

        adapter=new ArrayAdapter<>(activity,
                android.R.layout.simple_list_item_1,
                employeeInfo);
        listOfBranches.setAdapter(adapter);

        databaseEmployeesReference = FirebaseDatabase.getInstance().getReference("Employees");

        populateSpinner();

        databaseEmployeesReference = FirebaseDatabase.getInstance().getReference("Employees");
        
        editServiceSearchedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("\n\n\n HI \n\n\n");

                Spinner holder = (Spinner) adapterView;
//                searchByServiceOffered(holder.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        init();
    }

    public CustomerSearchByServiceOffered(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
        this.activity = activity;
//        this.services = services;
    }

    private void init(){
        searchByBranchAddressTitle = (TextView) findViewById(R.id.searchByBranchAddressTitle);

    }

    public void searchByServiceOffered(final String searchServiceHolder){
        employeeList.clear();
        employeeInfo.clear();
        System.out.println("\n\n\n HI \n\n\n");
        databaseEmployeesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    System.out.println("\n\n\n HI \n\n\n");
                    Employee branch = postSnapshot.getValue(Employee.class);
                    for(DataSnapshot post2Snapshot : postSnapshot.child("Offered").getChildren()) {
                        System.out.println("\n\n\n");
                        String serviceName = postSnapshot.getKey();
                        System.out.println(serviceName);
                        System.out.println("\n\n\n");

                        if(serviceName != null){
                            if(serviceName.equals(searchServiceHolder))employeeList.add(branch);
                        }

                    }

//
                }

                for(Employee tmp : employeeList){
                    employeeInfo.add(tmp.getAddress());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    
    public void populateSpinner(){
        employeeInfo.clear();
        employeeList.clear();

        databaseEmployeesReference = FirebaseDatabase.getInstance().getReference("Services");

        databaseEmployeesReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesOfferedNames.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service serviceHolder = postSnapshot.getValue(Service.class);
                    servicesOfferedNames.add(serviceHolder.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, servicesOfferedNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.editServiceSearchedSpinner.setAdapter(arrayAdapter);
        databaseEmployeesReference = FirebaseDatabase.getInstance().getReference("Employees");
    }




}