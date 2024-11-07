package com.chumbeke.agenda;
import com.chumbeke.agenda.Database;
import com.chumbeke.agenda.ObjetosUsuariosYNotas.Notas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ManejoDB {
    private Database dbHelper;

    public ManejoDB(Context context){
        dbHelper = new Database(context);
    }

    public void registrarUsuariosDB(ArrayList<String> String){

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("nombre", String.get(0));
            valores.put("correo", String.get(1));
            valores.put("password", String.get(2));
            db.insert("usuarios", null, valores);
            db.close();

    }
    public void RegistrarNotaDB(Notas notas){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre",notas.getNombre());
        valores.put("correo",notas.getCorreo());
        valores.put("titulo",notas.getTitulo());
        valores.put("descripcion",notas.getDescripcion());
        valores.put("fecha",notas.getFecha());
        db.insert("notas",null,valores);
        db.close();
    }


    public ArrayList<String> BuscarUsuario(ArrayList<String> Array) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE correo = ? and password = ?",new String[]{Array.get(0),Array.get(1)});
        if (cursor != null && cursor.moveToFirst()){
            ArrayList<String> Login = new ArrayList<>();
            Login.add(cursor.getString(2));
            Login.add(cursor.getString(3));
            Login.add(cursor.getString(1));
            cursor.close();
            db.close();
            return Login;
        }
        cursor.close();
        db.close();
        return null;
    }
    public ArrayList<Notas> TodasLasNotas(){
        ArrayList<Notas> ListaNotas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notas",null);
        if (cursor.moveToFirst()){
            do {
                String nombre = cursor.getString(1);
                String correo = cursor.getString(2);
                String titulo = cursor.getString(3);
                String descripcion = cursor.getString(4);
                String fecha = cursor.getString(5);
                Notas notas = new Notas(nombre,correo,titulo,descripcion,fecha);
                ListaNotas.add(notas);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListaNotas;
    }
    
}
