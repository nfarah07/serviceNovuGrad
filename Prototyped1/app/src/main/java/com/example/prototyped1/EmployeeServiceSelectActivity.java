package com.example.prototyped1;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.CheckBox;

import com.example.prototyped1.Account;
import com.example.prototyped1.Admin;
import com.example.prototyped1.Customer;
import com.example.prototyped1.Employee;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeServiceSelectActivity extends AppCompatActivity {

    private ListView selectList;

    DatabaseReference ref;

    List<Service> services;

    //private FirebaseAuth mAuth; //used to authenticate user

    private Employee currentUser;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        currentUser = (Employee) getIntent().getSerializableExtra("USER_INFO");

        uid = currentUser.getID();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_select_services);

        ref = FirebaseDatabase.getInstance().getReference();

        services = new ArrayList<>();

        selectList = (ListView) findViewById(R.id.list_of_selectable_services);

        final Button buttonExit = (Button) findViewById(R.id.serviceSelectExit);
        final Button buttonSave = (Button) findViewById(R.id.serviceSelectSave);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExitWelcome(v);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calls the method that handles this occurence
                onSave();
            }
        });
    }

    protected void onStart() {

        super.onStart();
        ref.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }

                EmployeeServiceList serviceAdapter = new EmployeeServiceList(EmployeeServiceSelectActivity.this,services);
                selectList.setAdapter(serviceAdapter);

/*                for(int j=0; j<services.size(); j++) {

                    if(dataSnapshot.child("Employees").child(uid).hasChild("Offered") && dataSnapshot.child("Employees").child(uid).child("Offered").hasChild(services.get(j).getName())) { // Checking the currently offered services by this branch
                        CheckBox selectable = (CheckBox)selectList.getAdapter().getItem(j);
                        selectable.setChecked(true); // If a service is offered, set it as already checked in the checklist
                    }
                }
                Toast.makeText(EmployeeServiceSelectActivity.this, "aaaaa", Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onExitWelcome(View view) {
        Intent intent = new Intent(getApplicationContext(), CDisplayActivity.class);   //Application Context and Activity
        intent.putExtra("USER_INFO", currentUser);
        startActivity(intent);
        finish();
    }

    private void onSave() {
        boolean[] checked = new boolean[selectList.getAdapter().getCount()];
        for(int j=0; j<checked.length; j++) {

        }

        ref.child("Employees").child(uid).child("Offered").child("dummy").setValue("dummy2"); // If there isn't already a category Offered, there is now. Useful for avoiding null pointer in next line
        ref.child("Employees").child(uid).child("Offered").removeValue(); // Remove all the Offered services before adding the new ones

        for(int j=0; j<checked.length; j++){
            if(checked[j]) {
                ref.child("Employees").child(uid).child("Offered").child(services.get(j).getName()).child("serviceID").setValue(services.get(j).getId()); // Now, enter all the newly selected values
            }
        }
        Toast.makeText(EmployeeServiceSelectActivity.this, "Your changes have been saved.", Toast.LENGTH_LONG).show();
    }
}
