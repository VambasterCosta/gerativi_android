package com.example.gerativi.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TAREFAS_TABELA = "terefas";

    public DbHelper(Context context) {
        super(context, NOME_DB,null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS "+TAREFAS_TABELA+" (id INTEGER PRIMARY KEY AUTOINCREMENT, nomeTarefa TEXT NOT NULL,  prioridade TEXT NOT NULL ); ";
        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar tabela");
        }catch (Exception e){
            Log.i("INFO DB", "erro ao criar tabela"+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
