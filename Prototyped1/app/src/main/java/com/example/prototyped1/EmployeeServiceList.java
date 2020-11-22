package com.example.prototyped1;

import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.CheckBox;

public class EmployeeServiceList extends ArrayAdapter<Service>{

    private Activity context;
    List<Service> services;

    public EmployeeServiceList(EmployeeServiceSelectActivity context, List<Service> services) {
        super(context, R.layout.layout_select_service_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_select_service_list, null, true);

        TextView serviceName = (TextView) listViewItem.findViewById(R.id.selectableService);

        Service service = services.get(position);
        serviceName.setText(service.getName());
        return listViewItem;
    }


}
