package com.example.prototyped1.LayoutImplementations;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.EmployeeServiceSelectActivity;
import com.example.prototyped1.R;

public class EmployeeServiceList extends ArrayAdapter<Service>{

    private Activity context;
    List<Service> services;
    ArrayList<String> servicesOfferedIDs;

    public EmployeeServiceList(EmployeeServiceSelectActivity context, List<Service> services, ArrayList<String> servicesOfferedIDs) {
        super(context, R.layout.layout_select_service_list, services);
        this.context = context;
        this.services = services;
        this.servicesOfferedIDs = servicesOfferedIDs;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_select_service_list, null, true);

        Service service = services.get(position);
        CheckBox serviceBox = (CheckBox) listViewItem.findViewById(R.id.selectableService);
        serviceBox.setChecked(false);

        if(servicesOfferedIDs.contains(service.getId())){
            serviceBox.setChecked(true);
        }

        TextView serviceName = (TextView) listViewItem.findViewById(R.id.selectableService);


        serviceName.setText(service.getName());


        return listViewItem;
    }

    public boolean itemClicked(View view){
        CheckBox serviceCheck = (CheckBox) view;
        if(serviceCheck.isChecked()){
            System.out.println("\n\n\n\nChecked\n\n\n\n");
            return true;
        }
        return false;
    }
}
