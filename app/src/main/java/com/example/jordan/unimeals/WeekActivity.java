package com.example.jordan.unimeals;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.example.jordan.unimeals.MainActivity.EXTRA_DAY;
import static com.example.jordan.unimeals.MainActivity.EXTRA_MEALS;
import static com.example.jordan.unimeals.MainActivity.EXTRA_MONEY;
import static com.example.jordan.unimeals.MainActivity.EXTRA_MONTH;
import static com.example.jordan.unimeals.WeekActivity.Meal.MEAL_PLAN;

public class WeekActivity extends AppCompatActivity {

    public static final String TAG = "WeekActivity";

    private int mMeals;
    private float mMoney;
    private int mEndDay;
    private int mEndMonth;

    private Day[] mDays;
    private Meal[] mWeekMeals;
    private ImageView[] mMealImages;

    enum Meal {MEAL_PLAN, EAT_OUT, EAT_IN};
    enum Weekday {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMeals = getIntent().getIntExtra(EXTRA_MEALS, 200);
        mMoney = getIntent().getFloatExtra(EXTRA_MONEY, 50f);
        mEndMonth = getIntent().getIntExtra(EXTRA_MONTH, 5);
        mEndDay = getIntent().getIntExtra(EXTRA_DAY, 31);

        mMealImages = getMealImages();
        int daysRemaining = getDaysRemaining(mEndMonth, mEndDay);
        TextView textDaysRemaining = (TextView) findViewById(R.id.textDaysRemaining);
        String s = daysRemaining + " days left in the semester.";
        textDaysRemaining.setText(s);
        mWeekMeals = makeSchedule(mMeals, mMoney, daysRemaining);
        for (int i = 0; i < mWeekMeals.length; i++){
            switch (mWeekMeals[i]){
                case MEAL_PLAN:
                    mMealImages[i].setImageDrawable(getDrawable(R.drawable.school));
                    break;
                case EAT_IN:
                    mMealImages[i].setImageDrawable(getDrawable(R.drawable.home));
                    break;
                case EAT_OUT:
                    mMealImages[i].setImageDrawable(getDrawable(R.drawable.meal));
                    break;
                default:
                    break;

            }
        }
    }

    private ImageView[] getMealImages(){
        ImageView[] images = new ImageView[21];
        images[0] = (ImageView) findViewById(R.id.img1);
        images[1] = (ImageView) findViewById(R.id.img2);
        images[2] = (ImageView) findViewById(R.id.img3);
        images[3] = (ImageView) findViewById(R.id.img4);
        images[4] = (ImageView) findViewById(R.id.img5);
        images[5] = (ImageView) findViewById(R.id.img6);
        images[6] = (ImageView) findViewById(R.id.img7);
        images[7] = (ImageView) findViewById(R.id.img8);
        images[8] = (ImageView) findViewById(R.id.img9);
        images[9] = (ImageView) findViewById(R.id.img10);
        images[10] = (ImageView) findViewById(R.id.img11);
        images[11] = (ImageView) findViewById(R.id.img12);
        images[12] = (ImageView) findViewById(R.id.img13);
        images[13] = (ImageView) findViewById(R.id.img14);
        images[14] = (ImageView) findViewById(R.id.img15);
        images[15] = (ImageView) findViewById(R.id.img16);
        images[16] = (ImageView) findViewById(R.id.img17);
        images[17] = (ImageView) findViewById(R.id.img18);
        images[18] = (ImageView) findViewById(R.id.img19);
        images[19] = (ImageView) findViewById(R.id.img20);
        images[20] = (ImageView) findViewById(R.id.img21);
        return images;
    }

    public void searchYelp(View v){
        Intent intent = new Intent(this, YelpActivity.class);
        startActivity(intent);
    }

    private int getDaysRemaining(int endMonth, int endDay){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
        Log.d(TAG, "M: " + month + " D: " + day);
        Log.d(TAG, "End M: " + endMonth + " D: " + endDay);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.clear();
        endCalendar.set(year, endMonth-1, endDay-1);
        long diff = endCalendar.getTimeInMillis() - calendar.getTimeInMillis();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return (int) dayCount;

    }

    private Meal[] makeSchedule(int meals, float money, int daysRemaining){
//        Day[] days = new Day[7];
        Meal[] weekMeals = new Meal[21];

        //assume one meal is about $8
        float toSpendPerWeek = (float) ((meals*8.0) + money) / (daysRemaining / 7);
        float moneyPerWeek = money / (daysRemaining/7);
        int mealsPerWeek = (int) ((toSpendPerWeek - moneyPerWeek) / 8);
        String s = String.format(Locale.US,
                "You should use %d meals and spend $%.2f on food this week.", mealsPerWeek, moneyPerWeek);
        TextView textShouldUse = (TextView) findViewById(R.id.textShouldUse);
        textShouldUse.setText(s);

        Random random = new Random();
        for (int i = 0; i < mealsPerWeek; i++){
            int mealNumber = random.nextInt(21);
            while (weekMeals[mealNumber] != null){
                mealNumber = random.nextInt(21);
            }
            weekMeals[mealNumber] = MEAL_PLAN;
        }

        while (moneyPerWeek > 0){
            int mealNumber = random.nextInt(21);
            while (weekMeals[mealNumber] != null){
                mealNumber = random.nextInt(21);
            }
            weekMeals[mealNumber] = Meal.EAT_OUT;
            moneyPerWeek -= 8;
        }

        for (int i = 0; i < weekMeals.length; i++){
            if (weekMeals[i] == null){
                weekMeals[i] = Meal.EAT_IN;
            }
        }

        return weekMeals;
    }

    private class Day{
        private Meal[] meals;

        Day(){
            meals = new Meal[3];
        }

        void setMeal(int i, Meal m){
            meals[i] = m;
        }

        Meal[] getMeals(){
            return meals;
        }
    }

}
