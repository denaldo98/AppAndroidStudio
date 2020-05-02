package com.progetto.progmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
//import com.progetto.progmobile.AdapterToDo;
import com.progetto.progmobile.AdapterToDoNuovo;
import com.progetto.progmobile.HomeActivity;
import com.progetto.progmobile.MainActivity;
import com.progetto.progmobile.R;
//import com.progetto.progmobile.dialogs.DialogToDoAdd;
import com.progetto.progmobile.dialogs.DialogToDoAdd;
import com.progetto.progmobile.entities.Attivita;

import java.util.ArrayList;

public class FragmentTodo extends Fragment{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference todoRef = db.collection("utenti").document(user.getUid()).collection("ToDo");

    private AdapterToDoNuovo adapter;

    private ImageButton btnAdd;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        Query query = todoRef.orderBy("priorita", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Attivita> options = new FirestoreRecyclerOptions.Builder<Attivita>().setQuery(query, Attivita.class).build();

        adapter = new AdapterToDoNuovo(options);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewToDo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        btnAdd = view.findViewById(R.id.button_add_to_do);
        //adapterToDo.notifyDataSetChanged();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogToDoAdd dialog = DialogToDoAdd.newInstance();
                assert getFragmentManager() != null;
                dialog.show(getFragmentManager(), "tag");
            }
        });

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
