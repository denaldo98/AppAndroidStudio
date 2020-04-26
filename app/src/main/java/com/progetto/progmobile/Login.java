package com.progetto.progmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

        private Button btn_register, btn_login, btn_forget;
        private TextInputEditText email, password;
        private ProgressBar progressBar;

        private FirebaseAuth mAuth;

         @Override
        protected void onCreate(Bundle savedInstanceState)
         {
             super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //Per togliere la status bar
            setContentView(R.layout.activity_login);

            // taking instance of FirebaseAuth
            mAuth = FirebaseAuth.getInstance();

            // initialising all views through id defined above
            btn_register = findViewById(R.id.btn_signup);
            btn_login = findViewById(R.id.btn_login);
            btn_forget = findViewById(R.id.btn_forget);
            email = findViewById(R.id.login_email);
            password = findViewById(R.id.login_password);
            progressBar = findViewById(R.id.progressBar2);

            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, SignUp.class);
                    startActivity(intent);
                    finish();
                }
            });

            btn_forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, ResetPassword.class);
                    startActivity(intent);
                }
            });

            // Set on Click Listener on Sign-in button
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUserAccount();
                }
            });
         }


         private void loginUserAccount() {
             // show the visibility of progress bar to show loading
             progressBar.setVisibility(View.VISIBLE);

             // Take the value of two edit texts in Strings
             String userEmail = email.getText().toString();
             String userPassword = password.getText().toString();

             if (TextUtils.isEmpty(userEmail) && (TextUtils.isEmpty(userPassword))) {
                 Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_LONG).show();
                 progressBar.setVisibility(View.GONE);  // hide the progress bar
             } else if (TextUtils.isEmpty(userEmail)) {
                 Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
                 progressBar.setVisibility(View.GONE);  // hide the progress bar
             } else if (TextUtils.isEmpty(userPassword)) {
                 Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
                 progressBar.setVisibility(View.GONE);  // hide the progress bar
             } else {
                 // signin existing user
                 mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                             progressBar.setVisibility(View.GONE);  // hide the progress bar

                             // if sign-in is successful intent to HomeActivity
                             Intent intent = new Intent(Login.this, HomeActivity.class);
                             startActivity(intent);
                             finish();
                         } else {
                             // sign-in failed
                             Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                             // hide the progress bar
                             progressBar.setVisibility(View.GONE);
                         }
                     }
                 });
             }
         }






   /*@Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }*/



}



