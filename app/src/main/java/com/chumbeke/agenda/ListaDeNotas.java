package com.chumbeke.agenda;

import android.os.Bundle;

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
    ArrayList<Notas> listaArrayNotas;
    ManejoDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaDeNotasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listaArrayNotas = new ArrayList<>();
        binding.RecyclerNotas.setLayoutManager(new LinearLayoutManager(this));
        database = new ManejoDB(this);
        ListaAdaptadores adaptador = new ListaAdaptadores(database.TodasLasNotas());

        binding.RecyclerNotas.setAdapter(adaptador);
    }


}