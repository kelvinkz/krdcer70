package com.engenharia.agendan70;

    import android.content.Context;
    import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Conexao extends SQLiteAssetHelper {

        private static final String DATABASE_NAME = "DBAGENDA_N.DB";
        private static final int DATABASE_VERSION = 1;

    public Conexao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}