package com.example.prototyped1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototyped1.LayoutImplementations.ServiceRequiredInformationRowElement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//This activity changes the information required to be presented by clients when applying to a service
//this ability is only available to admins until future implementations
public class AdminEditServiceRequiredInformation extends AppCompatActivity {

    DatabaseReference ref; //Used to connect to Firebase

    private LinearLayout serviceInformationList;
    private Button addServiceInformation;
    private Button submitButton;
    private TextView editServiceRequiredInformationTitle;
    private String serviceId;
    private ArrayList<String> serviceDetails;
    private Map<String,String> informationHolder = new HashMap<String,String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_service_required_information);

        //(name, id, tmpPrice, form, documents)
        this.serviceDetails = (ArrayList<String>) getIntent().getSerializableExtra("ServiceDetails");
        this.serviceId = (String) getIntent().getSerializableExtra("ServiceID");
//
        //Set the title of the service edit page to be the name of the service
        editServiceRequiredInformationTitle = (TextView) findViewById(R.id.decideServiceRequestTitle);
        editServiceRequiredInformationTitle.setText(serviceDetails.get(0));

        ref = FirebaseDatabase.getInstance().getReference().child("Services"); //Get List of Services

        //Get list to view information regarding service
        serviceInformationList = (LinearLayout) findViewById(R.id.serviceRequestInformationList);

        //Get a reference to the service that is being edited
        ref = FirebaseDatabase.getInstance().getReference().child("Services").child(this.serviceId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //For some reason this had to be done, I tried using Map<String, Object> and troubleshooted but nothing works
                //TODO Fix how to send maps to the database
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                informationHolder = dataSnapshot.child("mapOfInformation").getValue(genericTypeIndicator);
                if(informationHolder != null) populateList();//If the map isn't empty or non-existent, pull from the database
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String errorMessage = "The read failed: " + databaseError.getCode();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
        ref = FirebaseDatabase.getInstance().getReference().child("Services");

        for(int i= 0 ; i < serviceInformationList.getChildCount() ; i++){
            View view  = serviceInformationList.getChildAt(i);
            view.setTag(i);
            ServiceRequiredInformationRowElement holder = (ServiceRequiredInformationRowElement) view;
            View buttonDeleteInformation = holder.getChildAt(2);
            buttonDeleteInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_LONG).show();
                    LinearLayout holder = (LinearLayout) view.getParent();
                    EditText inputHolder = (EditText) holder.getChildAt(0);
                    informationHolder.remove(inputHolder.getText().toString());
                    serviceInformationList.removeView(view);
                }
            });
        }
    }

    protected void onStart() {
        super.onStart();

    }

    private void populateList(){
        for(String name : this.informationHolder.keySet()){
            ServiceRequiredInformationRowElement holder = new ServiceRequiredInformationRowElement(this);

            EditText inputHolder = (EditText) holder.getChildAt(0);
            Spinner typeHolder = (Spinner) holder.getChildAt(1);

            inputHolder.setText(name);

            switch((String) this.informationHolder.get(name)){
                case "String":
                    typeHolder.setSelection(0);
                    break;
                case "Double":
                    typeHolder.setSelection(1);
                    break;
                case "Integer":
                    typeHolder.setSelection(2);
                    break;
                case "Boolean":
                    typeHolder.setSelection(3);
                    break;
                case "Map":
                    typeHolder.setSelection(4);
                    break;
                case "Image":
                    typeHolder.setSelection(5);
                    break;
            }
            holder.getChildAt(2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_LONG).show();
                    LinearLayout holder = (LinearLayout) view.getParent();
                    EditText inputHolder = (EditText) holder.getChildAt(0);
                    informationHolder.remove(inputHolder.getText().toString());
                    serviceInformationList.removeView(holder);
                }
            });
            this.serviceInformationList.addView(holder);


        }
    }

    public void addNewServiceInformation(View view){
        ServiceRequiredInformationRowElement holder = new ServiceRequiredInformationRowElement(this);
        this.serviceInformationList.addView(holder);
    }

    public void submitAndExit(View v){
        boolean informationFilled = true;
        if(informationHolder == null) informationHolder = new HashMap<String,String>();
        Toast.makeText(getApplicationContext(), Integer.toString(serviceInformationList.getChildCount()), Toast.LENGTH_LONG).show();
        for(int i = 0; i < serviceInformationList.getChildCount(); i++){
            LinearLayout viewHolder = (LinearLayout) serviceInformationList.getChildAt(i);
            EditText inputHolder = (EditText) viewHolder.getChildAt(0);
            Spinner typeHolder = (Spinner) viewHolder.getChildAt(1);

            if(inputHolder.getText().toString() == null || inputHolder.getText().toString().equals("")){
                informationFilled = false;
            }else{
                informationHolder.put(inputHolder.getText().toString(),typeHolder.getSelectedItem().toString());
            }
        }

        if(!informationFilled) {
            Toast.makeText(getApplicationContext(), "Fill Out Information", Toast.LENGTH_LONG).show();
        }else{
            ref.child(this.serviceId).child("mapOfInformation").setValue(this.informationHolder);
            finish();
        }
    }
}