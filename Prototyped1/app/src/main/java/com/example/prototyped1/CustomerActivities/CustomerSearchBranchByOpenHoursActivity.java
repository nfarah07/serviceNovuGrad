package com.example.prototyped1.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Customer;
import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.LayoutImplementations.CustomerSearchBranchesList;
import com.example.prototyped1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerSearchBranchByOpenHoursActivity extends AppCompatActivity {

    // instance variables
    private ListView listOfResultingBranches;
    private Spinner spinnerDay;
    private Spinner spinnerHour;
    private Spinner spinnerMin;
    private List<Employee> branchesOffering;
    private Map<String,String> timeRangeHolder = new HashMap<String,String>();
    private DatabaseReference branchesRef;
    private ArrayList<String> daysOfTheWeek = new ArrayList<String>(
            Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    private ArrayList<String> hoursOfDay = new ArrayList<String>(Arrays.asList(
            "00","01","02", "03", "04", "05", "06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"));
    private ArrayList<String> minOfDay = new ArrayList<String>(Arrays.asList(
            "00","05","10","15","20","25","30","35","40","45","50","55"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_branch_by_open_hours);
        final Customer fromCD = (Customer) getIntent().getSerializableExtra("CUSTOMER");
        listOfResultingBranches = (ListView) findViewById(R.id.listViewSearchByHours);
        spinnerDay = (Spinner) findViewById(R.id.spinnerDayOfWeeek);
        spinnerHour = (Spinner) findViewById(R.id.spinnerHourOfDay);
        spinnerMin = (Spinner) findViewById(R.id.spinnerMinuteOfHour);
        branchesRef = FirebaseDatabase.getInstance().getReference("Employees");
        branchesOffering = new ArrayList<>();

        // populate Days of Week spinner
        ArrayAdapter<String> daySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, daysOfTheWeek);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(daySpinnerAdapter);
        spinnerDay.setSelection(0, true);
        View v = spinnerDay.getSelectedView();
        ((TextView)v).setTextColor(Color.WHITE);
        // populate Hours of Day spinner
        ArrayAdapter<String> hourSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hoursOfDay);
        hourSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(hourSpinnerAdapter);
        spinnerHour.setSelection(0, true);
        View w = spinnerHour.getSelectedView();
        ((TextView)w).setTextColor(Color.WHITE);
        // populate Minutes of Day spinner
        ArrayAdapter<String> minsSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minOfDay);
        minsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMin.setAdapter(minsSpinnerAdapter);
        spinnerMin.setSelection(0, true);
        View z = spinnerMin.getSelectedView();
        ((TextView)z).setTextColor(Color.WHITE);

        // onClick listener for listview items
        listOfResultingBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Employee selectedBranch = branchesOffering.get(position);
                Intent intent = new Intent(getApplicationContext(), CustomerCreateServiceRequestActivity.class);
                intent.putExtra("EMPLOYEE_FOR_REQUEST", selectedBranch);
                intent.putExtra("CUSTOMER", fromCD);
                startActivity(intent);
                return true;
            }
        });

        // item listener for day of week spinner
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.WHITE);
                //TODO: search for branch by hours
                searchByHours(parent.getItemAtPosition(position).toString(), spinnerHour.getSelectedItem().toString(), spinnerMin.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        // item listener for hour of day spinner
        spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.WHITE);
                //TODO: search for branch by hours
                searchByHours(spinnerDay.getSelectedItem().toString(), parent.getItemAtPosition(position).toString(), spinnerMin.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        // item listener for minutes of hour spinner
        spinnerMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.WHITE);
                //TODO: search for branch by hours
                searchByHours(spinnerDay.getSelectedItem().toString(), spinnerHour.getSelectedItem().toString(), parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    // search all branch hours
    public void searchByHours(final String day, final String hour, final String min) {
        branchesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                branchesOffering.clear();
                String hourMin = hour+""+min;
                int searchTime = Integer.parseInt(hourMin);
                // for every employee in the list of employees
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Employee branch = postSnapshot.getValue(Employee.class);
                    GenericTypeIndicator<Map<String,String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                    timeRangeHolder = postSnapshot.child("mapOfHours").getValue(genericTypeIndicator);
                    if(timeRangeHolder != null) {
                        int startForDay = Integer.parseInt(timeRangeHolder.get(day + ",Start"));

                        int endForDay = Integer.parseInt(timeRangeHolder.get(day + ",End"));

                        if (startForDay <= searchTime && searchTime <= endForDay) {
                            branchesOffering.add(branch);
                        }
                    }
                }
                CustomerSearchBranchesList adapter = new CustomerSearchBranchesList(CustomerSearchBranchByOpenHoursActivity.this, branchesOffering);
                listOfResultingBranches.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}