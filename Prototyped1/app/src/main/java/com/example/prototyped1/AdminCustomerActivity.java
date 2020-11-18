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
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                String userID = null;
                try {
                    userID = mAuth.getCurrentUser().getUid();
                }catch(NullPointerException e){

                    String cid =  customer.getID();
                    String cEmail = customer.getEmail();
                    //String password = unhashPassword(customer.getPassword());
                    String password = customer.getPassword();
                    mAuth.signInWithEmailAndPassword(cEmail, password);
                    try{
                        //System.out.println("Testing");
                        Thread.sleep(5000);
                    }catch (InterruptedException t){}
                    while (!Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().equals(cEmail)){
                        //Wait for sign up to complete
                        try{
                            //System.out.println("Testing");
                            Thread.sleep(100);
                        }catch (InterruptedException t){}
                    }
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(cEmail)){
                        userID = mAuth.getCurrentUser().getUid();
                    }
                }
                showUpdateDeleteDialog(userID, customer.getEmail(),i);
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
                    Customer customer = postSnapshot.getValue(Customer.class);
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

    private void showUpdateDeleteDialog(final String customerID, String customerEmail, final int i) {

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
                deleteCustomer(customerID,i);
                b.dismiss();
            }
        });
    }

    private boolean deleteCustomer(String id, int i) { //TODO remove from authentication

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Customer").child(id);

        Customer customer = customers.get(i);

        String cid =  customer.getID();
        String cEmail = customer.getEmail();
        //String password = unhashPassword(customer.getPassword());
        String password = customer.getPassword();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(cEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
        while (!FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(cEmail)){
            //Wait for sign up to complete
            try{
                //System.out.println("Testing");
                Thread.sleep(100);
            }catch (InterruptedException e){}
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(cEmail)){
            user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete();
            db.removeValue();

        }

        //Toast.makeText(getApplicationContext(), "Branch Deleted", Toast.LENGTH_LONG).show();
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