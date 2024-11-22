package com.chumbeke.agenda;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chumbeke.agenda.ObjetosUsuariosYNotas.Notas;
import com.chumbeke.agenda.databinding.ActivityAgregarNotaBinding;
import com.chumbeke.agenda.databinding.ActivityEditorDeNotasBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditorDeNotas extends AppCompatActivity {
    private ActivityEditorDeNotasBinding binding;
    int dia,mes,year;
    ArrayList<String> Datos = new ArrayList<>();
    ArrayList<Notas> ListaNotas = new ArrayList<>();
    AlertDialog progressDialog;
    ManejoDB database;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorDeNotasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Datos = getIntent().getStringArrayListExtra("DatosParaEditar");
        String fecha = getIntent().getStringExtra("Fecha");
        String titulo = getIntent().getStringExtra("Titulo");
        String descripcion = getIntent().getStringExtra("Descripcion");
        database = new ManejoDB(this);
        id = database.BuscarNotaParaEdit(fecha,titulo,descripcion,Datos.get(0));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_progress);
        builder.setCancelable(false);
        progressDialog = builder.create();




        binding.Titulo.setText(titulo);
        binding.Descripcion.setText(descripcion);
        binding.NombreUsuaruiNota.setText(Datos.get(0));
        binding.CorreoUsuarioNota.setText(Datos.get(2));
        binding.FechaHora.setText(fecha);

        binding.BtnCalendario.setOnClickListener(view -> {
            final Calendar calendario = Calendar.getInstance();
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            year = calendario.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    EditorDeNotas.this,
                    (datePicker, yearSeleccionado, mesSeleccionado, diaSeleccionado) -> {
                        String DiaForma = String.format("%02d", diaSeleccionado);
                        String MesForma = String.format("%02d", mesSeleccionado + 1);
                        binding.Fecha.setText(DiaForma + "/" + MesForma + "/" + yearSeleccionado);
                    },
                    year, mes, dia
            );

            datePickerDialog.show();
        });

        binding.ActualizarNota.setOnClickListener(view -> {

            MostrarProgress();
            AgregarNota();

        });
        binding.EliminarNota.setOnClickListener(view -> {
            MostrarProgress();
            EliminarNota();
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

    @SuppressLint("NotConstructor")
    public void AgregarNota(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);


                Notas notas = new Notas(Datos.get(2),Datos.get(0),binding.Titulo.getText().toString(),
                        binding.Descripcion.getText().toString(),binding.Fecha.getText().toString());
                database.EditarNOtas(id,notas.getTitulo(),notas.getDescripcion(),notas.getFecha());
                runOnUiThread(() -> {
                    EsconderProgress();
                    Toast.makeText(this, "Nota agregada con exito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditorDeNotas.this,MenuPrincipal.class);
                    intent.putStringArrayListExtra("EditorNotas",Datos);
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
    public void EliminarNota(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);

                boolean ElimExitosa = database.EliminarNota(id);
                if (ElimExitosa == true){
                    runOnUiThread(() -> {
                        EsconderProgress();
                        Toast.makeText(this, "Se elimino con exito la nota", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditorDeNotas.this,MenuPrincipal.class);
                        intent.putStringArrayListExtra("EditorNotas",Datos);
                        startActivity(intent);
                    });
                }else {
                    runOnUiThread(() -> {
                        EsconderProgress();
                        Toast.makeText(this, "No se pudo eliminar la nota", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditorDeNotas.this,MenuPrincipal.class);
                        intent.putStringArrayListExtra("EditorNotas",Datos);
                        startActivity(intent);
                    });
                }


            }catch (InterruptedException e){
                runOnUiThread(() -> {
                    EsconderProgress();
                    Toast.makeText(this, "No se pudo agregar la nota", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}