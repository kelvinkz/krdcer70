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
import java.util.Date;

public class NewEventActivity extends AppCompatActivity {

    private EditText campoTitulo;
    private EditText campoLocal;
    private Spinner campoTipoEvento;
    private Spinner campoRepeticao;
    private EditText campoDataInicio;
    private EditText campoDataFim;
    private EditText campoParticipante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        campoTitulo = (EditText) findViewById(R.id.campoTitulo);
        campoLocal = (EditText) findViewById(R.id.campoLocal);
        campoTipoEvento = (Spinner) findViewById(R.id.campoTipoEvento);
        campoRepeticao = (Spinner) findViewById(R.id.campoRepeticao);
        campoDataInicio = (EditText) findViewById(R.id.campoDataInicio);
        campoDataFim = (EditText) findViewById(R.id.campoDataFim);
        campoParticipante = (EditText) findViewById(R.id.campoParticipante);

        ArrayAdapter<String> adapterTipoEvento = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Festa", "Casamento", "Reuniao", "Outro"});
        campoTipoEvento.setAdapter(adapterTipoEvento);

        ArrayAdapter<String> adapterRepeticao = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Sem repetição", "Diariamente", "Semanalmente", "Mensalmente", "Anualmente"});
        campoRepeticao.setAdapter(adapterRepeticao);
    }

    public void gotoSalvar(View view) {


        // TODO: ACAO DE SALVAR AQUI
        AcessoBanco.getInstance(this).open();
        AcessoBanco.getInstance(this).insertCompromisso(campoDataInicio.getText().toString(), campoTitulo.getText().toString(), campoLocal.getText().toString(), campoParticipante.getText().toString(), campoTipoEvento.getSelectedItem().toString());
        AcessoBanco.getInstance(this).close();


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoCancelar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
