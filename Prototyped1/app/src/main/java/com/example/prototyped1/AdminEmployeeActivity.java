
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
import com.google.firebase.database.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

                showUpdateDeleteDialog(employee.getID(),employee.getEmail(),true);
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
                    String email = postSnapshot.child("email").getValue(String.class);
                    String password = postSnapshot.child("password").getValue(String.class);
                    String firstName = postSnapshot.child("nameFirst").getValue(String.class);
                    String lastName = postSnapshot.child("nameLast").getValue(String.class);
                    String id = postSnapshot.getKey();

                    Employee employee = new Employee(firstName,lastName,email,password,id);
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

    private void showUpdateDeleteDialog(final String employeeID, String employeeEmail, final boolean isEmployee) {

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
                deleteUser(employeeID,isEmployee);
                b.dismiss();
            }
        });
    }

    private void deleteUser(String id, boolean isEmployee){
        DatabaseReference dR;
        if( isEmployee){
            dR = FirebaseDatabase.getInstance().getReference("Employees").child(id);
        } else {
            dR = FirebaseDatabase.getInstance().getReference("Customer").child(id);
        }
        dR.removeValue();
        Toast.makeText(this, "User Deleted", Toast.LENGTH_LONG).show();
    }


    public void onToWelcome(View view) {
        Admin adminMain = new Admin("admin", "admin");
        Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);   //Application Context and Activity
        intent.putExtra("USER_INFO", adminMain);
        startActivity(intent);
        finish();
    }
}