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
import com.progetto.progmobile.entities.Appello;

import java.text.DateFormat;
import java.util.Calendar;

public class DialogAppello extends DialogFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText nomeMateria;
    private TextView dataScelta, txtAppello;
    private String path;
    private Appello appello;

    public DialogAppello(Appello appello, String path) {
        this.appello = appello;
        this.path  = path;
    }

    public DialogAppello() {
        this.appello = null;
        this.path = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dataScelta.setText(currentDateString);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_appello, container, false);
        ImageButton chiudi = view.findViewById(R.id.dialogAppelloChiudi);
        nomeMateria = view.findViewById(R.id.dialogAppelloMateria);
        txtAppello = view.findViewById(R.id.txtAppello);

        ImageButton pickDate = view.findViewById(R.id.pickDate);
        Button aggiungi = view.findViewById(R.id.dialogAppelloButtonAdd);
        dataScelta = view.findViewById(R.id.dataScelta);

        if( this.path != null ) {
        txtAppello.setText("Modifica Appello");
        nomeMateria.setText(appello.getMateria());
        dataScelta.setText(new StringBuilder().append(appello.getGiorno()).append("/").append(appello.getMese()).append("/").append(appello.getAnno()).toString());
        } else txtAppello.setText("Inserimento Appello");




        chiudi.setOnClickListener(this);
        aggiungi.setOnClickListener(this);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String scadenza = dayOfMonth+"/"+(month+1)+"/"+year;
                        dataScelta.setText(scadenza);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialogAppelloChiudi: dismiss(); break;
            case R.id.dialogAppelloButtonAdd:
                String materia = nomeMateria.getText().toString();
                String data = dataScelta.getText().toString();

                if (materia.trim().isEmpty()||data.equals("Scadenza")) {
                    Toast.makeText(getContext(), "Please insert a titleand a date", Toast.LENGTH_SHORT).show();
                } else {
                    String delimiter = "/";
                    String[] dataseparata = data.split(delimiter);
                    String toast = dataseparata[0]+" : "+ dataseparata[1]+" : "+ dataseparata[2];
                    int giorno = Integer.parseInt(dataseparata[0]);
                    int mese = Integer.parseInt(dataseparata[1]);
                    int anno = Integer.parseInt(dataseparata[2]);
                    if(path != null) {
                        FirebaseFirestore.getInstance().document(path).set(new Appello(materia, anno, mese, giorno));
                        Toast.makeText(getContext(), "Appello modificato", Toast.LENGTH_LONG).show();
                    }
                    else{
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        CollectionReference AppelliRef = FirebaseFirestore.getInstance().collection("utenti").document(user.getUid()).collection("Appelli");
                        AppelliRef.add(new Appello(materia, anno, mese, giorno));
                        Toast.makeText(getContext(), "Appello aggiunto", Toast.LENGTH_LONG).show();
                    }
                    dismiss();
                }
                break;
        }
    }

}