package com.progetto.progmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CViewHolder> {
    int lunghezzaMax=60; //eventualmente anche meno
    String stringheAttivit[];
    String stringheDate[];
    String stringheDescriz[];
    int[] priorita;
    public MyAdapter(String[] attivit, String[] date, String[] descriz, int[] prio){
        stringheAttivit = attivit;
        stringheDate = date;
        stringheDescriz = descriz;
        priorita = prio;
    }

    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row, parent, false);
        return new CViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {
        holder.attivit.setText(stringheAttivit[position]);
        holder.data.setText(stringheDate[position]);
        if(stringheDescriz[position].length()>lunghezzaMax)
            holder.descriz.setText(stringheDescriz[position].substring(0,lunghezzaMax-4)+"...");
        else
            holder.descriz.setText(stringheDescriz[position]);
        switch (priorita[position]) {
            case 1 : holder.priorita.setImageResource(R.drawable.verde); break;
            case 2 : holder.priorita.setImageResource(R.drawable.giallo); break;
            default: holder.priorita.setImageResource(R.drawable.rosso);
        }
    }

    //Context context;
    @Override
    public int getItemCount(){
        return stringheAttivit.length;
    }

    class CViewHolder extends RecyclerView.ViewHolder{
        TextView attivit;
        TextView data;
        TextView descriz;
        ImageView priorita;
        CViewHolder (View itemView){
            super(itemView);
            attivit = itemView.findViewById(R.id.textAttivita);
            data= itemView.findViewById(R.id.textData);
            descriz = itemView.findViewById(R.id.textDescrizione);
            priorita = itemView.findViewById(R.id.cerchioPriorita);

        }
    }
}
