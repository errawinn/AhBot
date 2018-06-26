package com.example.clair.ahbot;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MedicalProfileFirestoreHelper {

    List<MedicalProfile> profileList;
    //region firebase database

    //endregion

    static CollectionReference profileCollection = FirebaseFirestore.getInstance().collection("Profile").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).collection("profileList");


    public MedicalProfileFirestoreHelper(MainActivity r) {

        final MainActivity reference = r;
        profileCollection
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e)
                    {
                        if (e != null)
                        {
                            Log.w("FirestoreHelper", "Listen failed.", e);
                            return;
                        }
                        List<MedicalProfile> allprofileList = new ArrayList<>();
                        //medicineList.clear();
                        for (DocumentSnapshot document : value)
                        {
                            String id = document.getId();
                            String name = (String) document.get("name");
                            String age = (String) document.get("age");
                            List<String> allergies = (List<String>) document.get("allergies");
                            List<String> diseases = (List<String>) document.get("diseases");


                            MedicalProfile medicalProfile = new MedicalProfile(id,name,age,allergies,diseases);
                            allprofileList.add(medicalProfile);
                        }
                        //reference.getMedicine(allmedicineList);
                    }
                });

    }

    public  MedicalProfileFirestoreHelper(){}



    public static void deleteData(MedicalProfile p) {
        MedicalProfile profile = p;
        String id = profile.getId();
        profileCollection.document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }


}
