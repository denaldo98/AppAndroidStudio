package com.progetto.progmobile.fragments.tabsFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.progetto.progmobile.AdapterOrario;

import com.progetto.progmobile.R;
import com.progetto.progmobile.dialogs.DialogOrario;

import com.progetto.progmobile.entities.Evento;


public class tab1 extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference lunediRef = db.collection("utenti").document(user.getUid()).collection("Lunedì");

    private AdapterOrario adapterOrario;

    public tab1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        Query query = lunediRef.orderBy("oraInizio", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Evento> options = new FirestoreRecyclerOptions.Builder<Evento>().setQuery(query, Evento.class).build();

        adapterOrario = new AdapterOrario(options);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewOrario1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterOrario);



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
                removeAlert.setMessage("Per favore, conferma di voler eliminare l'attività!");
                removeAlert.setIcon(R.drawable.ic_error_black_24dp);

                removeAlert.setCancelable(false);
                removeAlert.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapterOrario.deleteItem(viewHolder.getAdapterPosition());
                        Toast.makeText(getContext(), "Attività eliminata!", Toast.LENGTH_LONG).show();
                    }
                });

                removeAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapterOrario.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = removeAlert.create();
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);

        adapterOrario.setOnItemClickListener(new AdapterOrario.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Evento evento = documentSnapshot.toObject(Evento.class);

                String path = documentSnapshot.getReference().getPath(); //ottengo il path del documento che posso passare ad un altra activity ad esempio per modificare

                //String id = documentSnapshot.getId();
                //attivita.getDescrizione();
                //documentSnapshot.getReference();
                //Toast.makeText(getContext(), "Position: " + position + " ID: " + id , Toast.LENGTH_SHORT).show();

                //startActivity(); posso lanciare un altra activity e fare modifiche sul db, devo passare l'id del document!!!!!!!!!!

                DialogOrario dialogModifyOrario = new DialogOrario(evento, path);
                assert getFragmentManager() != null;
                dialogModifyOrario.show(getFragmentManager(), "tag");
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapterOrario.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterOrario.stopListening();
    }
}
