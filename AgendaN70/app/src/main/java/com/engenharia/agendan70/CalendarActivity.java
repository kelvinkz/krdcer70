package com.engenharia.agendan70;

/**
 * Created by diego on 06/05/16.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define a activity
        setContentView(R.layout.activity_calendar);

        //Chama a função que cria o layout
        initializeCalendar();
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setShowWeekNumber(false);

        calendar.setFirstDayOfWeek(2);

        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.blue));

        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.blue));

        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        calendar.setOnDateChangeListener(new OnDateChangeListener() {

            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}