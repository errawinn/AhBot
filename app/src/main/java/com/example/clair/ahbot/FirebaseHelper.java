/*package com.example.clair.ahbot;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FirebaseHelper {
    FirebaseDatabase firebaseDatabase;
    static DatabaseReference databaseReference;
    String value;
    Medicine medicine;
    List<Medicine> medicineList = new ArrayList<>();

    public FirebaseHelper(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("medicine");
        final String TAG = "Firebase";

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                String id = snapshot.child("id").getValue(String.class);
                String name = snapshot.child("medName").getValue(String.class);
                String amt = snapshot.child("medAmount").getValue(String.class);
                String freq = snapshot.child("medFrequency").getValue(String.class);
                String remarks = snapshot.child("remarks").getValue(String.class);

                medicine = new Medicine(id,name,amt,freq,remarks);
                medicineList.add(medicine);
                Log.d(TAG, "Value is: " + value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    public static void saveData(Medicine med){
        final Medicine medicine = med;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference.push().setValue(medicine);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public List<Medicine> getMed(){
        return medicineList;
    }
}
*/