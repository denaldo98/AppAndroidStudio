package com.progetto.progmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    int lunghezzaMax=60; //eventualmente anche meno
    String stringheAttivit[];
    String stringheDate[];
    String stringheDescriz[];
    int[] priorita;
    Context context;
    public Adapter(Context ct, String[] attivit, String[] date, String[] descriz, int[] prio){
        context = ct;
        stringheAttivit = attivit;
        stringheDate = date;
        stringheDescriz = descriz;
        priorita = prio;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.riga, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

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
    @Override
    public int getItemCount() {
        return stringheAttivit.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView attivit;
        TextView data;
        TextView descriz;
        ImageView priorita;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            attivit = itemView.findViewById(R.id.textAttivita);
            data= itemView.findViewById(R.id.textData);
            descriz = itemView.findViewById(R.id.textDescrizione);
            priorita = itemView.findViewById(R.id.cerchioPriorita);
        }


    }
}
