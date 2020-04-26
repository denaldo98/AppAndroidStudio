package com.progetto.progmobile;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
        //variabili
        Button btn_register, btn_login, btn_forget;
        TextInputEditText email, password;
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //Per togliere la status bar
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        btn_register = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);
        btn_forget = findViewById(R.id.btn_forget);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Toast.makeText(Login.this, "User logged in", Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if (userEmail.isEmpty() && userPassword.isEmpty()) {
                    Toast.makeText(Login.this, "Fields empty", Toast.LENGTH_SHORT).show();
                } else if (userPassword.isEmpty()) {
                    password.setError("Enter Password!");
                    password.requestFocus();
                } else if (userEmail.isEmpty()) {
                    email.setError("Provide your Email first!");
                    email.requestFocus();
                } else if (!(userEmail.isEmpty() && userPassword.isEmpty())){
                    mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "Not successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                });
                } else {
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }
}


