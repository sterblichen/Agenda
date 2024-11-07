package com.chumbeke.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.chumbeke.agenda.MenuPrincipal;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chumbeke.agenda.databinding.ActivityLoginBinding;

import java.util.ArrayList;


public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    AlertDialog progressDialog;
    ManejoDB database;
    ArrayList<String> Datos = new ArrayList<>();
    MenuPrincipal MP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = new ManejoDB(this);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle("Login");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_progress);
        builder.setCancelable(false);
         progressDialog = builder.create();

         binding.BtnIniciar.setOnClickListener(view -> {
            Boolean vali = Validarlogin();
            if (vali){

                LogeandoUsuario(binding.CorreoLogin.getText().toString(),binding.Password.getText().toString());
            }
         });
         binding.UsuarioNuevo.setOnClickListener(view -> {
             startActivity(new Intent(Login.this,Registro.class));
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

    public Boolean Validarlogin(){
        if (TextUtils.isEmpty(binding.CorreoLogin.getText().toString().trim())){
            Toast.makeText(this, "Ingrese el Correo", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(binding.Password.getText().toString().trim())) {
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
        }else{
            MostrarProgress();
            Datos.add(binding.CorreoLogin.getText().toString());
            Datos.add(binding.Password.getText().toString());
            return true;

        }
        return false;
    }

    private void LogeandoUsuario(String correo,String psw){
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                ArrayList<String> Data = database.BuscarUsuario(Datos);
                if (Data != null){

                    runOnUiThread(() -> {
                        EsconderProgress();
                        Toast.makeText(this, "Iniciar Sesion", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this,MenuPrincipal.class);
                        intent.putStringArrayListExtra("Datos",Data);
                        startActivity(intent);


                    });
                }else {
                    runOnUiThread(() -> {
                        EsconderProgress();
                        Toast.makeText(this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();

                    });
                }

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