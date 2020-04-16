package com.progetto.progmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;

    //Variabili
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;
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

        new Handler().postDelayed(new Runnable() {    //handler per far partire la dashboard con un intent dopo i 5 secondi
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class );
                startActivity(intent);
                finish(); //altrimenti se premo indietro ritorno qui: ma dalla dashboars. Con finish rimuovo l'activity dallo stack
            }
        }, SPLASH_SCREEN);


    }
}
