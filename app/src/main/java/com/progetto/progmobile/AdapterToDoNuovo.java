package com.progetto.progmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.progetto.progmobile.entities.Attivita;

public class AdapterToDoNuovo extends FirestoreRecyclerAdapter<Attivita, AdapterToDoNuovo.ToDoHolder> {
    public AdapterToDoNuovo(@NonNull FirestoreRecyclerOptions<Attivita> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ToDoHolder toDoHolder, int i, @NonNull Attivita attivita) {
    toDoHolder.textNome.setText(attivita.getNome());
    toDoHolder.textData.setText(attivita.getData());
    toDoHolder.textDescrizione.setText(attivita.getDescrizione());
    switch(attivita.getPriorita()) {
        case 1: toDoHolder.immaginePriorita.setImageResource(R.drawable.verde); break;
        case 2: toDoHolder.immaginePriorita.setImageResource(R.drawable.giallo); break;
        default: toDoHolder.immaginePriorita.setImageResource(R.drawable.rosso); break;
        }
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_to_do_nuovo,parent,false);
        return new ToDoHolder(v);
    }

    class ToDoHolder extends RecyclerView.ViewHolder{
        TextView textNome, textData, textDescrizione;
        ImageView immaginePriorita;

        public ToDoHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textToDoNome);
            textData = itemView.findViewById(R.id.textToDoData);
            textDescrizione = itemView.findViewById(R.id.textToDoDescrizione);
            immaginePriorita = itemView.findViewById(R.id.immagineToDoPriorita);
        }
    }
}
