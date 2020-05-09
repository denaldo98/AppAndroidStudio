package com.progetto.progmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.progetto.progmobile.AdapterCorsi;

import com.progetto.progmobile.R;

import com.progetto.progmobile.dialogs.DialogCorso;
import com.progetto.progmobile.dialogs.DialogToDo;
import com.progetto.progmobile.entities.Appunto;
import com.progetto.progmobile.entities.Corso;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class FragmentCorsi extends Fragment  {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference corsiRef = db.collection("utenti").document(user.getUid()).collection("Corsi");


    private AdapterCorsi adapter;

    private ImageButton btnAdd;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_corsi, container, false);

        Query query = corsiRef.orderBy("nome", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Corso> options = new FirestoreRecyclerOptions.Builder<Corso>().setQuery(query, Corso.class).build();

        adapter = new AdapterCorsi(options);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewCorsi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



        //classe per operazioni di swipe sulla recycler view: facendo swipe sia verso destra che verso sinistra si cancella un item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {  //il primo parametro è per il DRAG che non consideriamo, il secondo paramtro è per le direzione di swipe
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) { //onMove method is for drag and drop
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) { //onSwipe is for swipe movements
                //AlertDialog per confermare l'eliminazione con lo swipe
                AlertDialog.Builder removeAlert = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                removeAlert.setTitle("Conferma eliminazione");
                removeAlert.setMessage("Per favore, conferma di voler eliminare il corso!");
                removeAlert.setIcon(R.drawable.ic_error_black_24dp);

                removeAlert.setCancelable(false);
                removeAlert.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.deleteItem(viewHolder.getAdapterPosition());
                        Toast.makeText(getContext(), "Corso eliminato!", Toast.LENGTH_LONG).show();
                    }
                });

                removeAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = removeAlert.create();
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AdapterCorsi.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Corso corso = documentSnapshot.toObject(Corso.class);

                String path = documentSnapshot.getReference().getPath(); //ottengo il path del documento che posso passare ad un altra activity ad esempio per modificare
                DialogCorso dialogModifyCorso = new DialogCorso(corso, path);
                assert getFragmentManager() != null;
                dialogModifyCorso.show(getFragmentManager(), "tag");
            }
        });


        btnAdd = view.findViewById(R.id.button_add_corsi);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCorso dialogCorso = new DialogCorso();
                assert getFragmentManager() != null;
                dialogCorso.show(getFragmentManager(), "tag");
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
