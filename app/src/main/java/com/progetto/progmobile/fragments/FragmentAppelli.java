package com.progetto.progmobile.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.progetto.progmobile.AdapterAppelli;
import com.progetto.progmobile.R;
import com.progetto.progmobile.dialogs.DialogAppello;
import com.progetto.progmobile.entities.Appello;

public class FragmentAppelli extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference appelliRef = db.collection("utenti").document(user.getUid()).collection("Appelli");

    private AdapterAppelli adapter2;

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
        adapter2 = new AdapterAppelli(options);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewAppelli);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter2);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {  //il primo parametro è per il DRAG che non consideriamo, il secondo paramtro è per le direzione di swipe
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) { //onMove method is for drag and drop
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) { //onSwipe is for swipe movements
                //AlertDialog per confermare l'eliminazione con lo swipe
                AlertDialog.Builder removeAlert = new AlertDialog.Builder(getContext());
                removeAlert.setTitle("Conferma eliminazione");
                removeAlert.setMessage("Per favore, conferma di voler eliminare l'appello!");
                removeAlert.setIcon(R.drawable.ic_error_black_24dp);

                removeAlert.setCancelable(false);
                removeAlert.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter2.deleteItem(viewHolder.getAdapterPosition());
                        Toast.makeText(getContext(), "Appello eliminato!", Toast.LENGTH_LONG).show();
                    }
                });

                removeAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter2.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = removeAlert.create();
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter2.setOnItemClickListener(new AdapterAppelli.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Appello appello = documentSnapshot.toObject(Appello.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath(); //ottengo il path del documento che posso passare ad un altra activity ad esempio per modificare
                appello.getMateria();

                DialogAppello dialogModifyAppello = new DialogAppello(appello, path);
                assert getFragmentManager() != null;
                dialogModifyAppello.show(getFragmentManager(), "tag");
            }
        });


        btnAdd = view.findViewById(R.id.button_add_appelli);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAppello dialogInsert =  new DialogAppello();
                assert getFragmentManager() != null;
                dialogInsert.show(getFragmentManager(), "tag");
            }
        });

        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter2.stopListening();
    }
}
