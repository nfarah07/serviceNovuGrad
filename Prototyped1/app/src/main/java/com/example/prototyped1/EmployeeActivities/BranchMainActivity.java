package com.example.prototyped1.EmployeeActivities;

import android.content.Intent;
import android.os.Bundle;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class BranchMainActivity extends AppCompatActivity {
    private Employee fromCD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_main);
        //gets employee from CDisplayActivity
        fromCD = (Employee) getIntent().getSerializableExtra("EMPLOYEE");
        String userfirstname = fromCD.getNameFirst();
        String userlastname = fromCD.getNameLast();
        TextView message = (TextView) findViewById(R.id.messageDisplayBranchName);
        message.setText(userfirstname+" "+userlastname);

        // if address and phone not yet set
        if(fromCD.address == null && fromCD.phone == null) {
            showMandatoryInfoDialog(fromCD);
        }

    }

    private void showMandatoryInfoDialog(final Employee employee){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.e_mandatory_info_dialog, null);
        builder.setView(dialogView);
        final EditText employeePhone = (EditText) dialogView.findViewById(R.id.editTextPhone);
        final EditText employeeAddress = (EditText) dialogView.findViewById(R.id.editTextTextPostalAddress);
        final Button saveBtn = (Button) dialogView.findViewById(R.id.button2);
        builder.setTitle("Mandatory Branch Information");
        final AlertDialog d = builder.create();
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        d.show();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = employeePhone.getText().toString().trim();
                String address = employeeAddress.getText().toString().trim();
                // ensure something is entered
                if(!(phone.isEmpty()) && !(address.isEmpty())){
                    //validate phone, only numbers and only 10 of them
                    if(phone.matches("[0-9]{10}")){
                        //validate address, has no special characters (that's what the prof said on Piazza)
                        if(address.matches("^[a-zA-Z0-9 ]+$")){
                            updateMandatoryInfo(employee, phone, address);
                            d.dismiss();
                        }
                        //address not valid
                        else {
                            Toast.makeText(BranchMainActivity.this, "Invalid Address", Toast.LENGTH_LONG).show();
                        }
                    }
                    // phone number wrong
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    }
                }
                // nothing was entered
                else{
                    Toast.makeText(getApplicationContext(), "You must enter a value for phone and address", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void updateMandatoryInfo(Employee e, String phone, String address) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Employees").child(e.getID());
        //make new employee with same names, email, password, id, but new phone and address
        Map<String, String> openHours = new HashMap<>();

        openHours.put("Monday,Start", "0000");
        openHours.put("Monday,End", "0000");
        openHours.put("Tuesday,Start", "0000");
        openHours.put("Tuesday,End", "0000");
        openHours.put("Wednesday,Start", "0000");
        openHours.put("Wednesday,End", "0000");
        openHours.put("Thursday,Start", "0000");
        openHours.put("Thursday,End", "0000");
        openHours.put("Friday,Start", "0000");
        openHours.put("Friday,End", "0000");
        openHours.put("Saturday,Start", "0000");
        openHours.put("Saturday,End", "0000");
        openHours.put("Sunday,Start", "0000");
        openHours.put("Sunday,End", "0000");

        //Employee tmp =  new Employee(e.getNameFirst(), e.getNameLast(), e.getEmail(), e.getPassword(), e.getID(), phone, address);
        Employee tmp =  new Employee(e.getNameFirst(), e.getNameLast(), e.getEmail(), e.getPassword(), e.getID(), phone, address, openHours);
        ref.setValue(tmp); //replace Employee with updated version
        Toast.makeText(getApplicationContext(), "Successfully updated Branch information", Toast.LENGTH_LONG).show();

        fromCD = tmp;

    }


    public void onServicesOffered(View view){
//        Employee tmp = new Employee("employee", "employee", "employee@email.com", "employee", "u84RkxHlJIXTuU0udUeaXxxboN72", "1234567890", "2 Nowhere Lane" ); // Hardcoded for testing with an employee
//        Employee tmp = new Employee("BigNe", "wTest", "newtes@hotmail.ca", "1234567", "FWcEDlygrMhDSAaSyjDghRS4gT33", "1234567890", "5 Losers Lane" ); // Hardcoded for testing with an employee

        Intent intent = new Intent(getApplicationContext(), EmployeeServiceSelectActivity.class);
        intent.putExtra("USER_INFO", fromCD);
        startActivity(intent);
    }


    public void onServiceRequests(View view) {
        //TODO : The below commented code was used to create
        // instances of Service Request for testing. The code works for displaying service request.
//        String id = FirebaseDatabase.getInstance().getReference("ServiceRequests").push().getKey();
//        Map<String, Object> holder = new HashMap<String, Object>();
//        holder.put("FirsName","Kanye");
//        holder.put("LastName","East");
//        ServiceRequest req1 = new ServiceRequest(id, "J2h0IBRuyZZpLRopNyHEM8J19Id2", "FWcEDlygrMhDSAaSyjDghRS4gT33", "-MM7xnHr2I5kH-_PkuXQ", holder);
//        FirebaseDatabase.getInstance().getReference("ServiceRequests").child(id).setValue(req1);
//        FirebaseDatabase.getInstance().getReference("ServiceRequests").child(id).child("FormResponses").setValue(holder);

        Intent intent = new Intent(getApplicationContext(), BranchRequestHandlingActivity.class);
        intent.putExtra("BRANCH", fromCD);
        startActivity(intent);
    }




    public void onHoursOpen(View view){
        Intent intent = new Intent(getApplicationContext(), BranchChangeHoursActivity.class);
        intent.putExtra("EMPLOYEE", fromCD);
        startActivity(intent);
    }

    public void onUpdateInfo(View view) {
        //when you click the button on BranchMain
        updateInfoDialog(fromCD);
    }

    private void updateInfoDialog(Employee e) {
        //makes dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.e_mandatory_info_dialog, null);
        builder.setView(dialogView);
        final EditText employeePhone = (EditText) dialogView.findViewById(R.id.editTextPhone);
        final EditText employeeAddress = (EditText) dialogView.findViewById(R.id.editTextTextPostalAddress);
        final Button saveBtn = (Button) dialogView.findViewById(R.id.button2);
        builder.setTitle("Update Branch Information");
        final AlertDialog d = builder.create();
        d.setCancelable(true);
        d.setCanceledOnTouchOutside(true);
        d.show();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = employeePhone.getText().toString().trim();
                String address = employeeAddress.getText().toString().trim();
                //makes sure they are not empty
                if(!(phone.isEmpty()) && !(address.isEmpty())){
                    //validate phone, only numbers and only 10 of them
                    if(phone.matches("[0-9]{10}")){
                        //validate address, has no special characters (that's what the prof said on Piazza)
                        if(address.matches("^[a-zA-Z0-9 ]+$")){
                            updateBranchInfo(fromCD, phone, address);
                            d.dismiss();
                        }
                        //address not valid
                        else { Toast.makeText(getApplicationContext(), "Invalid Address", Toast.LENGTH_LONG).show(); }
                    }
                    // phone number wrong
                    else { Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show(); }
                }
                // nothing was entered
                else{ Toast.makeText(getApplicationContext(), "You must enter a value for phone and address", Toast.LENGTH_LONG).show(); }
            }
        });
    }

    private void updateBranchInfo(Employee emp, String phone, String address) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Employees").child(emp.getID());
        //make new employee with same names, email, password, id, but new phone and address
        Employee tmp =  new Employee(emp.getNameFirst(), emp.getNameLast(), emp.getEmail(), emp.getPassword(), emp.getID(), phone, address, emp.getOpenHours());
        ref.setValue(tmp); //replace Employee with updated version

        Toast.makeText(getApplicationContext(), "Successfully updated Branch information", Toast.LENGTH_LONG).show();
    }
}