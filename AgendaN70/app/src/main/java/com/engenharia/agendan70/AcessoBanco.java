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

    public long insertEvento(String descricao) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("DESCRICAO", descricao);
        return database.insert("TIPO_EVENTO", null, initialValues);
    }

    public Cursor selectEvento(long id) {
        Cursor mCursor = database.query(true, "TIPO_EVENTO", new String[] {"ID", "DESCRICAO"}, "ID" + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}