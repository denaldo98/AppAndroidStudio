package com.progetto.progmobile.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.progmobile.R;
import com.progetto.progmobile.entities.Corso;

public class DialogCorsoVisualizza extends DialogFragment implements View.OnClickListener {

    private TextView nomeCorso, nomeProfessore, emailProfessore, numeroCFU;
    private RecyclerView appunti;
    private String path;
    private Corso corso;

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
        ImageButton chiudi = view.findViewById(R.id.dialogCorsoButtonModify);
        nomeCorso = view.findViewById(R.id.nomeCorso);
        nomeProfessore = view.findViewById(R.id.nomeProfessore);
        emailProfessore = view.findViewById(R.id.emailProfessore);
        numeroCFU = view.findViewById(R.id.numeroCFU);
        Button modifica = view.findViewById(R.id.dialogCorsoButtonModify);
        nomeCorso.setText(corso.getNome());
        nomeProfessore.setText(corso.getNomeProfessore());
        numeroCFU.setText(String.format("%d", corso.getNumeroCFU()));
        emailProfessore.setText(corso.getEmailProfessore());

        chiudi.setOnClickListener(this);
        modifica.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) { //DA MODIFICARE QUI!
        int id = v.getId();
        switch (id) {
            case R.id.dialogCorsoChiudi:
                dismiss();
                break;
            case R.id.dialogCorsoButtonModify:
                dismiss();
                break;
            default:
                break;
        }
    }

}
