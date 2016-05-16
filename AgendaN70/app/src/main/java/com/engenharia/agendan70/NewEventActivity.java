package com.engenharia.agendan70;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Sem repetição", "Diariamente", "Semanalmente", "Mensalmente", "Anualmente"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
