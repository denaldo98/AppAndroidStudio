package com.progetto.progmobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tolgo la ActionBar
        //getSupportActionBar().hide();
        //per togliere la status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        //Save data in Firebase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//Register Button method end
    }//onCreate Method End
}
