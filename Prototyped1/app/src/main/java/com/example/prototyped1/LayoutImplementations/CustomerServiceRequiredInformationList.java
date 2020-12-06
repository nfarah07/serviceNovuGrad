package com.example.prototyped1.LayoutImplementations;

import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class CustomerServiceRequiredInformationList extends ArrayAdapter<String> {

    private Activity context;
    private Service service;
    List<String> keyNames;
    List<String> valueTypes;

    public CustomerServiceRequiredInformationList(Activity context, Service service, ArrayList<String> keyNames) {
        super(context, R.layout.layout_service_required_information_list, keyNames);
        this.context = context;
        this.keyNames = keyNames;
//        this.valueTypes = valueTypes;
        this.service = service;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_service_required_information_list, null, true);

        TextView textViewServiceRequestInfo = (TextView) listViewItem.findViewById(R.id.textViewServiceRequestInfo);
        EditText editTextServiceInfo = (EditText) listViewItem.findViewById(R.id.editTextServiceInfo);
        EditText editTextNumberServiceInfo = (EditText) listViewItem.findViewById(R.id.editTextNumberServiceInfo);
        ImageView imageViewServiceInfo = (ImageView) listViewItem.findViewById(R.id.imageViewServiceInfo);
        CheckBox checkBoxServiceRequestInfo = (CheckBox) listViewItem.findViewById(R.id.checkBoxServiceRequestInfo);



        if(service.getmapOfInformation() != null){
            switch (service.getmapOfInformation().get(keyNames.get(position))) {
                case "Integer":
                    textViewServiceRequestInfo.setText(keyNames.get(position));
                    checkBoxServiceRequestInfo.setVisibility(GONE);
                    editTextServiceInfo.setVisibility(GONE);
                    imageViewServiceInfo.setVisibility(GONE);
                    break;
                case "String":
                    textViewServiceRequestInfo.setText(keyNames.get(position));
                    checkBoxServiceRequestInfo.setVisibility(GONE);
                    editTextNumberServiceInfo.setVisibility(GONE);
                    imageViewServiceInfo.setVisibility(GONE);
                    break;
                case "Boolean":
                    textViewServiceRequestInfo.setText(keyNames.get(position));
                    textViewServiceRequestInfo.setVisibility(GONE);
                    editTextNumberServiceInfo.setVisibility(GONE);
                    imageViewServiceInfo.setVisibility(GONE);
                    break;
                case "Double":
                    textViewServiceRequestInfo.setText(keyNames.get(position));
                    checkBoxServiceRequestInfo.setVisibility(GONE);
                    editTextServiceInfo.setVisibility(GONE);
                    imageViewServiceInfo.setVisibility(GONE);
                    break;
                case "Map":

                    break;
                case "File":
                    textViewServiceRequestInfo.setText(keyNames.get(position));
                    checkBoxServiceRequestInfo.setVisibility(GONE);
                    editTextNumberServiceInfo.setVisibility(GONE);
                    imageViewServiceInfo.setVisibility(GONE);
                    break;
            }
        }




//        TextView branchResultName = (TextView) listViewItem.findViewById(R.id.resultBranchName);
//        TextView branchResultPhone = (TextView) listViewItem.findViewById(R.id.resultBranchPhone);
//        TextView branchResultAddress = (TextView) listViewItem.findViewById(R.id.resultBranchAddress);
//        Employee entry = resultingBranches.get(position);
//        branchResultName.setText(entry.getNameFirst()+" "+entry.getNameLast());
//        branchResultAddress.setText(entry.getAddress());
//        branchResultPhone.setText(entry.getPhone());
        return listViewItem;
    }

    public void addPhoto(){

    }
}