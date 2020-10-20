package com.example.prototyped1;

import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        Service product = services.get(position);
        serviceName.setText(product.getName());
        servicePrice.setText(product.priceToString());
        return listViewItem;
    }

    private boolean createService(String name, double price) {

        return true;
    }


}