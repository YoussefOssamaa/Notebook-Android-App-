package com.kotlin.notebook_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNoteActivity extends AppCompatActivity {
    EditText title_et , content_et ;
    ImageButton done_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title_et = findViewById(R.id.title_et_xml) ;
        content_et = findViewById(R.id.content_et_xml ) ;
        done_btn = findViewById(R.id.done_btn_xml) ;

        done_btn.setOnClickListener(v-> check());



    }

    void check() {
        String title = title_et.getText().toString() ;
        String content = content_et.getText().toString() ;

        if (title.isEmpty() && content.isEmpty()){
            startActivity(new Intent(AddNoteActivity.this , MainActivity.class)) ;
        }else{
        Note note = new Note() ;
        note.setTitle(title);
        note.setContent(content);
        note.setTimestamp(Timestamp.now());

        saveNoteinFirebase(note) ;
            //startActivity(new Intent(AddNoteActivity.this , SplashScreenSec.class));
finish();
        }

    }

    void saveNoteinFirebase (Note note) {



        //setting the document reference
        DocumentReference documentReference ;
        documentReference = Utility.getCollectionReferenceForNotes().document() ;


        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(AddNoteActivity.this, "Note created successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddNoteActivity.this , MainActivity.class));
                    //finish();
                }
                else {
                    Toast.makeText(AddNoteActivity.this, "Failure while adding the note", Toast.LENGTH_LONG).show();

                    //finish();
                }
            }
        });
        //startActivity(new Intent(AddNoteActivity.this , SplashScreenSec.class));

     }
}