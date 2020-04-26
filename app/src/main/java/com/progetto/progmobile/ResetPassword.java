package com.progetto.progmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private TextInputEditText inputEmail;
    private Button btnReset, btnLogin;
    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //per togliere la status bar
        setContentView(R.layout.activity_reset_password);

        inputEmail = findViewById(R.id.email_reset);
        btnReset = findViewById(R.id.btn_reset_password);
        btnLogin = findViewById(R.id.btn_to_login);
        mAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your mail address", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this, "We send you an e-mail", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(ResetPassword.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void NavigateLogin(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
