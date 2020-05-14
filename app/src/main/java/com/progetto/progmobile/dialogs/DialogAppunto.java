package com.progetto.progmobile.dialogs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Appunto;


public class DialogAppunto extends DialogFragment implements View.OnClickListener {
    private EditText titoloAppunto, testoAppunto;
    private TextView textAppunto;
    private String path, path2;
    private Appunto appunto;

    public DialogAppunto(String path2) {
        this.appunto = null;
        this.path = null;
        this.path2 = path2;
    }

    public DialogAppunto(Appunto appunto, String path) {
        this.appunto = appunto;
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
        View view = inflater.inflate(R.layout.dialog_appunto, container, false);
        ImageButton chiudi = view.findViewById(R.id.dialogAppuntoChiudi);
        titoloAppunto = view.findViewById(R.id.textAppuntoTitolo);
        textAppunto = view.findViewById(R.id.textAppunto);
        testoAppunto = view.findViewById(R.id.dialogAppuntoTesto);
        Button aggiungi = view.findViewById(R.id.dialogAppuntoButtonAdd);

        if(this.path != null) {
            textAppunto.setText("Visualizza appunto");
            titoloAppunto.setText(appunto.getTitolo());
            testoAppunto.setText(appunto.getTesto());


        } else textAppunto.setText("Inserimento Appunto");


        //descrizioneAttivita.setScroller(new Scroller(getContext()));
        // descrizioneAttivita.setVerticalScrollBarEnabled(true);
        //descrizioneAttivita.setHorizontallyScrolling(false);
        //descrizioneAttivita.setLines();
        //descrizioneAttivita.setHorizontalScrollBarEnabled(false);

        chiudi.setOnClickListener(this);
         aggiungi.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialogAppuntoChiudi: dismiss(); break;
            case R.id.dialogAppuntoButtonAdd:
                String titolo = titoloAppunto.getText().toString();
                String testo = testoAppunto.getText().toString();

                if (testo.trim().isEmpty() || titolo.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please insert a title, a text", Toast.LENGTH_SHORT).show();
                } else {

                    if(path != null) {
                        FirebaseFirestore.getInstance().document(path).set(new Appunto(titolo, testo));
                        Toast.makeText(getContext(), "Appunto modificata", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        CollectionReference appuntiRef = FirebaseFirestore.getInstance().document(path2).collection("Appunti");
                        appuntiRef.add(new Appunto(titolo, testo));
                        Toast.makeText(getContext(), "Appunto aggiunto", Toast.LENGTH_LONG).show();
                    }
                    dismiss();
                }
                break;
        }
    }
}
