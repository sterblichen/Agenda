package com.chumbeke.agenda;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;
import com.chumbeke.agenda.ManejoDB;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chumbeke.agenda.databinding.ActivityMainBinding;
import com.chumbeke.agenda.databinding.ActivityRegistroBinding;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {


    private ActivityRegistroBinding binding;
    AlertDialog progressDialog;
    ArrayList<String> Datos = new ArrayList<>();

    ManejoDB database;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = new ManejoDB(this);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("Registrar");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_progress);
        builder.setCancelable(false);
        progressDialog = builder.create();

        binding.RegistrarUs.setOnClickListener(view -> {
            Boolean Validar = ValidarDatos();
            if (Validar == true){
                RegistrarUsuario();
            }else {
                Toast.makeText(this,"Ingrese nuevamente los Datos",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void MostrarProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
    private void EsconderProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }
    public boolean ValidarDatos() {

        if (TextUtils.isEmpty(binding.NombreUS.getText().toString().trim())) {
            Toast.makeText(this, "Ingrese el Nombre", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.CorreoUs.getText().toString().trim()).matches()) {
            Toast.makeText(this, "Ingrese el Correo", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(binding.ContraseAUs.getText().toString().trim())) {
            Toast.makeText(this, "Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(binding.ConfirmaContraseAUs.getText().toString().trim())) {
            Toast.makeText(this, "Confirme Contraseña", Toast.LENGTH_SHORT).show();
        } else if (!binding.ContraseAUs.getText().toString().trim().equals(binding.ConfirmaContraseAUs.getText().toString().trim())) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

        } else {
            MostrarProgress();
            Datos.add(binding.NombreUS.getText().toString().trim());
            Datos.add(binding.CorreoUs.getText().toString().trim());
            Datos.add(binding.ContraseAUs.getText().toString().trim());
            Datos.add(binding.ConfirmaContraseAUs.getText().toString().trim());
            return true;
        }
        return false;
    }

    private void RegistrarUsuario(){
        new Thread(()->{
           try {

               Thread.sleep(3000);
               database.registrarUsuariosDB(Datos);
               runOnUiThread(() -> {
                   EsconderProgress();
                   Toast.makeText(this, "Registro completado", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(Registro.this,MenuPrincipal.class));

               });

           }catch (InterruptedException e){
               e.printStackTrace();
               runOnUiThread(()->{
                   EsconderProgress();
                   Toast.makeText(this,"Error al registrar",Toast.LENGTH_SHORT).show();
               });
           }
        }).start();
    }
}