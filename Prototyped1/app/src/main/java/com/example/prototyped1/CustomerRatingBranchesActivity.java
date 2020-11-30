package com.example.prototyped1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Customer;
import com.example.prototyped1.ClassFiles.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerRatingBranchesActivity extends AppCompatActivity {

    private ListView employeeList;
    DatabaseReference databaseBranches;
    List<Employee> allEmployees;
    private Customer currentCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rating_branches);
        currentCustomer = (Customer) getIntent().getSerializableExtra("CUSTOMER");
        employeeList = (ListView) findViewById(R.id.listOfEmployees);
        TextView btnReturn = (TextView) findViewById(R.id.backToHome);
        databaseBranches = FirebaseDatabase.getInstance().getReference("Employees");
        allEmployees = new ArrayList<>();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerMainActivity.class);
                intent.putExtra("CUSTOMER", currentCustomer);
                startActivity(intent);
                finish();
            }
        });

        employeeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //get specific employee you long clicked
                Employee pickedBranch = allEmployees.get(position);
                ratingBranchDialog();
                return true;
            }
        });
    }

    protected void onStart() {
        super.onStart();
        databaseBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allEmployees.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Employee employee = (Employee) postSnapshot.getValue(Employee.class);
                    allEmployees.add(employee);
                }
                BranchRatingsList branchesAdapter = new BranchRatingsList(CustomerRatingBranchesActivity.this, allEmployees);
                employeeList.setAdapter(branchesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void ratingBranchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setTitle("How do You Rate This Branch?");
        View dialogLayout = inflater.inflate(R.layout.rate_branch_dialog, null);
        builder.setView(dialogLayout);
        final RatingBar ratingBar = dialogLayout.findViewById(R.id.branchRatingBar);
        final Button submitButton = dialogLayout.findViewById(R.id.submitRatingBtn);
        final AlertDialog build = builder.create();
        build.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build.dismiss();
            }
        });
    }
}