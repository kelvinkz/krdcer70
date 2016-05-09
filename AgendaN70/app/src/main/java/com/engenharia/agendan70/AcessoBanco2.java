package com.engenharia.agendan70;

/**
 * Created by Eduardo on 05/05/2016.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class AcessoBanco2 {

    public static final String CODIGO = "ID";
    public static final String NOME = "NOME";
    private static final String NOME_BD = "DBAGENDA.sqlite";
    private static final String NOME_TABELA = "PARTICIPANTE";

    private final Context context;

    private Conexao2 conector;
    private SQLiteDatabase db;

    public AcessoBanco2(Context ctx)
    {
        this.context = ctx;
        conector = new Conexao2(context);
    }


    public AcessoBanco2 open() throws SQLException
    {
        db = conector.getWritableDatabase();
        return this;
    }

    public void close()
    {
        conector.close();
    }

    public long insertParticipante(String name)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NOME, name);
        return db.insert(NOME_TABELA, null, initialValues);
    }

    public boolean removeParticipante(long cod)
    {
        return db.delete(NOME_TABELA, CODIGO + "=" + cod, null) > 0;
    }

    public Cursor getParticipantes()
    {
        return db.query(NOME_TABELA, new String[] {CODIGO, NOME}, null, null, null, null, null);
    }

    public Cursor getParticipante(long cod) throws SQLException
    {
        Cursor mCursor =
                db.query(true, NOME_TABELA, new String[] {CODIGO, NOME}, CODIGO + "=" + cod, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateParticipante(long cod, String name, String email)
    {
        ContentValues args = new ContentValues();
        args.put(NOME, name);
        return db.update(NOME_BD, args, CODIGO + "=" + cod, null) > 0;
    }
}
