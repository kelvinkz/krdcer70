package com.engenharia.agendan70;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListEventActivity extends AppCompatActivity {

    private ListView listView;
    private CursorAdapter dataSource;
    private static final String campos[] = {"ID", "DESCRICAO"};
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        listView = (ListView) findViewById(R.id.listView1);

        AcessoBanco.getInstance(this).open();
        cursor = AcessoBanco.getInstance(this).selectEvento(1L);
        AcessoBanco.getInstance(this).close();

        //cria cursor que será exibido na tela, nele serão exibidos
        //todos os contatos cadastrados
       dataSource = new SimpleCursorAdapter(this, R.layout.formulario, cursor, campos, new int[] { R.id.campoId, R.id.campoDescricao});
        //relaciona o dataSource ao próprio listview
       listView.setAdapter(dataSource);
    }
}
