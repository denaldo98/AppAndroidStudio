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
import com.progetto.progmobile.AdapterAppelli;
import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Appello;

public class FragmentAppelli extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference appelliRef = db.collection("utenti").document(user.getUid()).collection("Appelli");

    private AdapterAppelli adapter;

    private ImageButton btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appelli, container, false);
        Query query = appelliRef.orderBy("materia", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Appello> options = new FirestoreRecyclerOptions.Builder<Appello>().setQuery(query, Appello.class).build();
        adapter = new AdapterAppelli(options);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewAppelli);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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
