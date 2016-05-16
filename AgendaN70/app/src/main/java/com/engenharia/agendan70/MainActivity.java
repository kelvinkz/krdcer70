package com.engenharia.agendan70;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AcessoBanco db = new AcessoBanco(this);
        Button btMostrar = (Button) findViewById(R.id.buttonCarregar);
        Button btGravar = (Button) findViewById(R.id.buttonSalvar);
        Button btCalendar = (Button) findViewById(R.id.buttonCalendario);
        final EditText id = (EditText) findViewById(R.id.codigo);
        final EditText nome = (EditText) findViewById(R.id.nome);

        btGravar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String novoNome = nome.getText().toString();
                int novoID = Integer.parseInt(id.getText().toString());

                db.open();
                db.inserePessoa(novoNome, novoID);
                db.close();

                nome.setText("");
                id.setText("");
                //mostraRegistro("Inserção realizada com sucesso. ");
            }
        });

        btMostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int idBuscar = Integer.parseInt(id.getText().toString());

                db.open();
                Cursor c = db.getPessoa(idBuscar);

                if (c.moveToFirst())
                {
                    do {
                        mostraRegistro(c);

                    } while (c.moveToNext());
                }
                db.close();
            }
        });
    }

    public void mostraRegistro(Cursor c)
    {
        Toast.makeText(this,
                "Codigo: " + c.getString(0) + "\n" +
                        "Nome: " + c.getString(1) + "\n",
                Toast.LENGTH_SHORT).show();
    }

    public void gotoToCalendario() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}