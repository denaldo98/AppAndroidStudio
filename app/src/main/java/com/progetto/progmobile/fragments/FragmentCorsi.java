package com.progetto.progmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.progetto.progmobile.AdapterToDo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.progetto.progmobile.AdapterCorsi;
import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Attivita;

import java.util.ArrayList;
import java.util.Random;

public class FragmentCorsi extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference todoRef = db.collection("utenti").document(user.getUid()).collection("Corsi");

    private AdapterCorsi adapter;

    private ImageButton btnAdd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_corsi, container, false);
    }
}
