package com.kotlin.notebook_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    TextView logintxt;
    EditText emailtxt, passtxt, confirmPasstxt;
    Button createAccBtn;
    ProgressBar progressBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        logintxt = findViewById(R.id.loginxml);
        emailtxt = findViewById(R.id.email_etxml);
        passtxt = findViewById(R.id.pass_etxml);
        confirmPasstxt = findViewById(R.id.confirm_pass_etxml);
        createAccBtn = findViewById(R.id.createBtnxml);
        progressBar = findViewById(R.id.progressBarxml_createAcc);


        createAccBtn.setOnClickListener(v -> createAcc());

        /////////////////////////    From Create Account activity /////    to Login Activity
        logintxt.setOnClickListener(v -> startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class)));


    }

    void createAcc() {

        // takes values from the user then check them

        String email = emailtxt.getText().toString();
        String pass = passtxt.getText().toString();
        String confirmpass = confirmPasstxt.getText().toString();
        check(email, pass, confirmpass);


        //checking if the data collected from the user is correct

        if (!check(email, pass, confirmpass)) {
            return;
        }
        // if the data is correct then we create a new account on Firebase
        createAccountInFirebase(email, pass);
    }

    boolean check(String email_checking, String pass_checking, String confirmpass_checking) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email_checking).matches()) {
            emailtxt.setError("Wrong email format");
            return false;
        }

        if (pass_checking.length() < 6) {
            passtxt.setError("Password must be more than 6 characters");
            return false;
        }

        if (!confirmpass_checking.equals(pass_checking)) {
            confirmPasstxt.setError("Password doesn't match");
            return false;
        }

        return true;
    }


    void createAccountInFirebase(String email, String pass) {
        inProgressfn(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(CreateAccountActivity.this,

                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        inProgressfn(false);
                        //creating account status check

                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Acount created successfully, check your email for verification ", Toast.LENGTH_LONG).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            inProgressfn(false);
                        } else {
                            //in case of failure

                            Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }






    void inProgressfn(boolean inProgress ) {
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            createAccBtn.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            createAccBtn.setVisibility(View.VISIBLE);
        }
    }

}
