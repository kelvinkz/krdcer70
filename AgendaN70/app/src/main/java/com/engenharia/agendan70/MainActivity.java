package com.engenharia.agendan70;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.EditText;
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

                //Mes
                month++;
                String sMonth;
                if (month < 10) {
                    sMonth = "0" + month;
                } else {
                    sMonth = Integer.toString(month);
                }

                //Dia
                String sDay;
                if (day < 10) {
                    sDay = "0" + day;
                } else {
                    sDay = Integer.toString(day);
                }

                String date = sDay + "/" + sMonth + "/" + year;
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
                updateListView(date);
            }
        });
    }

    public void updateListView(String date) {

        AcessoBanco.getInstance(this).open();
        Cursor cursor = AcessoBanco.getInstance(this).selectCompromisso(date);
        AcessoBanco.getInstance(this).close();

        if (cursor.getCount() > 0) {
            CursorAdapter dataSource = new SimpleCursorAdapter(this, R.layout.formulario, cursor, new String[]{"DESCRICAO", "DATA", "_id"}, new int[]{R.id.campoTitulo, R.id.campoData, R.id.campoId});
            listView.setAdapter(dataSource);
            listView.setVisibility(listView.VISIBLE);
        } else {
            Toast.makeText(getApplicationContext(), "Nenhum compromisso para o dia!", Toast.LENGTH_LONG).show();
            listView.refreshDrawableState();
            listView.setVisibility(listView.INVISIBLE);
        }
    }

    public void gotoNewEvent(View view) {
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
    }

    public void gotoEditCompromisso(View view) {
        EditText campoId = (EditText) findViewById(R.id.campoId);
        int id = Integer.parseInt(campoId.getText().toString());
        Intent intent = new Intent(this, EditEventActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    public void gotoDeletCompromisso(View view) {

        EditText campoId = (EditText) findViewById(R.id.campoId);
        int id = Integer.parseInt(campoId.getText().toString());

        AcessoBanco.getInstance(this).open();
        Cursor cursor = AcessoBanco.getInstance(this).selectCompromisso(id);
        AcessoBanco.getInstance(this).close();

        if (!cursor.getString(cursor.getColumnIndex("REPETICAO")).equals("Sem repetição")) {
            for (int i = 0; i < 30; i++) {
                AcessoBanco.getInstance(this).open();
                AcessoBanco.getInstance(this).deleteCompromisso(id);
                AcessoBanco.getInstance(this).close();
                id++;
            }
        } else {
            AcessoBanco.getInstance(this).open();
            AcessoBanco.getInstance(this).deleteCompromisso(id);
            AcessoBanco.getInstance(this).close();
        }
    }

    public void gotoViewCompromisso(View view) {
        EditText campoId = (EditText) findViewById(R.id.campoId);
        int id = Integer.parseInt(campoId.getText().toString());
        Intent intent = new Intent(this, ViewEventActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}