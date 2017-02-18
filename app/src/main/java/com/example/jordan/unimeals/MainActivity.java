package com.example.jordan.unimeals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int remainingMeals;
    private float remainingMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewWeek(View v){
        Intent intent = new Intent(this, WeekActivity.class);
        startActivity(intent);
    }
}
