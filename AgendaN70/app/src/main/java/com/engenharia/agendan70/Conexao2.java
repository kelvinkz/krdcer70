package com.engenharia.agendan70;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Eduardo on 05/05/2016.
 */
public class Conexao2  extends SQLiteOpenHelper{

    private static final String TAG = "DBAdapter";

    private static final String NOME_BD = "DBAGENDA";
    private static final int DATABASE_VERSION = 2;

    Conexao2(Context context)
    {
        super(context, NOME_BD, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        /*try {
            db.execSQL(DATABASE_CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        /*Log.w(TAG, "Atualizando banco de dados da versão " + oldVersion + " para "
                + newVersion + ". Todos os dados serão destruidos.");
        db.execSQL("DROP TABLE IF EXISTS pessoas");
        onCreate(db);*/
    }


}
