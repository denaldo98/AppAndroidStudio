package com.progetto.progmobile.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Appunto;
import com.progetto.progmobile.entities.Corso;
import com.progetto.progmobile.uiutilities.AdapterAppunti;

public class DialogCorsoVisualizza extends DialogFragment{

    private TextView nomeCorso, nomeProfessore, emailProfessore, numeroCFU;
    private RecyclerView appunti;
    private String path;
    private Corso corso;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference appuntiRef;
    private ImageButton btnAdd;
    private AdapterAppunti adapterAppunti;

    public DialogCorsoVisualizza (Corso corso, String path) {
        this.corso = corso;
        this.path = path;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_corso_visualizza, container, false);
        ImageButton chiudi = view.findViewById(R.id.dialogCorsoChiudi);
        nomeCorso = view.findViewById(R.id.nomeCorso);
        nomeProfessore = view.findViewById(R.id.nomeProfessore);
        emailProfessore = view.findViewById(R.id.emailProfessore);
        numeroCFU = view.findViewById(R.id.numeroCFU);
        Button modifica = view.findViewById(R.id.dialogCorsoButtonModify);
        nomeCorso.setText(corso.getNome());
        nomeProfessore.setText(corso.getNomeProfessore());
        numeroCFU.setText(String.format("%d", corso.getNumeroCFU()));
        emailProfessore.setText(corso.getEmailProfessore());


        appuntiRef = db.document(path).collection("Appunti");

        Query query = appuntiRef.orderBy("titolo", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Appunto> options = new FirestoreRecyclerOptions.Builder<Appunto>().setQuery(query, Appunto.class).build();
        adapterAppunti = new AdapterAppunti(options);
        appunti = (RecyclerView)view.findViewById(R.id.recyclerViewAppunti);
        appunti.setHasFixedSize(true);
        appunti.setLayoutManager(new LinearLayoutManager(getContext()));
        appunti.setAdapter(adapterAppunti);
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
                removeAlert.setMessage("Per favore, conferma di voler eliminare l'appunto!");
                removeAlert.setIcon(R.drawable.ic_error_black_24dp);

                removeAlert.setCancelable(false);
                removeAlert.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapterAppunti.deleteItem(viewHolder.getAdapterPosition());
                        Toast.makeText(getContext(), "Appunto eliminato!", Toast.LENGTH_LONG).show();
                    }
                });

                removeAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapterAppunti.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = removeAlert.create();
                alert.show();
            }
        }).attachToRecyclerView(appunti);

        adapterAppunti.setOnItemClickListener(new AdapterAppunti.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Appunto appunto = documentSnapshot.toObject(Appunto.class);

                String path2 = documentSnapshot.getReference().getPath(); //ottengo il path del documento che posso passare ad un altra activity ad esempio per modificare




                DialogAppunto dialogModifyAppunto = new DialogAppunto(appunto, path2);
                assert getFragmentManager() != null;
                dialogModifyAppunto.show(getFragmentManager(), "tag");
            }
        });

        btnAdd = view.findViewById(R.id.button_add_appunto);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAppunto dialogAppunto = new DialogAppunto(path);
                assert getFragmentManager() != null;
                dialogAppunto.show(getFragmentManager(), "tag");
            }
        });




        chiudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCorso dialogCorso = new DialogCorso(corso,path);
                assert getFragmentManager() != null;
                dismiss();
                dialogCorso.show(getFragmentManager(), "tag");
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterAppunti.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterAppunti.stopListening();
    }

}
