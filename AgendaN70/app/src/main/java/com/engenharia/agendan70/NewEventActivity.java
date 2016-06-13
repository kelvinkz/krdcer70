package com.engenharia.agendan70;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewEventActivity extends AppCompatActivity {

    private EditText campoTitulo;
    private EditText campoLocal;
    private Spinner campoTipoEvento;
    private Spinner campoRepeticao;
    private EditText campoDataInicio;
    private EditText campoParticipante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        campoTitulo = (EditText) findViewById(R.id.campoTitulo);
        campoLocal = (EditText) findViewById(R.id.campoLocal);
        campoTipoEvento = (Spinner) findViewById(R.id.campoTipoEvento);
        campoRepeticao = (Spinner) findViewById(R.id.campoRepeticao);
        campoDataInicio = (EditText) findViewById(R.id.campoData);
        campoParticipante = (EditText) findViewById(R.id.campoParticipante);

        ArrayAdapter<String> adapterTipoEvento = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Festa", "Casamento", "Reuniao", "Outro"});
        campoTipoEvento.setAdapter(adapterTipoEvento);

        ArrayAdapter<String> adapterRepeticao = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Sem repetição", "Diariamente", "Semanalmente", "Mensalmente", "Anualmente"});
        campoRepeticao.setAdapter(adapterRepeticao);
    }

    public void gotoSalvar(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat  df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            c.setTime(df.parse(campoDataInicio.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String data = df.format(c.getTime());
        if (!campoRepeticao.getSelectedItem().toString().equals("Sem repetição")) {
            for (int i = 0; i < 30; i++) {
                AcessoBanco.getInstance(this).open();
                AcessoBanco.getInstance(this).insertCompromisso(data, campoTitulo.getText().toString(), campoLocal.getText().toString(), campoParticipante.getText().toString(), campoTipoEvento.getSelectedItem().toString(), campoRepeticao.getSelectedItem().toString());
                AcessoBanco.getInstance(this).close();

                switch (campoRepeticao.getSelectedItem().toString()) {
                    case "Diariamente":
                        c.add(Calendar.DAY_OF_YEAR,1);
                        break;
                    case "Semanalmente":
                        c.add(Calendar.DAY_OF_YEAR,7);
                        break;
                    case "Mensalmente":
                        c.add(Calendar.MONTH,1);
                        break;
                    case "Anualmente":
                        c.add(Calendar.YEAR,1);
                        break;
                }
                data = df.format(c.getTime());
            }
        } else {
            AcessoBanco.getInstance(this).open();
            AcessoBanco.getInstance(this).insertCompromisso(data, campoTitulo.getText().toString(), campoLocal.getText().toString(), campoParticipante.getText().toString(), campoTipoEvento.getSelectedItem().toString(), campoRepeticao.getSelectedItem().toString());
            AcessoBanco.getInstance(this).close();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoCancelar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
