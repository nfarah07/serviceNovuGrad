package com.example.prototyped1.LayoutImplementations;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.example.prototyped1.AdministratorActivities.CustomerCreateServiceRequestActivity;
import com.example.prototyped1.ClassFiles.Employee;
import com.example.prototyped1.CustomerActivities.CustomerSearchBranchByAddressActivity;
import com.example.prototyped1.R;

import org.w3c.dom.Text;

import java.util.List;

public class CustomerSearchBranchesList extends ArrayAdapter<Employee> {

    private Activity context;
    List<Employee> resultingBranches;

    public CustomerSearchBranchesList(Activity context, List<Employee> resultingBranches) {
        super(context, R.layout.layout_branches_from_search, resultingBranches);
        this.context = context;
        this.resultingBranches = resultingBranches;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_branches_from_search, null, true);
        TextView branchResultName = (TextView) listViewItem.findViewById(R.id.resultBranchName);
        TextView branchResultPhone = (TextView) listViewItem.findViewById(R.id.resultBranchPhone);
        TextView branchResultAddress = (TextView) listViewItem.findViewById(R.id.resultBranchAddress);
        Employee entry = resultingBranches.get(position);
        branchResultName.setText(entry.getNameFirst()+" "+entry.getNameLast());
        branchResultAddress.setText(entry.getAddress());
        branchResultPhone.setText(entry.getPhone());
        return listViewItem;
    }
}
