package com.progetto.progmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.progmobile.R;

public class FragmentTodo extends Fragment {

    RecyclerView reciclerView;
    String Attivit[];
    String dateAttivit[];
    String descrizAttivit[];
    int[] priorita;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Attivit = getResources().getStringArray(R.array.arrayAttivit);
        dateAttivit = getResources().getStringArray(R.array.scadenze);
        descrizAttivit = getResources().getStringArray(R.array.descrizioni);
        priorita = getResources().getIntArray(R.array.priorita);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        return view;

    }


}
