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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView createAcctxt;
    EditText emailtxt, passtxt;
    Button loginbtn;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtxt = findViewById(R.id.email_etxml_login);
        passtxt = findViewById(R.id.pass_etxml_login);
        loginbtn = findViewById(R.id.LoginBtnxml);
        createAcctxt = findViewById(R.id.createAcc_et_xml);
        progressBar = findViewById(R.id.progressBarxml_loginAcc);

        loginbtn.setOnClickListener(v -> loginAcc());

                                             /////////////////////////    From login activity /////    to createAcc Activity
        createAcctxt.setOnClickListener(v-> startActivity(new Intent(LoginActivity.this , CreateAccountActivity.class)));



    }

    void loginAcc() {
        String email = emailtxt.getText().toString() ;
        String pass = passtxt.getText().toString() ;
        check(email , pass) ;

        if (!check(email, pass)) {
            return;
        }
        // if the data is correct then we create a new account on Firebase
        login_inFirebase(email, pass);
    }

    boolean check (String email_checking , String pass_checking) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email_checking).matches()) {
            emailtxt.setError("Wrong email format");
            return false;
        }

        if (pass_checking.length() < 6) {
            passtxt.setError("Password must be more than 6 characters");
            return false;
        }

        return true;

    }

    void login_inFirebase(String email , String pass) {
        inProgressfn(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                inProgressfn(false);

                if (task.isSuccessful()) {
                    //login success
                    if(firebaseAuth.getCurrentUser().isEmailVerified()) {

                        startActivity(new Intent(LoginActivity.this , MainActivity.class));
                        finish();


                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Email is not verified, please verify your email", Toast.LENGTH_LONG).show();
                        inProgressfn(false);
                    }
                }
                else {
                    //in case of failure
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    void inProgressfn(boolean inProgress ) {
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.GONE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            loginbtn.setVisibility(View.VISIBLE);
        }
    }


}