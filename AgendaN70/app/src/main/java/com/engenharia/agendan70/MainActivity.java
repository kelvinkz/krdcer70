package com.engenharia.agendan70;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AcessoBanco2 db = new AcessoBanco2(this);
        Button btMostrar = (Button) findViewById(R.id.bt_mostrar);
        Button btGravar = (Button) findViewById(R.id.button);
        final EditText txtNome = (EditText) findViewById(R.id.texto);

        btGravar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String novoNome = txtNome.getText().toString();

                db.open();
                db.inserePessoa(novoNome);
                db.close();

                txtNome.setText("");
                //mostraRegistro("Inserção realizada com sucesso. ");
            }
        });

        btMostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                db.open();
                Cursor c = db.getCadastros();

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
}
