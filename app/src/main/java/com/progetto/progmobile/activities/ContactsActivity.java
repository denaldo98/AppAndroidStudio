package com.progetto.progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.progetto.progmobile.R;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnBack;
    private TextView emailMarco, emailAntonio, emailDena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        btnBack = findViewById(R.id.btn_to_home);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        emailMarco = findViewById(R.id.mail_marco);
        emailAntonio = findViewById(R.id.mail_antonio);
        emailDena = findViewById(R.id.mail_denaldo);
        emailMarco.setOnClickListener(this);
        emailAntonio.setOnClickListener(this);
        emailDena.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Richiesta Informazioni");

        switch (v.getId()){
            case R.id.mail_denaldo:

                try {
                    intent.setData(Uri.parse("mailto:denaldo98@gmail.com"));
                    startActivity(Intent.createChooser(intent, "Send Email"));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.mail_antonio:

                break;

            case R.id.mail_marco:

                break;

        }
    }
}
