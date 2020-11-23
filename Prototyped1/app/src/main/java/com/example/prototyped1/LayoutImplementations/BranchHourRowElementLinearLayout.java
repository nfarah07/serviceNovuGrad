package com.example.prototyped1.LayoutImplementations;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prototyped1.ClassFiles.Service;
import com.example.prototyped1.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Linear layout to select the hour and minute of start/end
 */
public class BranchHourRowElementLinearLayout extends LinearLayout {

    private Activity activity;

    private ArrayList<String> hourOptions = new ArrayList<String>(
            Arrays.asList(
                    "00","01", "02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23")

    );

    private ArrayList<String> minuteOptions = new ArrayList<String>(
            Arrays.asList(
                    "00", "05","10","15","20","25","30","35","40","45","50","55","60"
            )
    );

    TextView dayHourTitle;
    Spinner hourSelectionSpinner;
    Spinner minuteSelectionSpinner;

    /**
     * Linear layout to select the hour and minute of start/end
     * @param activity -> Current activity context
     * @param daySpecifier -> Current Day Selected
     */
    public BranchHourRowElementLinearLayout(Activity activity, String daySpecifier) {
        super(activity);
        this.activity = activity;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(activity).inflate(R.layout.branch_hour_selection_row_element_linear_layout, this, true);
        init();
    }

    /**
     * Linear layout to select the hour and minute of start/end
     * @param activity -> Current activity context
     */
    public BranchHourRowElementLinearLayout(Activity activity) {
        this(activity, "Empty");
        this.activity = activity;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(activity).inflate(R.layout.branch_hour_selection_row_element_linear_layout, this, true);
        init();
    }

    public BranchHourRowElementLinearLayout(Activity activity, AttributeSet attrs) {
        super(activity, attrs);
        this.activity = activity;

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(activity).inflate(R.layout.branch_hour_selection_row_element_linear_layout, this, true);
        init();
    }

    /**
     * Populate Linear Layout Elements with relevant information
     * i.e fill spinners with the relevant options for selecting times
     */
    private void init(){
        //Title for the day
        dayHourTitle = (TextView) findViewById(R.id.DayHourTitle);
        hourSelectionSpinner = (Spinner) findViewById(R.id.hourSelectionSpinner); //Select the specific hour
        minuteSelectionSpinner = (Spinner) findViewById(R.id.minuteSelectionSpinner); //Select the minute

        //Insert list of hour options into spinner
        ArrayAdapter<String> hourSpinnerAdapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, this.hourOptions);
        hourSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSelectionSpinner.setAdapter(hourSpinnerAdapter);

        //Insert list of hour options into spinner
        ArrayAdapter<String> minuteSpinnerAdapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, this.minuteOptions);
        minuteSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSelectionSpinner.setAdapter(minuteSpinnerAdapter);
    }


}
