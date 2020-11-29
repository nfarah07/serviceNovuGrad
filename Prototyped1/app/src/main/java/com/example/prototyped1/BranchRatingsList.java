package com.example.prototyped1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.prototyped1.ClassFiles.Employee;

import java.util.List;

public class BranchRatingsList extends ArrayAdapter<Employee> {
    private Activity context;
    List<Employee> branchList;

    public BranchRatingsList(CustomerRatingBranchesActivity context, List<Employee> branchList) {
        super(context, R.layout.layout_branch_ratings, branchList);
        this.context = context;
        this.branchList = branchList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_branch_ratings, null, true);
        TextView nameOfBranch = (TextView) listViewItem.findViewById(R.id.branchName);
        TextView addressOfBranch = (TextView) listViewItem.findViewById(R.id.branchAddress);
        Employee entry = branchList.get(position);
        nameOfBranch.setText(entry.getNameFirst()+" "+entry.getNameLast());
        addressOfBranch.setText(entry.getAddress());
        return listViewItem;
    }
}
