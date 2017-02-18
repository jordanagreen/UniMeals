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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int mRemainingMeals;
    private float mRemainingMoney;
    private int mEndMonth;
    private int mEndDay;

    private Spinner mMonthSpinner;
    private Spinner mDaySpinner;
    private EditText mMealEditText;
    private EditText mMoneyEditText;

    private static final Integer[] MONTHS = makeIntRange(12);
    private static final Integer[] DAYS = makeIntRange(31);

    public static final String EXTRA_MONTH = "EXTRA_MONTH";
    public static final String EXTRA_DAY = "EXTRA_DAY";
    public static final String EXTRA_MONEY = "EXTRA_MONEY";
    public static final String EXTRA_MEALS = "EXTRA_MEALS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMealEditText = (EditText) findViewById(R.id.editMeals);
        mMoneyEditText = (EditText) findViewById(R.id.editMoney);
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
        int meals = Integer.parseInt(mMealEditText.getText().toString());
        float money = Float.parseFloat(mMoneyEditText.getText().toString());
        Intent intent = new Intent(this, WeekActivity.class);
        intent.putExtra(EXTRA_DAY, mEndDay);
        intent.putExtra(EXTRA_MONTH, mEndMonth);
        intent.putExtra(EXTRA_MONEY, money);
        intent.putExtra(EXTRA_MEALS, meals);
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
