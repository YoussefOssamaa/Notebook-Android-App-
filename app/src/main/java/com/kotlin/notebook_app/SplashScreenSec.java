package com.kotlin.notebook_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenSec extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_sec);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser() ;

if (currentuser == null) {
    startActivity(new Intent(SplashScreenSec.this , LoginActivity.class ));
}else{
    startActivity(new Intent(SplashScreenSec.this , MainActivity.class ));
}

                finish();
            }
        }, 1000) ;
    }
}