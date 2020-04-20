package com.progetto.progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

        Button btn_register, btn_login;
        ImageView image;
        TextView logoText, sloganText;
        TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Tolgo la ActionBar
       // getSupportActionBar().hide();
        //Per togliere la status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        btn_register = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(image, "logo_image");
                pairs[1] = new Pair<View,String>(logoText, "logo_text");
                pairs[2] = new Pair<View,String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View,String>(username, "username_tran");
                pairs[4] = new Pair<View,String>(password, "password_tran");
                pairs[5] = new Pair<View,String>(btn_login, "button_tran");
                pairs[6] = new Pair<View,String>(btn_register, "login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());

            }
        });


    }
}
