package com.example.prototyped1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EmployeeRequestList extends ArrayAdapter<ServiceRequest> {
    private Activity context;
    List<ServiceRequest> serviceRequestsList;

    public EmployeeRequestList(BranchRequestHandlingActivity context, List<ServiceRequest> serviceRequestsList) {
        super(context, R.layout.layout_employee_request_list, serviceRequestsList);
        this.context = context;
        this.serviceRequestsList = serviceRequestsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_employee_request_list, null, true);
        TextView serviceName = (TextView) listViewItem.findViewById(R.id.request_service_name);
        TextView requestID = (TextView) listViewItem.findViewById(R.id.request_service_id);
        ServiceRequest entry = serviceRequestsList.get(position);
        serviceName.setText(entry.getAssociatedService());
        requestID.setText(entry.getId());
        return listViewItem;
    }
}
