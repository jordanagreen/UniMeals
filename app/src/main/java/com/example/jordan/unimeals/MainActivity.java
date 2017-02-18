package com.example.jordan.unimeals;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int mRemainingMeals;
    private float mRemainingMoney;
    private int mRemainingDays;
    private int mEndMonth;
    private int mEndDay;

    private Spinner mMonthSpinner;
    private Spinner mDaySpinner;

    private static final Integer[] MONTHS = makeIntRange(12);
    private static final Integer[] DAYS = makeIntRange(31);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMonthSpinner = (Spinner) findViewById(R.id.spinnerMonth);
        mDaySpinner = (Spinner) findViewById(R.id.spinnerDay);
        ArrayAdapter<Integer> months = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, MONTHS);
        ArrayAdapter<Integer> days = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, DAYS);
        months.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMonthSpinner.setAdapter(months);
        mDaySpinner.setAdapter(days);
        mMonthSpinner.setOnItemSelectedListener(this);
        mDaySpinner.setOnItemSelectedListener(this);

    }

    public void viewWeek(View v){
        Intent intent = new Intent(this, WeekActivity.class);
        startActivity(intent);
    }

    private static Integer[] makeIntRange(int i){
        Integer[] nums = new Integer[i];
        for (int j = 0; j < i; j++){
            nums[j] = j+1;
        }
        return nums;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        Integer selected = (Integer) parent.getItemAtPosition(pos);
        switch (parent.getId()){
            case R.id.spinnerMonth:
                mEndMonth = selected;
                break;
            case R.id.spinnerDay:
                mEndDay = selected;
                break;
            default:
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}
