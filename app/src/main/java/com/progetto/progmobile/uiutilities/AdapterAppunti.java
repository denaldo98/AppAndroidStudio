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
import com.progetto.progmobile.entities.Appunto;

public class AdapterAppunti extends FirestoreRecyclerAdapter<Appunto, AdapterAppunti.AppuntoHolder> {

    private OnItemClickListener listener;

    public AdapterAppunti(@NonNull FirestoreRecyclerOptions<Appunto> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull AppuntoHolder holder, int position, @NonNull Appunto model) {
        holder.textTitolo.setText(model.getTitolo());

    }

    @NonNull
    @Override
    public AppuntoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_appunto, parent, false);
        return new AppuntoHolder(v);
    }

    public void deleteItem(int position) {
        // getSnapshots returns all the document snapshots; getSnapshot returns dhe particular item on this position
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    class AppuntoHolder extends RecyclerView.ViewHolder {
        TextView textTitolo;

        public AppuntoHolder(@NonNull View itemView) {
            super(itemView);
            textTitolo = itemView.findViewById(R.id.textTitolo);

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
