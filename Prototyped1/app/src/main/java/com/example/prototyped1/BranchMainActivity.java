package com.example.prototyped1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BranchMainActivity extends AppCompatActivity {
    private Employee fromCD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_main);
        //gets employee from CDisplayActivity
        fromCD = (Employee) getIntent().getSerializableExtra("EMPLOYEE");
    }


    public void onServicesOffered(View view){
//        Employee tmp = new Employee("employee", "employee", "employee@email.com", "employee", "u84RkxHlJIXTuU0udUeaXxxboN72", "1234567890", "2 Nowhere Lane" ); // Hardcoded for testing with an employee
        Employee tmp = new Employee("BigNe", "wTest", "newtes@hotmail.ca", "1234567", "FWcEDlygrMhDSAaSyjDghRS4gT33", "1234567890", "5 Losers Lane" ); // Hardcoded for testing with an employee

        Intent intent = new Intent(getApplicationContext(), EmployeeServiceSelectActivity.class);
        intent.putExtra("USER_INFO", fromCD);
        startActivity(intent);
        finish();
    }



        /*
    public void onServiceRequests(View view){
        Intent intent = new Intent(getApplicationContext(), BranchServiceRequests.class);
        startActivity(intent);
        finish();
    }

     */

    /*
    public void onHoursOpen(View view){
        Intent intent = new Intent(getApplicationContext(), BranchChangeHour.class);
        startActivity(intent);
        finish();
    }
    */
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
        Employee tmp =  new Employee(emp.getNameFirst(), emp.getNameLast(), emp.getEmail(), emp.getPassword(), emp.getID(), phone, address);
        ref.setValue(tmp); //replace Employee with updated version

        Toast.makeText(getApplicationContext(), "Successfully updated Branch information", Toast.LENGTH_LONG).show();
    }
}