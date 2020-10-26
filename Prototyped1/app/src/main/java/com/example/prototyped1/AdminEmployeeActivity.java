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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminEmployeeActivity extends AppCompatActivity {

    private ListView list;

    DatabaseReference databaseEmployees;

    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_employee);

        databaseEmployees = FirebaseDatabase.getInstance().getReference("Employees");

        employees =new ArrayList<>();

        list = (ListView) findViewById(R.id.list_of_employees);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Employee employee = employees.get(i);
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                String userID = mAuth.getCurrentUser().getUid();
                showUpdateDeleteDialog(userID, employee.getEmail());
                return true;
            }
        });

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
                }

                AdminEmployeeList employeeAdapter = new AdminEmployeeList(AdminEmployeeActivity.this, employees);
                list.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String employeeID, String employeeEmail) {

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
                deleteEmployee(employeeID);
                b.dismiss();
            }
        });
    }

    private boolean deleteEmployee(String id) { //TODO remove from authentication

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Employees").child(id);

        db.removeValue();
        Toast.makeText(getApplicationContext(), "Branch Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    public void onToWelcome(View view) {
        Admin adminMain = new Admin("admin", "admin");
        Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);   //Application Context and Activity
        intent.putExtra("USER_INFO", adminMain);
        startActivity(intent);
        finish();
    }
}