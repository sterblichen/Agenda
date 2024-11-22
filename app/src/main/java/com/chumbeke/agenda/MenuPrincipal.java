package com.chumbeke.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chumbeke.agenda.databinding.ActivityMenuPrincipalBinding;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity {
    private ActivityMenuPrincipalBinding binding;
    ArrayList<String> Datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Datos = getIntent().getStringArrayListExtra("Datos");
        if (Datos == null){
            Datos = getIntent().getStringArrayListExtra("DatosAgregarNota");
            if (Datos == null){
                Datos = getIntent().getStringArrayListExtra("EditorNotas");
            }
        }
        CambioDeDatos();

        binding.CrearNota.setOnClickListener(view -> {
            Intent intent = new Intent(MenuPrincipal.this,AgregarNota.class);
            intent.putStringArrayListExtra("US",Datos);
            startActivity(intent);

        });
        binding.verNotas.setOnClickListener(view -> {
            Intent intent = new Intent(MenuPrincipal.this, ListaDeNotas.class);
            intent.putStringArrayListExtra("US",Datos);
            startActivity(intent);

        });
        binding.CerrarSesion.setOnClickListener(view -> {
            Intent intent = new Intent(MenuPrincipal.this, MainActivity.class);
            startActivity(intent);
        });


    }


    public void CambioDeDatos(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);

                runOnUiThread(() -> {
                    binding.NombreMenu.setVisibility(View.VISIBLE);
                    binding.CorreoMenu.setVisibility(View.VISIBLE);
                    binding.NombreMenu.setText(Datos.get(2));
                    binding.CorreoMenu.setText(Datos.get(0));
                    binding.ProgressDatos.setVisibility(View.GONE);

                });
            }catch (InterruptedException e){
                e.printStackTrace();
                runOnUiThread(() -> {
                    binding.ProgressDatos.setVisibility(View.GONE);

                    Toast.makeText(this,"No se pudo colocar sus datos",Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}
