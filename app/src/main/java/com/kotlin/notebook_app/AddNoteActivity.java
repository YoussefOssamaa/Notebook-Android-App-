package com.kotlin.notebook_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNoteActivity extends AppCompatActivity {
    EditText title_et , content_et ;
    ImageButton done_btn ;
    TextView pageTitle_TV ;
    String title , content , docID ;
    boolean editMode = false;
    TextView delete_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title_et = findViewById(R.id.title_et_xml) ;
        content_et = findViewById(R.id.content_et_xml ) ;
        done_btn = findViewById(R.id.done_btn_xml) ;
        pageTitle_TV = findViewById(R.id.Add_note_et_xml) ;
        delete_btn = findViewById(R.id.delete_textview_xml);


        title = getIntent().getStringExtra("title") ;
        content = getIntent().getStringExtra("content") ;
        docID = getIntent().getStringExtra("docID") ;

        //check if we are on edit mode or we are creating new note {if docID id null so we are creating new note}
        if (docID!= null && !docID.isEmpty()){
            editMode = true ;
        }

        title_et.setText(title);
        content_et.setText(content);

        if (editMode) {
            pageTitle_TV.setText("Edit Note");
            delete_btn.setVisibility(View.VISIBLE);
        }

        delete_btn.setOnClickListener(v-> deleteNoteFromFirebase());
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
//finish();
        }

    }

    void saveNoteinFirebase (Note note) {

        //setting the document reference
        DocumentReference documentReference ;

        if (editMode){
            //if we are editing the note we will edit an existing note not creating a new one
            documentReference = Utility.getCollectionReferenceForNotes().document(docID);

        }else {
            //creating new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(AddNoteActivity.this, "Note Added", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(AddNoteActivity.this, "Failure while adding the note", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

     }

     void deleteNoteFromFirebase (){

             //deleting the document reference
             DocumentReference documentReference ;
             documentReference = Utility.getCollectionReferenceForNotes().document(docID);
             documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {

                 @Override
                 public void onComplete(@NonNull Task<Void> task) {

                     if (task.isSuccessful()){

                         Toast.makeText(AddNoteActivity.this, "Note Deleted", Toast.LENGTH_LONG).show();
                        finish();
                     }
                     else {
                         Toast.makeText(AddNoteActivity.this, "Failure while deleting the note", Toast.LENGTH_LONG).show();
                         finish();
                     }
                 }
             });
         }
     }


