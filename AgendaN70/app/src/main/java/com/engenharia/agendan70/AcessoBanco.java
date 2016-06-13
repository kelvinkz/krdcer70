package com.engenharia.agendan70;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AcessoBanco {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static AcessoBanco instance;

    private AcessoBanco(Context context) {
        this.openHelper = new Conexao(context);
    }

    public static AcessoBanco getInstance(Context context) {
        if (instance == null) {
            instance = new AcessoBanco(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM quotes", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public long insertCompromisso(String date, String descricao, String local, String participantes, String tipoEvento, String repeticao) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("DATA", date);
        initialValues.put("DESCRICAO", descricao);
        initialValues.put("LOCAL", local);
        initialValues.put("PARTICIPANTES", participantes);
        initialValues.put("TIPO_EVENTO", tipoEvento);
        initialValues.put("REPETICAO", repeticao);
        return database.insert("COMPROMISSO", null, initialValues);
    }

    public Cursor selectCompromisso(String date) {
        Cursor mCursor = database.rawQuery("SELECT ID _id, DESCRICAO, DATA FROM COMPROMISSO WHERE DATA = '" + date + "'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void deleteCompromisso(int id) {
        database.execSQL("DELETE FROM COMPROMISSO WHERE ID = " + id);
    }

    public Cursor selectCompromisso(int id) {
        Cursor mCursor = database.rawQuery("SELECT * FROM COMPROMISSO WHERE ID = " + id, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void updateCompromisso(String descricao, String local, String participantes, String tipoEvento, String repeticao, int id) {
        ContentValues cv = new ContentValues();
        cv.put("DESCRICAO", descricao);
        cv.put("LOCAL", local);
        cv.put("PARTICIPANTES", participantes);
        cv.put("TIPO_EVENTO", tipoEvento);
        cv.put("REPETICAO", repeticao);
        database.update("COMPROMISSO", cv, "ID="+id, null);
    }
}