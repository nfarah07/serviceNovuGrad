package com.example.prototyped1.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototyped1.AdministratorActivities.AdminEditServiceRequiredInformation;
import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.ClassFiles.ServiceRequest;
import com.example.prototyped1.LayoutImplementations.CustomerSearchBranchesList;
import com.example.prototyped1.LayoutImplementations.CustomerServiceRequiredInformationList;
import com.example.prototyped1.LayoutImplementations.ServiceRequiredInformationRowElement;
import com.example.prototyped1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//This activity changes the information required to be presented by clients when applying to a service
//this ability is only available to admins until future implementations

/**
 * This class is meant to allow customers to create a service request and submit it to a branch
 *
 * This class is given the branch that is offering the service, as well as the corresponding service associated with it
 *
 */
public class CustomerCreateServiceRequestActivity extends AppCompatActivity {

    private DatabaseReference dbBranches; //Used to connect to Firebase
    private DatabaseReference ref; //Used to connect to Firebase

    private Spinner branchNameSpinner; //Spinner to hold the branch name
    private Spinner branchServiceSpinner; //Once the user selects a branch from the spinner, this one is populated
    private ListView listOfCustomerInformation;

    //List of the branches to be populated
    private ArrayList<Employee> branchList = new ArrayList<Employee>();
    //List of branch names to be stored in the spinner
    private ArrayList<String> branchNames = new ArrayList<String>();
    //List of the services offered to be populated once a branch is selected
    private ArrayList<String> branchServicesOffered = new ArrayList<String>();

    private ArrayList<Service> serviceList = new ArrayList<Service>();
    private ArrayList<String> serviceNames = new ArrayList<String>();

    private Service serviceSelected;
    private Employee branchSelected;

    private FirebaseAuth mAuth;

    private static int RESULT_LOAD_IMAGE = 1;

//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageReference = storage.getReference();



    private ListView serviceRequiredInformationList;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_service_request);
        branchSelected = (Employee) getIntent().getSerializableExtra("EMPLOYEE_FOR_REQUEST");

        branchServiceSpinner = (Spinner) findViewById(R.id.branchServiceSpinner);
        branchNameSpinner = (Spinner) findViewById(R.id.branchNameSpinner);

        submitButton = (Button) findViewById(R.id.submitButton);

        listOfCustomerInformation = (ListView) findViewById(R.id.listOfCustomerInformation);

        dbBranches = FirebaseDatabase.getInstance().getReference().child("Employees"); //Get List of Employees

        populateBranchSpinner();

        branchNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Employee branchHolder = (Employee) branchList.get(i);
                branchSelected = branchHolder;
                populateServiceOfferedSpinner(branchSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("Services");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service serviceHolder = postSnapshot.getValue(Service.class);
                    serviceList.add(serviceHolder);
                    serviceNames.add(serviceHolder.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String errorMessage = "The read failed: " + databaseError.getCode();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
        ref = FirebaseDatabase.getInstance().getReference().child("Services");


        branchServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getChildCount() > 0){
                    String serviceName = adapterView.getItemAtPosition(i).toString();
                    Service serviceLoadHolder = serviceList.get(serviceNames.indexOf(serviceName));
                    serviceSelected = serviceList.get(serviceNames.indexOf(serviceName));

                    ArrayList<String> infoNameHolder = new ArrayList<String>();

                    if(serviceLoadHolder.getmapOfInformation() != null){
                        for(String keyHolder : serviceLoadHolder.getmapOfInformation().keySet()){
                            infoNameHolder.add(keyHolder);
                        }
                    }

                    CustomerServiceRequiredInformationList branchAdapter = new CustomerServiceRequiredInformationList(CustomerCreateServiceRequestActivity.this, serviceLoadHolder, infoNameHolder);
                    listOfCustomerInformation.setAdapter(branchAdapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        listOfCustomerInformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
//                View viewHolder = adapterView.getChildAt(i);
//
//                ImageView imageHolder = viewHolder.findViewById(R.id.imageViewServiceInfo);
//
//                if(imageHolder.getVisibility() == View.VISIBLE){
//                    Intent intent = new Intent(
//                            Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                    intent.putExtra("ServiceDetails", serviceDetails);
////                    intent.putExtra("ServiceID" , service.getId());
//
//                    startActivityForResult(intent, i);
//                }
//
////                finish();
//            }
//
////            @Override
////            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
////                Service service = services.get(i);
////                showAlterationDialog(service.getId());
////                return true;
////            }
//        });
    }

    protected void onStart() {
        super.onStart();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            View viewHolder = listOfCustomerInformation.getChildAt(requestCode);
//
//
//            ImageView imageView = (ImageView) viewHolder.findViewById(R.id.imageViewServiceInfo);
//            ImageView imageView1 = new ImageView(getApplicationContext());
//
//            try {
//                imageView.setImageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage)));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            listOfCustomerInformation.getChildAt(requestCode);
//            cursor.close();
//
//        }
//
//
//    }


    /**
     * Once the activity opens, we need a list of all the branches available1
     */
    private void populateBranchSpinner(){
        dbBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(searchHolder);
                    Employee branch = postSnapshot.getValue(Employee.class);
                    branchList.add(branch);
                }
                for(Employee tmp : branchList){
                    branchNames.add(tmp.getNameLast());
                }

                //Set adapter from list of branch names to the spinner
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_spinner_item,
                                branchNames); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                branchNameSpinner.setAdapter(spinnerArrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        if(branchSelected != null){
            branchNameSpinner.setSelection(branchNames.indexOf(branchSelected.getNameLast()));
        }
    }

    /**
     * Once a branch is selected, this method will populate the spinner with services offered by said branch
     * @param branchSelected
     */
    private void populateServiceOfferedSpinner(Employee branchSelected){
        branchServicesOffered.clear();
        dbBranches.child(branchSelected.getID()).child("Offered").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                branchServicesOffered.clear();
                branchServiceSpinner.setAdapter(null);
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(searchHolder);
                    if(postSnapshot != null) branchServicesOffered.add(postSnapshot.getKey());
                }
                if(!branchServicesOffered.isEmpty()){
                    //Set adapter from list of branch names to the spinner
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getApplicationContext(), android.R.layout.simple_spinner_item,
                                    branchServicesOffered); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                            .simple_spinner_dropdown_item);
                    branchServiceSpinner.setAdapter(spinnerArrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


    }


    public void submitAndExit(View v){
        boolean informationFilled = true;
        boolean ran = false;
        Map<String, Object> formResponses = new HashMap<String, Object>();
        for(int i = 0; i < listOfCustomerInformation.getChildCount(); i++){
            View viewHolder = listOfCustomerInformation.getChildAt(i);

            TextView textViewHolder = (TextView) viewHolder.findViewById(R.id.textViewServiceRequestInfo);
            EditText editTextServiceInfo = (EditText) viewHolder.findViewById(R.id.editTextServiceInfo);
            EditText editTextNumberServiceInfo = (EditText) viewHolder.findViewById(R.id.editTextNumberServiceInfo);


            if(editTextServiceInfo.getText().toString() == null && editTextNumberServiceInfo.getText().toString().equals("") && editTextNumberServiceInfo.getText().toString() == null && editTextServiceInfo.getText().toString().equals("")){
                informationFilled = false;
            }else{
                if(editTextServiceInfo.getVisibility() == View.VISIBLE){
                    String holder = editTextServiceInfo.getText().toString();
                    formResponses.put(textViewHolder.getText().toString(), holder);
                }else if(editTextNumberServiceInfo.getVisibility() == View.VISIBLE){
                    double numHolder = Double.parseDouble(editTextServiceInfo.getText().toString());
                    formResponses.put(textViewHolder.getText().toString(), numHolder);
                }
            }
        }

        if(listOfCustomerInformation.getChildCount() > 0){
            mAuth = FirebaseAuth.getInstance();
            ref = FirebaseDatabase.getInstance().getReference().child("ServiceRequests");
            if(!informationFilled) {
                Toast.makeText(getApplicationContext(), "Fill Out Information", Toast.LENGTH_LONG).show();
            }else{
                String serviceRequestID = ref.push().getKey();
                String customerID = mAuth.getUid();
                String branchID = branchSelected.getID();
                String serviceName = serviceSelected.getName();
                ServiceRequest newServiceRequest = new ServiceRequest(serviceRequestID, customerID, branchID, serviceName,formResponses, "pending");

                ref.child(newServiceRequest.getId()).setValue(newServiceRequest);
                ref.child(newServiceRequest.getId()).child("FormResponses").setValue(formResponses);
                finish();
            }

            ref = FirebaseDatabase.getInstance().getReference().child("Services");
        }

    }
}