package com.engenharia.agendan70;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListEventActivity extends AppCompatActivity {

    private ListView listView;
    private CursorAdapter dataSource;
    private static final String campos[] = {"DESCRICAO", "_id"};
    Cursor cursor;
    private EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        listView = (ListView) findViewById(R.id.listView1);
        descricao = (EditText) findViewById(R.id.descricao);

        AcessoBanco.getInstance(this).open();
        cursor = AcessoBanco.getInstance(this).selectEvento(0);
        AcessoBanco.getInstance(this).close();

        //cria cursor que será exibido na tela, nele serão exibidos
        //todos os contatos cadastrados
       dataSource = new SimpleCursorAdapter(this, R.layout.formulario, cursor, campos, new int[] {R.id.campoDescricao, R.id.campoID});
        //relaciona o dataSource ao próprio listview
       listView.setAdapter(dataSource);
    }

    public void btnInserir(View view) {

        AcessoBanco.getInstance(this).open();
        AcessoBanco.getInstance(this).insertEvento(descricao.getText().toString());
        descricao.setText("");
        AcessoBanco.getInstance(this).close();
        listView.refreshDrawableState();
    }

    public void btnApagar(View view) {

        AcessoBanco.getInstance(this).open();
        AcessoBanco.getInstance(this).deleteEvento();
        AcessoBanco.getInstance(this).close();
    }
}
