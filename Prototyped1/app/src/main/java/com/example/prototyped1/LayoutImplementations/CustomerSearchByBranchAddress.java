package com.example.prototyped1.LayoutImplementations;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.ServiceRequest;
import com.example.prototyped1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchByBranchAddress extends LinearLayout {

    private Activity activity;
    //    List<Service> services;
    private TextView searchByBranchAddressTitle;
    private EditText searchBranchEditText;
    private ListView listOfBranches;
    private DatabaseReference databaseRequests;
    public ArrayAdapter<String> adapter;
    private final ArrayList<Employee> employeeList =  new ArrayList<Employee>();
    private final ArrayList<String> employeeInfo = new ArrayList<String>();


    public CustomerSearchByBranchAddress(Activity activity) {
        super(activity);
        this.activity = activity;
//        this.services = services;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(activity).inflate(R.layout.layout_customer_search_by_branch_address, this, true);

        this.listOfBranches = (ListView) findViewById(R.id.listOfBranches);
        this.searchBranchEditText = (EditText) findViewById(R.id.editTextBranchAddress);

        adapter=new ArrayAdapter<>(activity,
                android.R.layout.simple_list_item_1,
                employeeInfo);
        listOfBranches.setAdapter(adapter);

        databaseRequests = FirebaseDatabase.getInstance().getReference("Employees");

        this.searchBranchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchByAddress(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        init();
    }

    public CustomerSearchByBranchAddress(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
        this.activity = activity;
//        this.services = services;
    }

    private void init(){
        searchByBranchAddressTitle = (TextView) findViewById(R.id.searchByBranchAddressTitle);
    }

    public void searchByAddress(final String searchHolder){
        employeeList.clear();
        employeeInfo.clear();
        databaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(searchHolder);
                    Employee branch = postSnapshot.getValue(Employee.class);
//                    String addressHolder = postSnapshot.child("address").getValue().toString();
                    if(branch.address != null){
                        final String holder = searchHolder;
                        if(branch.address.toLowerCase().contains(searchHolder.toLowerCase()))  {
                            System.out.println("f");
                            employeeList.add(branch);

                        }
//                        System.out.prin
                    }

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




}