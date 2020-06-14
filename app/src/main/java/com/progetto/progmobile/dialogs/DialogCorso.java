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
import com.progetto.progmobile.entities.Corso;


public class DialogCorso extends DialogFragment implements View.OnClickListener {

    private EditText nomeCorso, nomeProfessore, emailProfessore, numeroCFU;
    private TextView txtCorso;
    private String path;
    private Corso corso;

    public DialogCorso(Corso corso, String path) {
        this.corso = corso;
        this.path  = path;
    }

    public DialogCorso() {
        this.corso = null;
        this.path = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_corso, container, false);
        ImageButton chiudi = view.findViewById(R.id.dialogCorsoChiudi);
        nomeCorso = view.findViewById(R.id.nomeCorso);
        txtCorso = view.findViewById(R.id.txtCorso);
        nomeProfessore = view.findViewById(R.id.nomeProfessore);
        emailProfessore = view.findViewById(R.id.emailProfessore);
        numeroCFU = view.findViewById(R.id.numeroCFU);

        Button aggiungi = view.findViewById(R.id.dialogCorsoButtonAdd);

        if( this.path != null ) {
            txtCorso.setText("Modifica Corso");
            nomeCorso.setText(corso.getNome());
            nomeProfessore.setText(corso.getNomeProfessore());
            emailProfessore.setText(corso.getEmailProfessore());
            numeroCFU.setText(String.format("%d", corso.getNumeroCFU()));

        } else txtCorso.setText("Inserimento Corso");




        chiudi.setOnClickListener(this);
        aggiungi.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialogCorsoChiudi: dismiss(); break;
            case R.id.dialogCorsoButtonAdd:
                String stringNomeCorso = nomeCorso.getText().toString();
                String stringNomeProfessore = nomeProfessore.getText().toString();
                String stringEmailProfessore = emailProfessore.getText().toString();
                //int intNumeroCFU = Integer.parseInt(numeroCFU.getText().toString());

                if (stringNomeCorso.trim().isEmpty() || stringNomeProfessore.trim().isEmpty() || stringEmailProfessore.trim().isEmpty() || numeroCFU.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please insert a course name, professor, email, cfu", Toast.LENGTH_SHORT).show();
                } else {
                    if(path != null) {
                        int intNumeroCFU = Integer.parseInt(numeroCFU.getText().toString());
                        FirebaseFirestore.getInstance().document(path).set(new Corso(stringNomeCorso, stringNomeProfessore, intNumeroCFU, stringEmailProfessore));
                        Toast.makeText(getContext(), "Corso modificato", Toast.LENGTH_LONG).show();
                    }
                    else{
                        int intNumeroCFU = Integer.parseInt(numeroCFU.getText().toString());
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        CollectionReference CorsiRef = FirebaseFirestore.getInstance().collection("utenti").document(user.getUid()).collection("Corsi");
                        CorsiRef.add(new Corso(stringNomeCorso, stringNomeProfessore, intNumeroCFU, stringEmailProfessore));
                        Toast.makeText(getContext(), "Corso aggiunto", Toast.LENGTH_LONG).show();
                    }
                    dismiss();
                }
                break;
        }
    }

}