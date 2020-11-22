package com.example.prototyped1;

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

import com.example.prototyped1.Customer;
import com.example.prototyped1.Employee;
import com.example.prototyped1.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CDisplayActivity extends AppCompatActivity {
    private UserAccount user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (UserAccount) getIntent().getSerializableExtra("USER_INFO");
//        getIntent().getSerializableExtra("")
        String userFirstName = user.getNameFirst();
        setContentView(R.layout.activity_customer_display);
        TextView message = (TextView) findViewById(R.id.messageDisplayID);
        if (user instanceof Customer){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a Customer");

        }
        if(user instanceof Employee){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a Branch");

            // user = employee ---> if branch phone, address == null, open dialog to set them
            Employee current = (Employee)user;
            // if address and phone not yet set
            if(current.address == null && current.phone == (null)) {
                showMandatoryInfoDialog(current);
            }
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

    private void updateMandatoryInfo(Employee e, String phone, String address) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Employees").child(e.getID());
        //make new employee with same names, email, password, id, but new phone and address
        Employee tmp =  new Employee(e.getNameFirst(), e.getNameLast(), e.getEmail(), e.getPassword(), e.getID(), phone, address);
        ref.setValue(tmp); //replace Employee with updated version

        Toast.makeText(getApplicationContext(), "Successfully updated Branch information", Toast.LENGTH_LONG).show();
    }

    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
