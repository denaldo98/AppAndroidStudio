package com.progetto.progmobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //per togliere la status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        regName = findViewById(R.id.);
        regUsername = findViewById(R.id.);
        regEmail = findViewById(R.id.);
        regPhoneNo = findViewById(R.id.);
        regPassword = findViewById(R.id.);
        regBtn = findViewById(R.id.);
        regToLoginBtn = findViewById(R.id.);
    }
}
