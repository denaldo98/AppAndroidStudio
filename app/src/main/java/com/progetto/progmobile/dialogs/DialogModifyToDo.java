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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.progetto.progmobile.entities.Attivita;

import java.text.DateFormat;
import java.util.Calendar;

public class DialogModifyToDo extends DialogFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText nomeAttivita, descrizioneAttivita;
    private RadioGroup prioritaGroup;
    private TextView dataScelta, txtAttivita;
    private String path;
    private Attivita attivita;


    public DialogModifyToDo(Attivita attivita, String path) {
        this.attivita = attivita;
        this.path = path;
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
        View view = inflater.inflate(R.layout.dialog_to_do_add_nuovo2, container, false);
        ImageButton chiudi = view.findViewById(R.id.dialogToDoChiudi);

        nomeAttivita = view.findViewById(R.id.dialogToDoNome);
        nomeAttivita.setText(attivita.getNome());
        txtAttivita = view.findViewById(R.id.textAttivita);
        txtAttivita.setText("Modifica Attività");
        descrizioneAttivita = view.findViewById(R.id.dialogToDoDescrizione);
        descrizioneAttivita.setText(attivita.getDescrizione());
        ImageButton pickDate = view.findViewById(R.id.pickDate);
        Button aggiungi = view.findViewById(R.id.dialogToDoButtonAdd);
        prioritaGroup = view.findViewById(R.id.dialogToDoPrioritaGroup);
        if(attivita.getPriorita() == 1)
            prioritaGroup.check(R.id.bassaPriorita);
        else if(attivita.getPriorita() == 2)
            prioritaGroup.check(R.id.mediaPrioria);
        else prioritaGroup.check(R.id.altaPriorita);

        dataScelta = view.findViewById(R.id.dataScelta);
        dataScelta.setText(attivita.getData());

        chiudi.setOnClickListener(this);
        aggiungi.setOnClickListener(this);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
            case R.id.dialogToDoChiudi: dismiss(); break;
            case R.id.dialogToDoButtonAdd:
                int numPriorita = prioritaGroup.getCheckedRadioButtonId();
                RadioButton radioButton = prioritaGroup.findViewById(numPriorita);
                int valorePriorita;
                switch (radioButton.getText().toString()){
                    case "Bassa" : valorePriorita = 1; break;
                    case "Media" : valorePriorita = 2; break;
                    default : valorePriorita = 3; break;

                }
                String nome = nomeAttivita.getText().toString();
                String descrizione = descrizioneAttivita.getText().toString();
                String data = dataScelta.getText().toString();

                if (nome.trim().isEmpty() || descrizione.trim().isEmpty()||data.equals("Scadenza")) {
                    Toast.makeText(getContext(), "Please insert a title, a date and a description", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore.getInstance().document(path).set(new Attivita(nome, valorePriorita, descrizione, data));
                    Toast.makeText(getContext(), "Attività modificata", Toast.LENGTH_LONG).show();
                    dismiss();
                }
                break;
        }
    }
}