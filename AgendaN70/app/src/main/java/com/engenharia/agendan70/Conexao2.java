package com.engenharia.agendan70;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Eduardo on 05/05/2016.
 */

/*
public class Conexao2  extends SQLiteOpenHelper{

    private static final String TAG = "DBAdapter";

    private static String DBPATH = "C:/Transito/Desenvolvimento/Android/krdcer70/";
    private static final String NOME_BD = "DBAGENDA.sqlite";
    private static final int DATABASE_VERSION = 1;

    Conexao2(Context context)
    {
        super(context, NOME_BD, null, DATABASE_VERSION);
    }

    private void createDataBase(){
        throws Exception {

            boolean exists = checkDataBase();

            if(!exists) {
                this.getReadableDatabase();

                try {
                    copyDatabase();
                } catch (IOException e) {
                    throw new Error("Não foi possível copiar o arquivo");
                }
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        null;
    }

    public DatabaseHelper(Context context) {
	    super(context, NOME_BD, null, 1);
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        null;
    }

    private boolean checkDataBase() {

        SQLiteDatabase db = null;

        try {
            String path = DBPATH + NOME_BD;
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            db.close();
        } catch (SQLiteException e) {
            // O banco não existe
        }

        return db != null;
    }

    private void copyDatabase(){

        throws IOException {

            String dbPath = DBPATH + NOME_BD;
            OutputStream dbStream = new FileOutputStream(dbPath);
            InputStream dbInputStream = context.getAssets().open("DBAGENDA.sqlite");

            byte[] buffer = new byte[1024];
            int length;

            while((length = dbInputStream.read(buffer)) &gt; 0) {
                dbStream.write(buffer, 0, length);
            }

            dbInputStream.close();
            dbStream.flush();
            dbStream.close();
        }
    }
}
*/