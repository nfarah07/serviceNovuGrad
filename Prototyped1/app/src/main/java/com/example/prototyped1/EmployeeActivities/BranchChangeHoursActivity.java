 package com.example.prototyped1.EmployeeActivities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.LayoutImplementations.BranchHourRowElementLinearLayout;
import com.example.prototyped1.LayoutImplementations.ServiceRequiredInformationRowElement;
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
import java.util.Map;

 /**
  * This activity will allow branches to change the hours in which they are open
  */
 public class BranchChangeHoursActivity extends AppCompatActivity {

     DatabaseReference ref; //Used to connect to Firebase

     private LinearLayout hourChangeListLinearLayout;
     private Button submitHoursButton;
     private Map<String, String> timeRangeHolder = new HashMap<String, String>();
     private Employee branchChanging;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_branch_change_hours);

         this.hourChangeListLinearLayout = (LinearLayout) findViewById(R.id.hourChangeListLinearLayout);

         //Get branch modifying hours
         this.branchChanging = (Employee) getIntent().getSerializableExtra("EMPLOYEE");

         //Referenace database to get time range
         ref = FirebaseDatabase.getInstance().getReference().child("Employees").child(this.branchChanging.getID());
         ref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 //For some reason this had to be done, I tried using Map<String, Object> and troubleshooted but nothing works
                 //TODO Fix how to send maps to the database
                 GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                 };
                 timeRangeHolder = dataSnapshot.child("mapOfHours").getValue(genericTypeIndicator);
                 if (timeRangeHolder != null) {
                     populateList(timeRangeHolder);//If the map isn't empty or non-existent, pull from the database
                 } else {
                     populateList();
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 String errorMessage = "The read failed: " + databaseError.getCode();
                 Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
             }
         });


     }

     private void populateList() {

         ArrayList<String> daysOfWeek = new ArrayList<String>(
                 Arrays.asList("Monday,Start", "Monday,End",
                         "Tuesday,Start", "Tuesday,End",
                         "Wednesday,Start", "Wednesday,End",
                         "Thursday,Start", "Thursday,End",
                         "Friday,Start", "Friday,End",
                         "Saturday,Start", "Saturday,End",
                         "Sunday,Start", "Sunday,End")
         );

         for (String dayOfWeek : daysOfWeek) {
             BranchHourRowElementLinearLayout holder = new BranchHourRowElementLinearLayout(this);

             TextView dayTitle = (TextView) holder.getChildAt(0);
             Spinner hourChanger = (Spinner) holder.getChildAt(1);
             Spinner minuteChanger = (Spinner) holder.getChildAt(2);

             dayTitle.setText(dayOfWeek);
             this.hourChangeListLinearLayout.addView(holder);
         }


     }

     private void populateList(Map<String, String> mapOfHours) {

         ArrayList<String> daysOfWeek = new ArrayList<String>(
                 Arrays.asList("Monday,Start", "Monday,End",
                         "Tuesday,Start", "Tuesday,End",
                         "Wednesday,Start", "Wednesday,End",
                         "Thursday,Start", "Thursday,End",
                         "Friday,Start", "Friday,End",
                          "Saturday,Start", "Saturday,End",
                         "Sunday,Start", "Sunday,End")
         );

         for (String dayOfWeek : daysOfWeek) {
             BranchHourRowElementLinearLayout holder = new BranchHourRowElementLinearLayout(this, dayOfWeek);

             TextView dayTitle = (TextView) holder.getChildAt(0);
             Spinner hourChanger = (Spinner) holder.getChildAt(1);
             Spinner minuteChanger = (Spinner) holder.getChildAt(2);

             dayTitle.setText(dayOfWeek);
             hourChanger.setSelection(Integer.parseInt(mapOfHours.get(dayOfWeek).substring(0, 2)));
             minuteChanger.setSelection((int) Integer.parseInt(mapOfHours.get(dayOfWeek).substring(2)) / 5);
             this.hourChangeListLinearLayout.addView(holder);
         }


     }

     public void submitAndExit(View v) {
         boolean informationFilled = true;
         Toast.makeText(getApplicationContext(), Integer.toString(hourChangeListLinearLayout.getChildCount()), Toast.LENGTH_LONG).show();
         for (int i = 0; i < hourChangeListLinearLayout.getChildCount(); i++) {
             LinearLayout viewHolder = (LinearLayout) hourChangeListLinearLayout.getChildAt(i);
             TextView dayOfWeekText = (TextView) viewHolder.getChildAt(0);
             Spinner hourChanging = (Spinner) viewHolder.getChildAt(1);
             Spinner minuteChanging = (Spinner) viewHolder.getChildAt(2);

             String timeHolder = hourChanging.getSelectedItem().toString() + minuteChanging.getSelectedItem().toString();

             if(timeRangeHolder.containsKey(dayOfWeekText.getText().toString())) this.timeRangeHolder.remove(dayOfWeekText.getText().toString());
             this.timeRangeHolder.put(dayOfWeekText.getText().toString(), timeHolder);

         }

         if (!informationFilled) {
             Toast.makeText(getApplicationContext(), "Fill Out Information", Toast.LENGTH_LONG).show();
         } else {
             ref.child("mapOfHours").setValue(this.timeRangeHolder);
             finish();
         }
     }
 }
