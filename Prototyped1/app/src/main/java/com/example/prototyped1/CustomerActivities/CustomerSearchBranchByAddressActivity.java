package com.example.prototyped1.CustomerActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerSearchBranchByAddressActivity extends AppCompatActivity {

    //instance variables
    private EditText branchAddressSearch;
    private ListView branchesContainingSearch;
    public ArrayAdapter<String> adapter;
    private DatabaseReference dbBranches;
    private final ArrayList<Employee> employeeList =  new ArrayList<Employee>();
    private final ArrayList<String> employeeInfo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search_by_branch_address);
        branchAddressSearch = (EditText) findViewById(R.id.editTextBranchAddress);
        branchesContainingSearch = (ListView) findViewById(R.id.listViewBranchesByAddress);

        adapter=new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                employeeInfo) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return super.getView(position, convertView, parent);
            }
        };
        branchesContainingSearch.setAdapter(adapter);

        dbBranches = FirebaseDatabase.getInstance().getReference("Employees");

        this.branchAddressSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchByAddress(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    public void searchByAddress(final String searchHolder){
        employeeList.clear();
        employeeInfo.clear();  //branch address list
        dbBranches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(searchHolder);
                    Employee branch = postSnapshot.getValue(Employee.class);
//                    String addressHolder = postSnapshot.child("address").getValue().toString();
                    if(branch.address != null){
                        final String holder = searchHolder;
                        if(branch.address.toLowerCase().contains(searchHolder.toLowerCase()))  {
                            //System.out.println("f");
                            employeeList.add(branch);
                        }
                    }
                }
                for(Employee tmp : employeeList){
                    employeeInfo.add(tmp.getAddress());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}