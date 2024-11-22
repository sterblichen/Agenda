package com.chumbeke.agenda;

import android.content.Intent;
import android.os.Bundle;
import com.chumbeke.agenda.Adaptadores.ListaAdaptadores.OnItemClickListener;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chumbeke.agenda.Adaptadores.ListaAdaptadores;
import com.chumbeke.agenda.ObjetosUsuariosYNotas.Notas;
import com.chumbeke.agenda.databinding.ActivityListaDeNotasBinding;

import java.util.ArrayList;

public class ListaDeNotas extends AppCompatActivity {
    private ActivityListaDeNotasBinding binding;
    ArrayList<String> Datos = new ArrayList<>();
    ManejoDB database;
    ArrayList<Notas> listaArrayNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaDeNotasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Datos = getIntent().getStringArrayListExtra("US");

        listaArrayNotas = new ArrayList<>();
        binding.RecyclerNotas.setLayoutManager(new LinearLayoutManager(this));
        database = new ManejoDB(this);
        listaArrayNotas = database.TodasLasNotas(Datos.get(0));
        ListaAdaptadores adaptador = new ListaAdaptadores(listaArrayNotas, new ListaAdaptadores.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Notas notaSeleccionada = listaArrayNotas.get(position);

                Intent intent = new Intent(ListaDeNotas.this, EditorDeNotas.class);
                intent.putExtra("Titulo",notaSeleccionada.getTitulo());
                intent.putExtra("Fecha",notaSeleccionada.getFecha());
                intent.putExtra("Descripcion",notaSeleccionada.getDescripcion());
                intent.putStringArrayListExtra("DatosParaEditar",Datos);
                startActivity(intent);
            }
        });

        binding.RecyclerNotas.setAdapter(adaptador);
        binding.Atras.setOnClickListener(view -> {
            finish();
        });
    }


}