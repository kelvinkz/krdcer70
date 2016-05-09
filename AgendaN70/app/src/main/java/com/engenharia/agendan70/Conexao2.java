package com.engenharia.agendan70;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Created by Eduardo on 05/05/2016.
 */


public class Conexao2 extends SQLiteOpenHelper{

    private static final String TAG = "DBAdapter";

    //private static String DBPATH = "C:/Transito/Desenvolvimento/Android/krdcer70/";
    private static String DBPATH = "/Users/Ran/krdcer70/";
    private static final String NOME_BD = "DBAGENDA.sqlite";
    private static final int DATABASE_VERSION = 1;
    private Context context;


    private void createDataBase(){

        boolean exists = checkDataBase();

        if(!exists) {
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch (Exception e) {
                throw new Error("Não foi possível copiar o arquivo");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    public Conexao2(Context context) {
	    super(context, NOME_BD, null, 1);
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    private boolean checkDataBase() {

        SQLiteDatabase db = null;

        try {
            String path = DBPATH + NOME_BD;
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
        } catch (Exception e) {
            // O banco não existe
        }

        return db != null;
    }

    private void copyDatabase() throws IOException {

//            while((length = dbInputStream.read(buffer)) &gt; 0) {
//                dbStream.write(buffer, 0, length);
//            }
//
//            dbInputStream.close();
//            dbStream.flush();
//            dbStream.close();
//        }
    }
}
