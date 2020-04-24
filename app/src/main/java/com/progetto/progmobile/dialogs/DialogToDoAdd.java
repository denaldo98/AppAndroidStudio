package com.progetto.progmobile.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.progetto.progmobile.R;

import java.util.Objects;

public class DialogToDoAdd extends AppCompatDialogFragment {
    private EditText nomeAttivita, descrizioneAttivita, scadenzaAttivita, prioritaAttivita;
    private DialogToDoListener listener;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_to_do, null);
        builder.setView(view).setTitle("Aggiungi attività").setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
            }}).setPositiveButton("Aggiungi", new DialogInterface.OnClickListener(){
             @Override
             public void onClick(DialogInterface dialogInterface, int i){
                 String nome = nomeAttivita.getText().toString();
                 String scadenza = scadenzaAttivita.getText().toString();
                 String descrizione = descrizioneAttivita.getText().toString();
                 String priorita = prioritaAttivita.getText().toString();
                 listener.applyTexts(nome,scadenza,descrizione,priorita);

             }
         });
        nomeAttivita = view.findViewById(R.id.dialogToDoNome);
        descrizioneAttivita = view.findViewById(R.id.dialogToDoDescrizione);
        prioritaAttivita = view.findViewById(R.id.dialogToDoPriorita);
        scadenzaAttivita = view.findViewById(R.id.dialogToDoScadenza);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogToDoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogToDoListener");
        }
    }

    public interface DialogToDoListener {
        void applyTexts(String nome, String scadenza, String descrizione, String priorita);
    }
}
