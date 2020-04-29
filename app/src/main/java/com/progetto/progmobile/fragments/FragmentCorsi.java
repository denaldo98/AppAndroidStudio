package com.progetto.progmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.progmobile.AdapterToDo;
import com.progetto.progmobile.R;
import com.progetto.progmobile.dialogs.FullScreenDialog;
import com.progetto.progmobile.entities.Attivita;

import java.util.ArrayList;
import java.util.Random;

public class FragmentCorsi extends Fragment {

    private ImageButton btnAdd;

    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orario, container, false);

        btnAdd = view.findViewById(R.id.button_add_event);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = FullScreenDialog.newInstance();
                dialog.show(getFragmentManager(), "tag");
            }
        });

        return view;


    }




}
