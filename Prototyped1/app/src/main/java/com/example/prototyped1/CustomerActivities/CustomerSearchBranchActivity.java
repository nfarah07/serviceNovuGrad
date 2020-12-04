package com.example.prototyped1.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.LayoutImplementations.CustomerSearchByBranchAddress;
import com.example.prototyped1.LayoutImplementations.CustomerSearchByServiceOffered;
import com.example.prototyped1.R;
import com.example.prototyped1.ServiceNovigradApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerSearchBranchActivity extends AppCompatActivity {

    private Spinner branchSearchMethodSpinner;
    private String[] optionsOfSearch = {"Branch Address", "Services Offered","Branch Hours"};
    private LinearLayout searchLayoutBox;
    public ServiceNovigradApp serviceNovigradApp = null;
//    private CustomerSearchByServiceOffered searchServiceHolder;
    private ArrayList<String> servicesOfferedNames = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_branch);

        serviceNovigradApp = (ServiceNovigradApp) this.getApplicationContext();
        serviceNovigradApp.setCurrentActivity(this);


        searchLayoutBox = (LinearLayout) findViewById(R.id.searchLayoutBox);

        //This spinner contains the options for searching a branch (address/hours/services offered)
        this.branchSearchMethodSpinner = (Spinner) findViewById(R.id.branchSearchMethodSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.optionsOfSearch);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        this.branchSearchMethodSpinner.setAdapter(adapter);

//        Set a listener to change the UI when the user selects something
        setSpinnerListener();
    }

    /**
     * On selection method to provide the user with the correct interface based on the options they chose
     * this acts as a controller to select the appropriate items to display
     *     
     */
    private void setSpinnerListener(){
        //Create a new on selected item listener
        this.branchSearchMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()){
                    case "Branch Hours":
                        Toast.makeText(getApplicationContext(), "Branch Hours", Toast.LENGTH_LONG).show();
                        searchLayoutBox.removeAllViews();
                        break;
                    case "Services Offered":
                        searchLayoutBox.removeAllViews();
                        CustomerSearchByServiceOffered searchServiceHolder = new CustomerSearchByServiceOffered(serviceNovigradApp.getCurrentActivity());
//
                        System.out.println("\n.\n..\n... HI \n.\n..\n...");

//                        searchHolder.setOrientation(LinearLayout.VERTICAL);
                        Toast.makeText(getApplicationContext(), "Services Offered", Toast.LENGTH_LONG).show();
                        searchLayoutBox.addView(searchServiceHolder);
//                        assignListener();
                        break;
                    case "Branch Address":
                        searchLayoutBox.removeAllViews();
                        CustomerSearchByBranchAddress searchHolder = new CustomerSearchByBranchAddress(serviceNovigradApp.getCurrentActivity());
//                        searchHolder.setOrientation(LinearLayout.VERTICAL);
                        searchLayoutBox.addView(searchHolder);
                        Toast.makeText(getApplicationContext(), "Branch Address", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void assignListener(){
        LinearLayout holder = (LinearLayout) searchLayoutBox.getChildAt(0);

    }

    public ArrayList<String> populateSpinner(){
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        ArrayList<Employee> employeeInfo = new ArrayList<Employee>();
        employeeInfo.clear();
        employeeList.clear();

        DatabaseReference databaseEmployeesReference = FirebaseDatabase.getInstance().getReference("Services");

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

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, servicesOfferedNames);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        this.editServiceSearchedSpinner.setAdapter(arrayAdapter);
        databaseEmployeesReference = FirebaseDatabase.getInstance().getReference("Employees");
        return servicesOfferedNames;
    }
}