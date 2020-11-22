package com.example.prototyped1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prototyped1.LayoutImplementations.RequiredInfoElement;

import java.util.List;

// ListView code to make list view of required Infos for each Service

public class ServiceRequiredInfoActivity extends ArrayAdapter<RequiredInfoElement> {

    private Activity context;
    List<RequiredInfoElement> reqInfos;

    public ServiceRequiredInfoActivity(AdminServiceReqInfoChoiceActivity context, List<RequiredInfoElement> reqInfos) {
        super(context, R.layout.activity_service_required_info, reqInfos);
        this.context = context;
        this.reqInfos = reqInfos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_service_required_info, null, true);

        TextView infoName = (TextView) listViewItem.findViewById(R.id.information_name);
        TextView infoType = (TextView) listViewItem.findViewById(R.id.information_type);

        RequiredInfoElement product = reqInfos.get(position);
        infoName.setText(product.getInfoName());
        infoType.setText(product.getInfoType());
        return listViewItem;
    }


}