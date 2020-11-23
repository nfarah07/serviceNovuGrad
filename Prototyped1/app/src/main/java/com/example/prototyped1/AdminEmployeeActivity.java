
package com.example.prototyped1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prototyped1.ClassFiles.Admin;
import com.example.prototyped1.ClassFiles.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminEmployeeActivity extends AppCompatActivity {

    private ListView employeelist;

    DatabaseReference databaseEmployees;

    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employee);

        employeelist = (ListView) findViewById(R.id.list_of_employees);
        databaseEmployees = FirebaseDatabase.getInstance().getReference("Employees");
        employees =new ArrayList<>();

        employeelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Employee employee = employees.get(i);

                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                String userID = null;
                try {
                    userID = mAuth.getCurrentUser().getUid();
                }catch(NullPointerException e){

                    String eid =  employee.getID();
                    String eEmail = employee.getEmail();
                    //String password = unhashPassword(customer.getPassword());
                    String password = employee.getPassword();
                    mAuth.signInWithEmailAndPassword(eEmail, password);
                    try{
                        //System.out.println("Testing");
                        Thread.sleep(5000);
                    }catch (InterruptedException t){}
                    while (!Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().equals(eEmail)){
                        //Wait for sign up to complete
                        try{
                            //System.out.println("Testing");
                            Thread.sleep(100);
                        }catch (InterruptedException t){}
                    }
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(eEmail)){
                        userID = mAuth.getCurrentUser().getUid();
                    }
                }

                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Employees").child(userID);
                showUpdateDeleteDialog(employee.getID(),employee.getEmail(),i);
                return true;
            }

        });

        // same thing could be done here for customers!!!

    }

    protected void onStart() {
        super.onStart();
        databaseEmployees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employees.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Employee employee = postSnapshot.getValue(Employee.class);
                    employees.add(employee);
                    /**
                     Employee employee = postSnapshot.getValue(Employee.class);
                     employees.add(employee);*/
                }

                AdminEmployeeList employeeAdapter = new AdminEmployeeList(AdminEmployeeActivity.this, employees);
                employeelist.setAdapter(employeeAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // could be done for customer right here as well !!!!
    }

    private void showUpdateDeleteDialog(final String employeeID, String employeeEmail, final int i) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.admin_delete, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btn_delete);

        dialogBuilder.setTitle(employeeEmail);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(employeeID,i);
                b.dismiss();
            }
        });
    }

    private boolean deleteEmployee(final String id, int i) { //TODO remove from authentication

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Employees").child(id);

        Employee employee = employees.get(i);

        String eid = employee.getID();
        String eEmail = employee.getEmail();
        //String password = unhashPassword(employee.getPassword());
        String password = employee.getPassword();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(eEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Branch successfully Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Deletion has failed!", Toast.LENGTH_LONG).show();
                }
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getDisplayName();
        while (!FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(eEmail)) {
            //Wait for sign up to complete
            try {
                //System.out.println("Testing");
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(eEmail)) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete();
            db.removeValue();
        }
        return true;
    }

    /* Does not work as our encryption is too strong
    private String unhashPassword(String password) {
        try {
            // Returns a MessageDigest object that implements the specified digest algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Updates the digest using the specified byte.
            md.update(password.getBytes());
            // RETURNS THE ARRAY OF BYTES

            byte[] digestedBytes = md.digest();
            // Create a container that stores the hexCodes

            StringBuilder hexDigest = new StringBuilder();
            for (byte digestedByte : digestedBytes) {
                hexDigest.append(Integer.toString((digestedByte & 0xff) + 0x100, 16).substring(1));
            }
            return hexDigest.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

     */

    public void onToWelcome(View view) {
        Admin adminMain = new Admin("admin", "admin");
        Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);   //Application Context and Activity
        intent.putExtra("USER_INFO", adminMain);
        startActivity(intent);
        finish();
    }
}