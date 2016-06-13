package com.engenharia.agendan70;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditEventActivity extends AppCompatActivity {

    private EditText campoTitulo;
    private EditText campoLocal;
    private Spinner campoTipoEvento;
    private Spinner campoRepeticao;
    private EditText campoDataInicio;
    private EditText campoParticipante;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

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

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("ID");
        preencherCampos(id);
    }

    public void preencherCampos(int id) {
        AcessoBanco.getInstance(this).open();
        Cursor cursor = AcessoBanco.getInstance(this).selectCompromisso(id);
        AcessoBanco.getInstance(this).close();

        if (cursor.getCount() > 0) {
            campoDataInicio.setText(cursor.getString(cursor.getColumnIndex("DATA")));
            campoTitulo.setText(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
            campoLocal.setText(cursor.getString(cursor.getColumnIndex("LOCAL")));
            campoParticipante.setText(cursor.getString(cursor.getColumnIndex("PARTICIPANTES")));
        } else {
            Toast.makeText(getApplicationContext(), "SELECT VAZIO", Toast.LENGTH_LONG).show();
        }
    }

    public void gotoSalvar(View view) {
        AcessoBanco.getInstance(this).open();
        AcessoBanco.getInstance(this).updateCompromisso(campoDataInicio.getText().toString(), campoTitulo.getText().toString(), campoLocal.getText().toString(), campoParticipante.getText().toString(), campoTipoEvento.getSelectedItem().toString(), campoRepeticao.getSelectedItem().toString(), id);
        AcessoBanco.getInstance(this).close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoCancelar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
