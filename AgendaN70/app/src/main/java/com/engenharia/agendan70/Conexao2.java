package com.engenharia.agendan70;

import android.content.Context;
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

    private static Context context;
    private static String DBPATH = "";
    private static final String NOME_BD = "DBAGENDA.db";
    private static final int DATABASE_VERSION = 1;


    private void createDataBase(){

        DBPATH = context.getApplicationInfo().dataDir + "/krdcer70/";

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
            // Implementar criacao do db
        }

        return db != null;
    }

    private void copyDatabase() throws IOException {

        InputStream Input = context.getAssets().open(NOME_BD);
        String outFileName = DBPATH + NOME_BD;
        OutputStream Output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = Input.read(buffer)) > 0){
            Output.write(buffer, 0, length);
        }
        Output.flush();
        Output.close();
        Input.close();
    }
}
