package com.progetto.progmobile.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.progetto.progmobile.HomeActivity;
import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Attivita;
import com.progetto.progmobile.fragments.FragmentTodo;

import java.util.HashMap;
import java.util.Map;

public class DialogToDoAdd extends DialogFragment implements View.OnClickListener{
    private EditText nomeAttivita, descrizioneAttivita, scadenzaAttivita;
    private RadioGroup prioritaGroup;
    private String nome;

    public static DialogToDoAdd newInstance(){
        return new DialogToDoAdd();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_to_do_add_nuovo, container, false);
        ImageButton chiudi = view.findViewById(R.id.dialogToDoChiudi);
        nomeAttivita = view.findViewById(R.id.dialogToDoNome);
        descrizioneAttivita = view.findViewById(R.id.dialogToDoDescrizione);
        scadenzaAttivita = view.findViewById(R.id.dialogToDoScadenza);
        Button aggiungi = view.findViewById(R.id.dialogToDoButtonAdd);
        prioritaGroup = view.findViewById(R.id.dialogToDoPrioritaGroup);
        chiudi.setOnClickListener(this);
        aggiungi.setOnClickListener(this);
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
                Attivita attivita = new Attivita(nomeAttivita.getText().toString(), valorePriorita ,descrizioneAttivita.getText().toString(),scadenzaAttivita.getText().toString());
                HomeActivity.attivitaTutte.add(attivita);
                FragmentTodo.adapterToDo.notifyDataSetChanged();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,Object> attivitaAggiunta = new HashMap<>();
                attivitaAggiunta.put(attivita.getNome(), attivita);
                db.collection("ToDo").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(attivitaAggiunta);

                //aggiungi sul db
                dismiss();
                break;
        }
    }
}
