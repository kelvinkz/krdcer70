package com.engenharia.agendan70;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public long insertCompromisso(Date date, String descricao, long tipoEvento, String local) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("DATA", date.toString());
        initialValues.put("DESCRICAO", descricao);
        initialValues.put("LOCAL", local);
        return database.insert("COMPROMISSO", null, initialValues);
    }

    public Cursor selectCompromisso(long id) {

        Cursor mCursor = database.rawQuery("SELECT ID _id, DESCRICAO, DATA FROM COMPROMISSO", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void deleteCompromisso() {
        database.execSQL("DELETE FROM COMPROMISSO");
    }

    public long insertEvento(String descricao) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("DESCRICAO", descricao);
        return database.insert("TIPO_EVENTO", null, initialValues);
    }

    public Cursor selectEvento(long id) {

        Cursor mCursor = database.rawQuery("SELECT ID _id, DESCRICAO FROM TIPO_EVENTO", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void deleteEvento() {
        database.execSQL("DELETE FROM TIPO_EVENTO");
    }
}