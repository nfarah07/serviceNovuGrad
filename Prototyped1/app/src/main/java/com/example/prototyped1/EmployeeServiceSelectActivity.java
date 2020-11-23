package com.example.prototyped1;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prototyped1.ClassFiles.Employee;

import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.LayoutImplementations.EmployeeServiceList;
import com.example.prototyped1.LayoutImplementations.layout_selected_services;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeServiceSelectActivity extends AppCompatActivity {

    private ListView selectList;

    DatabaseReference ref;

    List<Service> services;

    //private FirebaseAuth mAuth; //used to authenticate user

    private Employee currentUser;

    private String uid;

    boolean[] checked;

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
                // Calls the method that handles this occurrence
                onSave();
            }
        });
    }

    protected void onStart() {

        super.onStart();

        final ArrayList<String> servicesOfferedIDs = new ArrayList<>();
        final ArrayList<String> servicesOfferedNames = new ArrayList<>();

        ref.child("Employees").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Offered") != null) {
                    for (DataSnapshot postSnapshot : dataSnapshot.child("Offered").getChildren()) {
                        for(DataSnapshot postpostSnapshot : postSnapshot.getChildren()){
                            String service = postpostSnapshot.getValue(String.class);
                            servicesOfferedIDs.add(service);
                        }
                        String serviceName = postSnapshot.getKey();
                        servicesOfferedIDs.add(serviceName);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ref.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);

                }

                //layout_selected_services serviceAdapeter = new layout_selected_services(EmployeeServiceSelectActivity.this,servicesOfferedNames);
                EmployeeServiceList serviceAdapter = new EmployeeServiceList(EmployeeServiceSelectActivity.this,services, servicesOfferedIDs);
                selectList.setAdapter(serviceAdapter);

                checked = new boolean[services.size()];



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
        Intent intent = new Intent(getApplicationContext(), BranchMainActivity.class);   //Application Context and Activity
        intent.putExtra("EMPLOYEE", currentUser);
        startActivity(intent);
        finish();
    }

    private void onSave() {

        ref.child("Employees").child(uid).child("Offered").child("dummy").setValue("dummy2"); // If there isn't already a category Offered, there is now. Useful for avoiding null pointer in next line
        ref.child("Employees").child(uid).child("Offered").removeValue(); // Remove all the Offered services before adding the new ones


        for(int j=0; j<checked.length; j++){
            if(checked[j]) {
                ref.child("Employees").child(uid).child("Offered").child(services.get(j).getName()).child("serviceID").setValue(services.get(j).getId()); // Now, enter all the newly selected values
            }
        }
        Toast.makeText(EmployeeServiceSelectActivity.this, "Your changes have been saved.", Toast.LENGTH_LONG).show();
    }

    public boolean itemClicked(View view){
        CheckBox serviceCheck = (CheckBox) view;
        String serviceName = (String) serviceCheck.getText();
        int newIndex = -1;
        for(int j=0; j<services.size(); j++) {
            if(services.get(j).getName().equals(serviceName)){
                newIndex=j;
            }
        }
        if(serviceCheck.isChecked()){
            System.out.println("\n\n\n\nChecked\n\n\n\n");
            checked[newIndex]=true;
            return true;
        }
        checked[newIndex]=false;
        return false;
    }
}
