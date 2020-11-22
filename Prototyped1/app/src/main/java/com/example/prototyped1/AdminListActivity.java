package com.example.prototyped1;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;

import com.example.prototyped1.Account;
import com.example.prototyped1.Admin;
import com.example.prototyped1.Customer;
import com.example.prototyped1.Employee;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminListActivity extends AppCompatActivity {

    private ListView list3;

    DatabaseReference ref;

    List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        ref = FirebaseDatabase.getInstance().getReference().child("Services");

        services = new ArrayList<>();

        list3 = (ListView) findViewById(R.id.list_of_services);

        list3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                Service service = services.get(i);
                showAlterationDialog(service.getId());
                return true;
            }
        });

        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Service service = services.get(i);
                ArrayList<String> serviceDetails = new ArrayList<String>(
                        Arrays.asList(service.getName(), service.getId(), String.valueOf(service.getPrice()), service.getForm(), service.getDocuments())
                );
                Intent intent = new Intent(getApplicationContext(), AdminCreateServiceActivity.class);   //Application Context and Activity
                intent.putExtra("ServiceDetails", serviceDetails);
                intent.putExtra("ServiceID" , service.getId());

                startActivity(intent);//, ProfileActivity.REQUEST_NEW_TEAM);
//                finish();
            }

//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
//                Service service = services.get(i);
//                showAlterationDialog(service.getId());
//                return true;
//            }
        });

        //adminUser = (Admin) getIntent().getSerializableExtra("USER_INFO");

        //val listView = findViewById<ListView>(R.id.listViewServices);
    }

    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }

                AdminServiceList serviceAdapter = new AdminServiceList(AdminListActivity.this,services);
                list3.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showAddNewDialog(View view) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.admin_add_service, null);
        dialogBuilder.setView(dialogView);

        final EditText nameEdit = (EditText) dialogView.findViewById(R.id.serviceNameEnter);
        final EditText priceEdit = (EditText) dialogView.findViewById(R.id.servicePriceEnter);
        final EditText formEdit = (EditText) dialogView.findViewById(R.id.serviceFormEnter);
        final EditText documentsEdit = (EditText) dialogView.findViewById(R.id.serviceDocumentsEnter);
        final Button buttonCreate = (Button) dialogView.findViewById(R.id.serviceCreateButton);

        dialogBuilder.setTitle("Create a service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameEdit.getText().toString().trim();
                final String form = formEdit.getText().toString().trim();
                final String documents = documentsEdit.getText().toString().trim();
                final String tmpPrice = priceEdit.getText().toString().trim();
                if(tmpPrice.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Price Required", Toast.LENGTH_LONG).show();
                    return;
                }
                int indexOfDec = tmpPrice.indexOf(".");
                if(indexOfDec >= 0) {
                    if(tmpPrice.substring(indexOfDec).length() >3) {
                        Toast.makeText(getApplicationContext(), "Price Invalid", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if(TextUtils.isEmpty(form)){
                    Toast.makeText(getApplicationContext(), "Information On Form Required", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(documents)){
                    Toast.makeText(getApplicationContext(), "Documents Required", Toast.LENGTH_LONG).show();
                    return;
                }
                final double price = Double.parseDouble(priceEdit.getText().toString());
                if(!TextUtils.isEmpty(name)) {
                    final String id = ref.push().getKey();
                    createService(name, id, price, form, documents);
                    Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_LONG).show();
                    b.dismiss();

                    ArrayList<String> serviceDetails = new ArrayList<String>(
                            Arrays.asList(name, id, tmpPrice, form, documents)
                    );
                    Intent intent = new Intent(getApplicationContext(), AdminCreateServiceActivity.class);   //Application Context and Activity
                    intent.putExtra("ServiceDetails", serviceDetails);
                    intent.putExtra("ServiceID" , id);

                    startActivity(intent);//, ProfileActivity.REQUEST_NEW_TEAM);
//                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Name Required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showAlterationDialog(String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.admin_edit_service, null);
        dialogBuilder.setView(dialogView);

        final EditText nameAlter = (EditText) dialogView.findViewById(R.id.serviceNameChange);
        final EditText priceAlter = (EditText) dialogView.findViewById(R.id.servicePriceChange);
        final EditText formAlter = (EditText) dialogView.findViewById(R.id.serviceFormsChange);
        final EditText documentsAlter = (EditText) dialogView.findViewById(R.id.serviceDocumentsChange);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.serviceUpdateButton);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.serviceDeleteButton);

        final String idNo = id;

        dialogBuilder.setTitle("Edit or delete a service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameAlter.getText().toString().trim();
                final String form = formAlter.getText().toString().trim();
                final String documents = documentsAlter.getText().toString().trim();
                final String tmpPrice = priceAlter.getText().toString();
                if(tmpPrice.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Price Required", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(form)){
                    Toast.makeText(getApplicationContext(), "Information On Form Required", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(documents)){
                    Toast.makeText(getApplicationContext(), "Documents Required", Toast.LENGTH_LONG).show();
                    return;
                }
                int indexOfDec = tmpPrice.indexOf(".");
                if(indexOfDec >= 0) {
                    if(tmpPrice.substring(indexOfDec).length() >3) {
                        Toast.makeText(getApplicationContext(), "Price Invalid", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                final double price = Double.parseDouble(priceAlter.getText().toString());
                if(!TextUtils.isEmpty(name)) {
                    ref.child(idNo).child("name").setValue(name);
                    ref.child(idNo).child("price").setValue(price);
                    ref.child(idNo).child("documents").setValue(documents);
                    ref.child(idNo).child("form").setValue(form);
                    Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();
                    b.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Name Required", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child(idNo).removeValue();
                Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
                b.dismiss();
            }
        });

    }

    public void onToWelcome(View view){
        Admin adminMain = new Admin("admin", "admin");
        Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);   //Application Context and Activity
        intent.putExtra("USER_INFO",  adminMain);
        startActivity(intent);
        finish();
    }

    private boolean createService(String name, String id, double price, String form, String documents) {
        Service newService = new Service(name, id, price, form, documents);
        ref.child(id).setValue(newService);
        return true;
    }


}
