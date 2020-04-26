package com.progetto.progmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private TextInputEditText regName, regSurname, regEmail, regPassword, regConfirmPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //per togliere la status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        regName = findViewById(R.id.reg_name);
        regSurname = findViewById(R.id.reg_cognome);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regConfirmPassword = findViewById(R.id.reg_confpassword);

        Button regBtn = findViewById(R.id.reg_btn);
        Button regToLoginBtn = findViewById(R.id.reg_login_btn);

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        //Registrazione Firebase con email e password
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(regPassword.getText().toString().equals(regConfirmPassword.getText().toString()))) {
                    Toast.makeText(SignUp.this, "Le password non coincidono.", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        final String nome = regName.getText().toString();
                        final String cognome = regSurname.getText().toString();
                        String email = regEmail.getText().toString();
                        String password = regPassword.getText().toString();

                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nome + " " + cognome).build();
                                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            writeUserToDb(nome, cognome, user.getUid());
                                            Intent intent = new Intent(SignUp.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    });
                                } else {
                                    task.getException().printStackTrace();
                                    Toast.makeText(SignUp.this, "C'è stato un errore nella registrazione.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(SignUp.this, "Per favore, inserisci tutte le informazioni richieste.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });//Register Button method end


    }//onCreate Method End


    private void writeUserToDb(String nome, String cognome, String uid) {
        Map<String, Object> user = new HashMap<>();
        user.put("nome", nome);
        user.put("cognome", cognome);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("utenti").document(uid).set(user);
    }
}
