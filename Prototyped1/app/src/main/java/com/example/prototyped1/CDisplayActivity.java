package com.example.prototyped1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototyped1.ClassFiles.Customer;
import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.UserAccount;
import com.example.prototyped1.CustomerActivities.CustomerMainActivity;
import com.example.prototyped1.EmployeeActivities.BranchMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CDisplayActivity extends AppCompatActivity {
    private UserAccount user;
    private boolean dialogeBoxIsOpen = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        user = (UserAccount) getIntent().getSerializableExtra("USER_INFO");
//        getIntent().getSerializableExtra("")
        String userFirstName = user.getNameFirst();
        setContentView(R.layout.activity_customer_display);
        TextView message = (TextView) findViewById(R.id.messageDisplayID);
        if (user instanceof Customer){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a Customer");
            // cast user to Customer and send customer to CustomerMainActivity
            final Customer customer = (Customer)user;
            Intent intent = new Intent(getApplicationContext(), CustomerMainActivity.class);
            intent.putExtra("CUSTOMER", customer);
            startActivity(intent);
            finish();
        }
        if(user instanceof Employee){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a BranchEmployee");

            // user = employee ---> if branch phone, address == null, open dialog to set them
            final Employee current = (Employee)user;
            // if address and phone not yet set
            if(current.address == null && current.phone == null) {
                //dialog to update phone and address
                showMandatoryInfoDialog(current);
            }else{
                goToBranchMain(current);
            }
        }
    }


    private void showMandatoryInfoDialog(final Employee employee){

        dialogeBoxIsOpen = true;

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
                            dialogeBoxIsOpen = false;
                            d.dismiss();
                        }
                        //address not valid
                        else {
                            Toast.makeText(CDisplayActivity.this, "Invalid Address", Toast.LENGTH_LONG).show();
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
        //default branch hours are 0000
        Map<String, String> hours = new HashMap<>();

        hours.put("Monday,Start", "0000");
        hours.put("Monday,End", "0000");
        hours.put("Tuesday,Start", "0000");
        hours.put("Tuesday,End", "0000");
        hours.put("Wednesday,Start", "0000");
        hours.put("Wednesday,End", "0000");
        hours.put("Thursday,Start", "0000");
        hours.put("Thursday,End", "0000");
        hours.put("Friday,Start", "0000");
        hours.put("Friday,End", "0000");
        hours.put("Saturday,Start", "0000");
        hours.put("Saturday,End", "0000");
        hours.put("Sunday,Start", "0000");
        hours.put("Sunday,End", "0000");

        Employee tmp =  new Employee(e.getNameFirst(), e.getNameLast(), e.getEmail(), e.getPassword(), e.getID(), phone, address);
        ref.setValue(tmp); //replace Employee with updated version

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newHourReference = database.getReference("Employees/" + user.getID());
        newHourReference.child("mapOfHours").setValue(hours);

        Toast.makeText(getApplicationContext(), "Successfully updated Branch information", Toast.LENGTH_LONG).show();
        goToBranchMain(tmp);

    }


    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToBranchMain(Employee transferEmployee){
        final Employee holder = transferEmployee;
        DatabaseReference refEmployees = FirebaseDatabase.getInstance().getReference("Employees");
        refEmployees.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(holder.getID())) {
                    String email = dataSnapshot.child(holder.getID()).child("email").getValue(String.class);
                    String firstName = dataSnapshot.child(holder.getID()).child("nameFirst").getValue(String.class);
                    String lastName = dataSnapshot.child(holder.getID()).child("nameLast").getValue(String.class);
                    String pwd = dataSnapshot.child(holder.getID()).child("password").getValue(String.class);
                    String phone = dataSnapshot.child(holder.getID()).child("phone").getValue(String.class);
                    String address = dataSnapshot.child(holder.getID()).child("address").getValue(String.class);
                    Employee toBranchMain = new Employee(firstName, lastName, email, pwd, holder.getID(), phone, address);

                    //send updated employee to BranchMain
                    Intent intent = new Intent(getApplicationContext(), BranchMainActivity.class);
                    intent.putExtra("EMPLOYEE", toBranchMain);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
