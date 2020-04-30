package com.progetto.progmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.progmobile.AdapterToDo;
import com.progetto.progmobile.R;
import com.progetto.progmobile.dialogs.DialogToDoAdd;
import com.progetto.progmobile.entities.Attivita;

import java.util.ArrayList;

public class FragmentTodo extends Fragment{

    private RecyclerView recyclerView;
    private AdapterToDo adapterToDo;
    private ArrayList<Attivita> attivitaTutte;
    private ImageButton btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        attivitaTutte = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewToDo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterToDo = new AdapterToDo(attivitaTutte);
        recyclerView.setAdapter(adapterToDo);

        btnAdd = view.findViewById(R.id.button_add_to_do);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogToDoAdd dialog = DialogToDoAdd.newInstance();
                dialog.setCallback(new DialogToDoAdd.Callback() {
                    @Override
                    public void onAddClick(Attivita attivita) {
                        attivitaTutte.add(attivita);
                        adapterToDo.notifyDataSetChanged();
                    }
                });
                assert getFragmentManager() != null;
                dialog.show(getFragmentManager(), "tag");
                /*
                DialogToDoAddNuovo.Builder alert = new DialogToDoAddNuovo.Builder(getContext());
                alert.setView(R.layout.dialog_to_do_add);
                alert.setMessage("Nuova attività");
                alert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setPositiveButton("Aggiungi" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }) ;
                alert.show();




                String nome = randomString(15);
                Random random = new Random();
                int priorita = random.nextInt(3)+1;
                String descrizione = randomString(90);
                String data = "11/1/21";


                //openToDoDialog();
                Attivita attivita = new Attivita(nome, priorita, descrizione, data);
                attivitaTutte.add(attivita);*/


            }
        });

        return view;
    }

    private String randomString(int count) {
        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQERSTUVXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while(count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
