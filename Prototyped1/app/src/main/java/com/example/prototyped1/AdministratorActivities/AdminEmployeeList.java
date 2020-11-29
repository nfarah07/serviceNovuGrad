package com.example.prototyped1.AdministratorActivities;

import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.R;

public class AdminEmployeeList extends ArrayAdapter<Employee> {
    private Activity context;
    List<Employee> employees;

    public AdminEmployeeList(AdminEmployeeActivity context, List<Employee> employees) {
        super(context, R.layout.layout_admin_employee_list, employees);
        this.context = context;
        this.employees = employees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_admin_employee_list, null, true);

        TextView employeeEmail = (TextView) listViewItem.findViewById(R.id.employee_email);

        Employee product = employees.get(position);
        employeeEmail.setText(product.getEmail());
        return listViewItem;
    }
}