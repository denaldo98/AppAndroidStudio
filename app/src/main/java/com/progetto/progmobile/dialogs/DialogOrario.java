package com.progetto.progmobile.dialogs;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Evento;

import java.util.Calendar;

import static com.progetto.progmobile.R.id.OraIButton;

public class DialogOrario extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private EditText nomeEvento, luogoEvento;
    private Button btnOraI, btnOraF; //bottoni che aprono i time-picker
    private Spinner spinner; //spinner giorno
    private String giorno;
    private String stringOraI, stringOraF;
    private int oraI, oraF, minutoI, minutoF;


    private String path;
    private Evento evento;

    public DialogOrario() {
        this.evento = null;
        this.path = null;
    }

    public DialogOrario(Evento evento, String path) {
        this.evento = evento;
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
        View view = inflater.inflate(R.layout.dialog_orario, container, false);

        ImageButton chiudi = view.findViewById(R.id.dialogOrarioChiudi);
        Button aggiungi = view.findViewById(R.id.dialogOrarioButtonAdd);
        nomeEvento = view.findViewById(R.id.text_orario_nome);
        luogoEvento = view.findViewById(R.id.text_luogo);

        spinner = (Spinner) view.findViewById(R.id.GiornoSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Giorni, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        chiudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //gestione dei bottoni time-picker
        btnOraI =(Button ) view.findViewById(OraIButton);
        btnOraI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current time
                final Calendar c = Calendar.getInstance();
                oraI = c.get(Calendar.HOUR_OF_DAY);
                minutoI = c.get(Calendar.MINUTE);

                //launch Time-picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                stringOraI = hourOfDay + ":" + minute;
                                btnOraI.setText(stringOraI);
                            }
                        }, oraI, minutoI, false);
                timePickerDialog.show();
            }
        });


        btnOraF =(Button ) view.findViewById(R.id.OraFButton);
        btnOraF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current time
                final Calendar c = Calendar.getInstance();
                oraF = c.get(Calendar.HOUR_OF_DAY);
                minutoF = c.get(Calendar.MINUTE);

                //launch Time-picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                stringOraF = hourOfDay + ":" + minute;
                                btnOraF.setText(stringOraF);
                            }
                        }, oraF, minutoF, false);
                timePickerDialog.show();
            }
        });



        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeEvento.getText().toString();
                String aula = luogoEvento.getText().toString();
                if(nome.trim().isEmpty() || stringOraI.isEmpty() || stringOraF.isEmpty()) {
                    Toast.makeText(getContext(), "Please insert a name ........", Toast.LENGTH_LONG).show();
                } else {
                    if(path != null) {
                        FirebaseFirestore.getInstance().document(path).set(new Evento(nome, aula , stringOraI , stringOraF ));
                        Toast.makeText(getContext(), "Evento modificato", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        CollectionReference todoRef = FirebaseFirestore.getInstance().collection("utenti").document(user.getUid()).collection(giorno);
                        todoRef.add(new Evento(nome, aula , stringOraI , stringOraF ));
                        Toast.makeText(getContext(), "Evento aggiunto", Toast.LENGTH_LONG).show();
                    }
                    dismiss();
                }


                }

        });


        return view;
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        giorno = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        giorno = "Lunedì";

    }
}