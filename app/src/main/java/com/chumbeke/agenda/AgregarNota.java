package com.chumbeke.agenda;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chumbeke.agenda.ObjetosUsuariosYNotas.Notas;
import com.chumbeke.agenda.databinding.ActivityAgregarNotaBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AgregarNota extends AppCompatActivity {
    private ActivityAgregarNotaBinding binding;
    int dia,mes,year;
    ArrayList<String> Datos = new ArrayList<>();
    AlertDialog progressDialog;
    ManejoDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarNotaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Datos = getIntent().getStringArrayListExtra("US");
        database = new ManejoDB(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_progress);
        builder.setCancelable(false);
        progressDialog = builder.create();

        Date date = new Date();

        binding.NombreUsuaruiNota.setText(Datos.get(0));
        binding.CorreoUsuarioNota.setText(Datos.get(2));
        binding.FechaHora.setText(date.toString());

        binding.BtnCalendario.setOnClickListener(view -> {
            final Calendar calendario = Calendar.getInstance();
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            year = calendario.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AgregarNota.this,
                    (datePicker, yearSeleccionado, mesSeleccionado, diaSeleccionado) -> {
                        String DiaForma = String.format("%02d", diaSeleccionado);
                        String MesForma = String.format("%02d", mesSeleccionado + 1);
                        binding.Fecha.setText(DiaForma + "/" + MesForma + "/" + yearSeleccionado);
                    },
                    year, mes, dia
            );

            datePickerDialog.show();
        });

        binding.AgregarNota.setOnClickListener(view -> {
            Boolean vali = ValidarDatos();
            if (vali == true){
                MostrarProgress();
                AgregarNota();
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
    public Boolean ValidarDatos(){
        if(TextUtils.isEmpty(binding.Titulo.getText().toString())){
            Toast.makeText(this, "Coloque un titulo", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(binding.Descripcion.getText().toString())) {
            Toast.makeText(this, "Escriba una descripcion", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(binding.Fecha.getText().toString())) {
        } else if (TextUtils.isEmpty(binding.Fecha.getText().toString())) {
            Toast.makeText(this, "Coloque una fecha", Toast.LENGTH_SHORT).show();
        }else {
            return true;
        }
        return false;
    }
    @SuppressLint("NotConstructor")
    public void AgregarNota(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Notas notas = new Notas(Datos.get(2),Datos.get(0),binding.Titulo.getText().toString(),
                        binding.Descripcion.getText().toString(),binding.Fecha.getText().toString());
                database.RegistrarNotaDB(notas);
                runOnUiThread(() -> {
                    EsconderProgress();
                    Toast.makeText(this, "Nota agregada con exito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AgregarNota.this,MenuPrincipal.class);
                    intent.putStringArrayListExtra("DatosAgregarNota",Datos);
                    startActivity(intent);
                });
            }catch (InterruptedException e){
                runOnUiThread(() -> {
                    EsconderProgress();
                    Toast.makeText(this, "No se pudo agregar la nota", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}