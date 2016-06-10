package com.engenharia.agendan70;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        calendar = (CalendarView) findViewById(R.id.calendarView);

        initializeCalendar();
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(2);
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.blue));
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.blue));
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                updateListView();
            }
        });
    }

    public void updateListView() {

        AcessoBanco.getInstance(this).open();
        Cursor cursor = AcessoBanco.getInstance(this).selectCompromisso(0);
        AcessoBanco.getInstance(this).close();

        CursorAdapter dataSource = new SimpleCursorAdapter(this, R.layout.formulario, cursor, new String[] {"DESCRICAO", "DATA"}, new int[]{R.id.campoTitulo, R.id.campoData});
        listView.setAdapter(dataSource);
    }

    public void gotoNewEvent(View view) {
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
    }
}