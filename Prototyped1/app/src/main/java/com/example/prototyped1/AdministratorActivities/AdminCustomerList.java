package com.example.prototyped1.AdministratorActivities;

import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Customer;
import com.example.prototyped1.R;

public class AdminCustomerList extends ArrayAdapter<Customer> {
    private Activity context;
    List<Customer> customers;

    public AdminCustomerList(AdminCustomerActivity context, List<Customer> customers) {
        super(context, R.layout.layout_admin_customer_list, customers);
        this.context = context;
        this.customers = customers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_admin_customer_list, null, true);

        TextView customerEmail = (TextView) listViewItem.findViewById(R.id.customer_email);

        Customer product = customers.get(position);
        customerEmail.setText(product.getEmail());
        return listViewItem;
    }

}
