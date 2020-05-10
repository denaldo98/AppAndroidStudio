package com.progetto.progmobile.uiutilities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Evento;

public class AdapterOrario extends FirestoreRecyclerAdapter<Evento, AdapterOrario.OrarioHolder> {
    private OnItemClickListener listener;

    public AdapterOrario(@NonNull FirestoreRecyclerOptions<Evento> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrarioHolder holder, int position, @NonNull Evento model) {
        holder.textNome.setText(model.getNome());
        holder.textAula.setText(model.getAula());
        holder.textOraInizio.setText(model.getOraInizio());
        holder.textOraFine.setText(model.getOraFine());
    }

    @NonNull
    @Override
    public OrarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_orario, parent, false);
        return new OrarioHolder(v);
    }

    public void deleteItem(int position) {
        // getSnapshots returns all the document snapshots; getSnapshot returns dhe particular item on this position
        getSnapshots().getSnapshot(position).getReference().delete();
    }



    class OrarioHolder extends RecyclerView.ViewHolder {
        TextView textNome, textAula, textOraInizio, textOraFine;

        public OrarioHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNome);
            textAula = itemView.findViewById(R.id.textAula);
            textOraInizio = itemView.findViewById(R.id.textOraInizio);
            textOraFine = itemView.findViewById(R.id.textOraFine);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot , int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}



