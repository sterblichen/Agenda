package com.chumbeke.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Agenda.db";
    private static final int DATABASE_VERSION = 6;
    public Database(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE notas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, titulo TEXT,descripcion TEXT,fecha TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notas");
        onCreate(sqLiteDatabase);
    }
}
