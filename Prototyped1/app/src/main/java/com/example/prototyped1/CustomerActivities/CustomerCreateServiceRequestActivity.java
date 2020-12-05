package com.example.prototyped1.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.Service;
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

/**
 * This class is meant to allow customers to create a service request and submit it to a branch
 *
 * This class is given the branch that is offering the service, as well as the corresponding service associated with it
 *
 */
public class CustomerCreateServiceRequestActivity extends AppCompatActivity {

    DatabaseReference dbBranches; //Used to connect to Firebase

    private Spinner branchNameSpinner; //Spinner to hold the branch name
    private Spinner branchServiceSpinner; //Once the user selects a branch from the spinner, this one is populated

    //List of the branches to be populated
    private ArrayList<Employee> branchList = new ArrayList<Employee>();
    //List of branch names to be stored in the spinner
    private ArrayList<String> branchNames = new ArrayList<String>();
    //List of the services offered to be populated once a branch is selected
    private ArrayList<String> branchServicesOffered = new ArrayList<String>();



    private ListView serviceRequiredInformationList;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_service_request);

        branchServiceSpinner = (Spinner) findViewById(R.id.branchServiceSpinner);
        branchNameSpinner = (Spinner) findViewById(R.id.branchNameSpinner);

        dbBranches = FirebaseDatabase.getInstance().getReference().child("Employees"); //Get List of Employees

        populateBranchSpinner();

        branchNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employee branchSelected = (Employee) branchList.get(i);
                populateServiceOfferedSpinner(branchSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //(name, id, tmpPrice, form, documents)
//        this.serviceDetails = (ArrayList<String>) getIntent().getSerializableExtra("ServiceDetails");
//        this.serviceId = (String) getIntent().getSerializableExtra("ServiceID");
//
//
////
//        //Set the title of the service edit page to be the name of the service
//        customerCreateServiceRequestTitle = (TextView) findViewById(R.id.customerServiceRequestTitle);
//        customerCreateServiceRequestTitle.setText(serviceDetails.get(0));



//        //Get list to view information regarding service
//        serviceInformationList = (LinearLayout) findViewById(R.id.serviceRequesCustomertInformationList);
//
//        //Get a reference to the service that is being edited
//        ref = FirebaseDatabase.getInstance().getReference().child("Services").child(this.serviceId);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //For some reason this had to be done, I tried using Map<String, Object> and troubleshooted but nothing works
//                //TODO Fix how to send maps to the database
//                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
//                informationHolder = dataSnapshot.child("mapOfInformation").getValue(genericTypeIndicator);
//                if(informationHolder != null) populateList();//If the map isn't empty or non-existent, pull from the database
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                String errorMessage = "The read failed: " + databaseError.getCode();
//                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
//            }
//        });
//        ref = FirebaseDatabase.getInstance().getReference().child("Services");
//
//        for(int i= 0 ; i < serviceInformationList.getChildCount() ; i++){
//            View view  = serviceInformationList.getChildAt(i);
//            view.setTag(i);
//            ServiceRequiredInformationRowElement holder = (ServiceRequiredInformationRowElement) view;
//            View buttonDeleteInformation = holder.getChildAt(2);
//            buttonDeleteInformation.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_LONG).show();
//                    LinearLayout holder = (LinearLayout) view.getParent();
//                    EditText inputHolder = (EditText) holder.getChildAt(0);
//                    informationHolder.remove(inputHolder.getText().toString());
//                    serviceInformationList.removeView(view);
//                }
//            });
//        }
    }

    protected void onStart() {
        super.onStart();

    }

    /**
     * Once the activity opens, we need a list of all the branches available1
     */
    private void populateBranchSpinner(){
        dbBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(searchHolder);
                    Employee branch = postSnapshot.getValue(Employee.class);
                    branchList.add(branch);
                }
                for(Employee tmp : branchList){
                    branchNames.add(tmp.getAddress());
                }

                //Set adapter from list of branch names to the spinner
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_spinner_item,
                                branchNames); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                branchNameSpinner.setAdapter(spinnerArrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /**
     * Once a branch is selected, this method will populate the spinner with services offered by said branch
     * @param branchSelected
     */
    private void populateServiceOfferedSpinner(Employee branchSelected){
        dbBranches.child(branchSelected.getID()).child("Offered").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(searchHolder);
                    branchServicesOffered.add(postSnapshot.getKey());
                }

                //Set adapter from list of branch names to the spinner
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_spinner_item,
                                branchNames); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                branchNameSpinner.setAdapter(spinnerArrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


//    public void submitAndExit(View v){
//        boolean informationFilled = true;
//        if(informationHolder == null) informationHolder = new HashMap<String,String>();
//        Toast.makeText(getApplicationContext(), Integer.toString(serviceInformationList.getChildCount()), Toast.LENGTH_LONG).show();
//        for(int i = 0; i < serviceInformationList.getChildCount(); i++){
//            LinearLayout viewHolder = (LinearLayout) serviceInformationList.getChildAt(i);
//            EditText inputHolder = (EditText) viewHolder.getChildAt(0);
//            Spinner typeHolder = (Spinner) viewHolder.getChildAt(1);
//
//            if(inputHolder.getText().toString() == null || inputHolder.getText().toString().equals("")){
//                informationFilled = false;
//            }else{
//                informationHolder.put(inputHolder.getText().toString(),typeHolder.getSelectedItem().toString());
//            }
//        }
//
//        if(!informationFilled) {
//            Toast.makeText(getApplicationContext(), "Fill Out Information", Toast.LENGTH_LONG).show();
//        }else{
//            ref.child(this.serviceId).child("mapOfInformation").setValue(this.informationHolder);
//            finish();
//        }
//    }
}