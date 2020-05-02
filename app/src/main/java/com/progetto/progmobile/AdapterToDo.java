package com.progetto.progmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.progmobile.entities.Attivita;

import java.util.ArrayList;

public class AdapterToDo extends RecyclerView.Adapter<AdapterToDo.CViewHolder> {

    class CViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textData, textDescrizione;
        ImageView immaginePriorita;

        CViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textToDoNome);
            textData = itemView.findViewById(R.id.textToDoData);
            textDescrizione = itemView.findViewById(R.id.textToDoDescrizione);
            immaginePriorita = itemView.findViewById(R.id.immagineToDoPriorita);
        }
    }

    private ArrayList<Attivita> struttura;

    public AdapterToDo(ArrayList<Attivita> struttura) {
        this.struttura = struttura;
    }

    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_to_do, parent, false);
        return new CViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {
        holder.textNome.setText(struttura.get(position).getNome());
        holder.textData.setText(struttura.get(position).getData());
        holder.textDescrizione.setText(struttura.get(position).getDescrizione());
        switch (struttura.get(position).getPriorita()){
            case 1 : holder.immaginePriorita.setImageResource(R.drawable.verde); break;
            case 2 : holder.immaginePriorita.setImageResource(R.drawable.giallo); break;
            default : holder.immaginePriorita.setImageResource(R.drawable.rosso); break;
        }
    }

    @Override
    public int getItemCount() {
        if(struttura!=null)
        return struttura.size();
        else return 0;
    }
}
