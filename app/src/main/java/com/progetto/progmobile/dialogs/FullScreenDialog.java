package com.progetto.progmobile.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.progetto.progmobile.R;

import java.util.Calendar;

import static com.progetto.progmobile.R.id.OraIButton;

public class FullScreenDialog extends DialogFragment {
    public static FullScreenDialog newInstance() {
        return new FullScreenDialog();
    }

    //bottoni che aprono i time-picker
    private Button btnOraI, btnOraF, btnGiorno;

    private String stringOraI, stringOraF;
    private int oraI, oraF, minutoI, minutoF;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_orario, container, false);


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
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                stringOraF = hourOfDay + ":" + minute;
                                btnOraF.setText(stringOraF);
                            }
                        }, oraF, minutoF, false);

                timePickerDialog.show();
            }
        });

        return view;
    }


}