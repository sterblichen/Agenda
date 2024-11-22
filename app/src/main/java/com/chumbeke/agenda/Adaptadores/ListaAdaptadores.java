package com.chumbeke.agenda.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chumbeke.agenda.ObjetosUsuariosYNotas.Notas;
import com.chumbeke.agenda.R;

import java.util.ArrayList;

public class ListaAdaptadores  extends RecyclerView.Adapter<ListaAdaptadores.NotasViewHolder> {

    ArrayList<Notas> ListaNotas;
    private OnItemClickListener listener;

    public ListaAdaptadores(ArrayList<Notas> ListaNotas,OnItemClickListener listener){
        this.ListaNotas = ListaNotas;
        this.listener = listener;
    }
    @NonNull
    @Override
    public NotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objeto_notas,parent,false);
        return new NotasViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull NotasViewHolder holder, int position) {
        holder.ObjetoNotaTitulo.setText(ListaNotas.get(position).getTitulo());
        holder.ObjetoNotaDescripcion.setText(ListaNotas.get(position).getDescripcion());
        holder.ObjetoNotaFecha.setText(ListaNotas.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return ListaNotas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class NotasViewHolder extends RecyclerView.ViewHolder {

        TextView ObjetoNotaTitulo, ObjetoNotaDescripcion,ObjetoNotaFecha;

        public NotasViewHolder(@NonNull View itemView) {
            super(itemView);
            ObjetoNotaTitulo = itemView.findViewById(R.id.ObjetoNotaTitulo);
            ObjetoNotaDescripcion= itemView.findViewById(R.id.ObjetoNotaDescripcion);
            ObjetoNotaFecha = itemView.findViewById(R.id.ObjetoNotaFecha);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
