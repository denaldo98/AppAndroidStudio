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
import com.progetto.progmobile.entities.Appello;

public class AdapterAppelli extends FirestoreRecyclerAdapter<Appello, AdapterAppelli.AppelloHolder> {

    private OnItemClickListener listener;

    public AdapterAppelli(@NonNull FirestoreRecyclerOptions<Appello> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull AppelloHolder holder, int position, @NonNull Appello model) {
        holder.textMateria.setText(model.getMateria());
        holder.textData.setText(new StringBuilder().append(model.getGiorno()).append("/").append(model.getMese()).append("/").append(model.getAnno()).toString());

    }

    @NonNull
    @Override
    public AppelloHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_appello, parent, false);
        return new AppelloHolder(v);
    }

    public void deleteItem(int position) {
        // getSnapshots returns all the document snapshots; getSnapshot returns dhe particular item on this position
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    class AppelloHolder extends RecyclerView.ViewHolder {
        TextView textMateria, textData;

        public AppelloHolder(@NonNull View itemView) {
            super(itemView);
            textMateria = itemView.findViewById(R.id.textMateria);
            textData = itemView.findViewById(R.id.textData);

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
