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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminCustomerActivity extends AppCompatActivity {

    private ListView customerlist; //customerList

    DatabaseReference databaseCustomers;

    List<Customer> customers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customer);

        databaseCustomers = FirebaseDatabase.getInstance().getReference("Customer");

        customers =new ArrayList<>();

        customerlist = (ListView) findViewById(R.id.list_of_customers);

        customerlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Customer customer = customers.get(i);

                showUpdateDeleteDialog(customer.getID(),customer.getEmail(),false);
                return true;
            }
        });

    }

    protected void onStart() {
        super.onStart();
        databaseCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customers.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String email = postSnapshot.child("email").getValue(String.class);
                    String password = postSnapshot.child("password").getValue(String.class);
                    String firstName = postSnapshot.child("nameFirst").getValue(String.class);
                    String lastName = postSnapshot.child("nameLast").getValue(String.class);
                    String id = postSnapshot.getKey();

                    Customer customer = new Customer(firstName,lastName,email,password,id);
                    customers.add(customer);
                }



                AdminCustomerList customerAdapter = new AdminCustomerList(AdminCustomerActivity.this,customers);
                customerlist.setAdapter(customerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String customerID, String customerEmail, final boolean isEmployee) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.admin_delete, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btn_delete);

        dialogBuilder.setTitle(customerEmail);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(customerID,isEmployee);
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