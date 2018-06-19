package com.example.clair.ahbot;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirestoreSetting {
    //disable firestore data persistence
    static FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build();

    public FirestoreSetting(){
            fs.setFirestoreSettings(settings);

    }
}
