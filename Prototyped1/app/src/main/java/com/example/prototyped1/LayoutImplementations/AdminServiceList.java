package com.example.prototyped1.LayoutImplementations;

import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prototyped1.AdministratorActivities.AdminListActivity;
import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.R;

public class AdminServiceList extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> services;

    public AdminServiceList(AdminListActivity context, List<Service> services) {
        super(context, R.layout.layout_admin_service_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_admin_service_list, null, true);

        TextView serviceName = (TextView) listViewItem.findViewById(R.id.service_name);
        TextView servicePrice = (TextView) listViewItem.findViewById(R.id.service_price);
        TextView serviceDocuments = (TextView) listViewItem.findViewById(R.id.service_documents);
        TextView serviceForm = (TextView) listViewItem.findViewById(R.id.service_form);
        Service product = services.get(position);
        serviceName.setText(product.getName());
        servicePrice.setText(product.priceToString());
        serviceDocuments.setText("Documents: " + product.getDocuments());
        serviceForm.setText("Form values: " + product.getForm());
        return listViewItem;
    }

    private boolean createService(String name, double price) {

        return true;
    }


}