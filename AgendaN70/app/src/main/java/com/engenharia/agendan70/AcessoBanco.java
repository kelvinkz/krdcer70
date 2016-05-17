package com.engenharia.agendan70;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;

    public class AcessoBanco {

        public static final String CODIGO = "codigo";
        public static final String NOME = "nome";
        private static final String NOME_BD = "empresa";
        private static final String NOME_TABELA = "pessoas";

        private final Context context;

        private Conexao conector;
        private SQLiteDatabase db;

        public AcessoBanco(Context ctx)
        {
            this.context = ctx;
            conector = new Conexao(context);
        }


        public AcessoBanco open() throws SQLException
        {
            db = conector.getWritableDatabase();
            return this;
        }

        public void close()
        {
            conector.close();
        }

        public long inserePessoa(String name, int codigo)
        {
            ContentValues initialValues = new ContentValues();
            initialValues.put(NOME, name);
            initialValues.put(CODIGO, codigo);
            return db.insert(NOME_TABELA, null, initialValues);
        }

        public boolean removePessoa(long cod)
        {
            return db.delete(NOME_TABELA, CODIGO + "=" + cod, null) > 0;
        }

        public Cursor getCadastros()
        {
            return db.query(NOME_TABELA, new String[] {CODIGO, NOME}, null, null, null, null, null);
        }

        public Cursor getPessoa(long cod) throws SQLException
        {
            Cursor mCursor =
                    db.query(true, NOME_TABELA, new String[] {CODIGO, NOME}, CODIGO + "=" + cod, null,
                            null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
        }

        public boolean updatePessoa(long cod, String name, String email)
        {
            ContentValues args = new ContentValues();
            args.put(NOME, name);
            return db.update(NOME_BD, args, CODIGO + "=" + cod, null) > 0;
        }
    }