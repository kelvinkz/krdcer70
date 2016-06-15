package com.engenharia.agendan70;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ViewEventActivity extends AppCompatActivity {

    private EditText campoTitulo;
    private EditText campoLocal;
    private EditText campoTipoEvento;
    private EditText campoRepeticao;
    private EditText campoDataInicio;
    private EditText campoParticipante;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        campoTitulo = (EditText) findViewById(R.id.campoTitulo);
        campoLocal = (EditText) findViewById(R.id.campoLocal);
        campoTipoEvento = (EditText) findViewById(R.id.campoTipoEvento);
        campoRepeticao = (EditText) findViewById(R.id.campoRepeticao);
        campoDataInicio = (EditText) findViewById(R.id.campoData);
        campoParticipante = (EditText) findViewById(R.id.campoParticipante);

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
            campoTipoEvento.setText(cursor.getString(cursor.getColumnIndex("TIPO_EVENTO")));
            campoRepeticao.setText(cursor.getString(cursor.getColumnIndex("REPETICAO")));
            campoParticipante.setText(cursor.getString(cursor.getColumnIndex("PARTICIPANTES")));
        } else {
            Toast.makeText(getApplicationContext(), "SELECT VAZIO", Toast.LENGTH_LONG).show();
        }
    }

    public void gotoVoltar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
