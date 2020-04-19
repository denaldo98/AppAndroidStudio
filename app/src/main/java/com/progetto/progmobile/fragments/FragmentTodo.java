package com.progetto.progmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.progmobile.MyAdapter;
import com.progetto.progmobile.R;

public class FragmentTodo extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    String Attivit[];
    String dateAttivit[];
    String descrizAttivit[];
    int[] priorita;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        Attivit = getResources().getStringArray(R.array.arrayAttivit);
        dateAttivit = getResources().getStringArray(R.array.scadenze);
        descrizAttivit = getResources().getStringArray(R.array.descrizioni);
        priorita = getResources().getIntArray(R.array.priorita);
        //recyclerView = getParentFragment().getView().findViewById(R.id.recyclerView);
        //recyclerView = findViewById(R.id.recyclerView); //View.?

        layoutManager = new LinearLayoutManager(getContext());//getContext dovrebbe essere ok ora
        //recyclerView.setLayoutManager(layoutManager);

       mAdapter = new MyAdapter(Attivit,dateAttivit,descrizAttivit,priorita);
        //recyclerView.setAdapter(mAdapter);
        return view;

    }


}
