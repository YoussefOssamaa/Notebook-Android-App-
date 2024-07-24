package com.kotlin.notebook_app;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Query;

import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    NoteAdapter noteAdapter ;

    FloatingActionButton add_btn ;
    RecyclerView recyclerView ;
    ImageButton menuButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_btn = findViewById(R.id.add_btnxml) ;
        recyclerView = findViewById(R.id.recycler_view_xml) ;
        menuButton = findViewById(R.id.menu_btn_xml) ;

        add_btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this , AddNoteActivity.class)));
        menuButton.setOnClickListener(v-> showmenu());
        RecyclerViewSetup() ;
    }


    void showmenu() {


    }
void RecyclerViewSetup() {
        Query query = Utility.getCollectionReferenceForNotes().orderBy("timestamp" , Query.Direction.DESCENDING) ;
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening(); ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}