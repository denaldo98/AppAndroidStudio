package com.progetto.progmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.progetto.progmobile.R;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private TextInputEditText regName, regSurname, regEmail, regPassword, regConfirmPassword;
    private Button regBtn, regToLoginBtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //per togliere la status bar
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance(); // taking FirebaseAuth instance

        // initialising all views through id defined above
        regName = findViewById(R.id.reg_name);
        regSurname = findViewById(R.id.reg_cognome);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regConfirmPassword = findViewById(R.id.reg_confpassword);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
        progressBar = findViewById(R.id.progressbar);

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Set on Click Listener on Registration button
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
                }
        });
    }


    private void registerNewUser()
    {

        // Take the value of edit texts in Strings
        final String nome = regName.getText().toString();
        final String cognome = regSurname.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();

        // Validations for input name, surname, email and password
        if(nome.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Per favore, inserire il nome!", Toast.LENGTH_LONG).show();
            return;
        }
        if(cognome.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Per favore, inserire il cognome!", Toast.LENGTH_LONG).show();
            return;
        }
        if(email.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Per favore, inserire la mail!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Per favore, inserire la password!", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "La password deve essere composta almeno da 6 caratteri!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!(regPassword.getText().toString().equals(regConfirmPassword.getText().toString()))) {
            Toast.makeText(SignUp.this, "Le password non coincidono.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE); // show the visibility of progress bar to show loading

        // create new user
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
                                            writeUserToDb(nome, cognome, user.getUid());   //Scriviamo le informazioni dell'utente (nome cognome) nel db firestore per identificare quell'utente: aggiungiamo un documento per il nostro utente

                                            Toast.makeText(getApplicationContext(),"Registrazione avvenuta con successo!", Toast.LENGTH_LONG).show();

                                             progressBar.setVisibility(View.GONE); // hide the progress bar
                                            Intent intent = new Intent(SignUp.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                } else {
                    //Registration failed
                    Toast.makeText(getApplicationContext(), "Registrazione fallita!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE); // hide the progress bar
                }
            }
        });
    }


    private void writeUserToDb(String nome, String cognome, String uid) {
        Map<String, Object> user = new HashMap<>();
        user.put("nome", nome);
        user.put("cognome", cognome);

        FirebaseFirestore db = FirebaseFirestore.getInstance();   //otteniamo un istanza del nostro db
        db.collection("utenti").document(uid).set(user);   //il document è l'utente che è identificato da un uid da firebaseAuth; settiamo il documento
    }
}
