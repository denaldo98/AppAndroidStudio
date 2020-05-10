package com.progetto.progmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.progetto.progmobile.R;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;

    //Variabili
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //per togliere la status bar
        setContentView(R.layout.activity_main);

        //collego le animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        //assegno le animations alle viste
        image.setAnimation(topAnim); //l'immagine deve scendere dall'alto
        logo.setAnimation(bottomAnim); //le due text view entrano dal basso
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {    //handler per far partire la HomeActivity/SignUp Activity dopo 5 secondi
            @Override
            public void run() {
                Intent intent = null;
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                if(preferences.getBoolean("autoLogin", true)) {
                    intent = new Intent(MainActivity.this, SignUp.class);
                } else {
                    intent = new Intent(MainActivity.this, HomeActivity.class);
                }
                startActivity(intent);
                finish(); //distruggo l'activity per rimuoverla dallo stack
            }
        }, SPLASH_SCREEN);

    }
}
