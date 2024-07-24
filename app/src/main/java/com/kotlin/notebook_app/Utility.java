package com.kotlin.notebook_app;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {
    static CollectionReference getCollectionReferenceForNotes(){

        //Getting the user id to save his note
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser() ;

        //Creating a collection specific for the user then a document in the collection
       return FirebaseFirestore.getInstance().collection("notes")
               .document(currentuser.getUid()).collection("my_notes") ;


    }

    static String timestampToString(Timestamp timestamp) {
       return new SimpleDateFormat("MM/dd/YYYY").format(timestamp.toDate()) ;
    }

}
