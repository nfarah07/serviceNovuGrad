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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCustomerActivity extends AppCompatActivity {

    private ListView list2;

    DatabaseReference databaseCustomers;

    List<Customer> customers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customer);

        databaseCustomers = FirebaseDatabase.getInstance().getReference("Customer");

        customers =new ArrayList<>();

        list2 = (ListView) findViewById(R.id.list_of_customers);

        list2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Customer customer = customers.get(i);
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                String userID = mAuth.getCurrentUser().getUid();
                showUpdateDeleteDialog(userID, customer.getEmail());
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
                list2.setAdapter(customerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String customerID, String customerEmail) {

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
                deleteCustomer(customerID);
                b.dismiss();
            }
        });
    }

    private boolean deleteCustomer(String id) { //TODO remove from authentication

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Customer").child(id);


        db.removeValue();
        Toast.makeText(getApplicationContext(), "Customer Deleted", Toast.LENGTH_LONG).show();
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