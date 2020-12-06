package com.example.prototyped1.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

//import com.example.prototyped1.AdministratorActivities.CustomerCreateServiceRequestActivity;
import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.LayoutImplementations.CustomerSearchBranchesList;
import com.example.prototyped1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchBranchByAddressActivity extends AppCompatActivity {

    //instance variables
    private EditText branchAddressSearch;
    private ListView branchesContainingSearch;
    private DatabaseReference dbBranches;
    private List<Employee> branchesReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_by_branch_address);
        branchAddressSearch = (EditText) findViewById(R.id.editTextBranchAddress);
        branchesContainingSearch = (ListView) findViewById(R.id.listViewBranchesByAddress);
        dbBranches = FirebaseDatabase.getInstance().getReference("Employees");
        branchesReturned = new ArrayList<>();

        this.branchAddressSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchByAddress(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        branchesContainingSearch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Employee selectedBranch = branchesReturned.get(position);
                Intent intent = new Intent(getApplicationContext(), CustomerCreateServiceRequestActivity.class);
                intent.putExtra("EMPLOYEE_FOR_REQUEST", selectedBranch);
                startActivity(intent);
                return true;
            }
        });

        branchesContainingSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void searchByAddress(final String searchHolder){
        branchesReturned.clear();
        dbBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(searchHolder);
                    Employee branch = postSnapshot.getValue(Employee.class);
//                    String addressHolder = postSnapshot.child("address").getValue().toString();
                    if(branch.address != null){
                        final String holder = searchHolder;
                        if(branch.address.toLowerCase().contains(searchHolder.toLowerCase()))  {
                            //System.out.println("f");
                            branchesReturned.add(branch);
                        }
                    }
                }
                CustomerSearchBranchesList branchAdapter = new CustomerSearchBranchesList(CustomerSearchBranchByAddressActivity.this, branchesReturned);
                branchesContainingSearch.setAdapter(branchAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}