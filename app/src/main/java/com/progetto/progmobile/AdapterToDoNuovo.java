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
    protected void onBindViewHolder(@NonNull ToDoHolder holder, int position, @NonNull Attivita model) {
        holder.textNome.setText(model.getNome());
        holder.textData.setText(model.getData());
        holder.textDescrizione.setText(model.getDescrizione());
       if(String.valueOf(model.getPriorita()).equals("1")) { holder.immaginePriorita.setImageResource(R.drawable.verde); }
       else if(String.valueOf(model.getPriorita()).equals("2")) { holder.immaginePriorita.setImageResource(R.drawable.giallo); }
       else holder.immaginePriorita.setImageResource(R.drawable.rosso);

        }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_to_do_nuovo, parent, false);
        return new ToDoHolder(v);
    }

    public void deleteItem(int position) {
        // getSnapshots returns all the document snapshots; getSnapshot returns dhe particular item on this position
        getSnapshots().getSnapshot(position).getReference().delete();
    }



    class ToDoHolder extends RecyclerView.ViewHolder {
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
