package com.example.clair.ahbot;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by user on 21/3/2018.
 */

public class FirestoreHelper {
    List<Medicine> medicineList;
    static CollectionReference medicineCollection = FirebaseFirestore.getInstance().collection("Medicine");

    public FirestoreHelper()
    {
        //final MainActivity reference = r
        medicineCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        medicineList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if(document.exists()) {
                                    String id = document.getId();
                                    String medName = document.getString("MedicineName");
                                    String amt = document.getString("Amount");
                                    String freq = document.getString("Frequency");
                                    String remarks = document.getString("Remarks");

                                    if (medName != null && remarks != null && id != null) {
                                        Medicine medicine = new Medicine(id, medName, amt, freq, remarks);
                                        medicineList.add(medicine);

                                    }
                                }else{
                                    Log.d(TAG, "No such document");
                                }

                                //reference.UpdateList(medicineList);
                               // reference.updateTasks();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

    }


    public static void deleteData(Medicine med) {
        Medicine medicine = med;
        String id = medicine.getId();
        medicineCollection.document(id)
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


    //one method to add one 'row' of data
  public static void saveData(Medicine medicine) {
      Map<String, String> data = new HashMap<String, String>();
      data.put("MedicineName", medicine.getMedName());
      data.put("Amount", medicine.getMedAmount());
      data.put("Frequency", medicine.getMedFrequency());
      data.put("Remarks", medicine.getRemarks());
      medicineCollection.document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
              Log.d("FirestoreHelper", "Document has been saved!");
          }
          })
                  .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Log.w(TAG, "Error adding document", e);
              }
      });

  }

  public static void updateData (Medicine med) {
      medicineCollection.document(med.getId())
              .update("MedicineName", med.getMedName(),
                      "Amount", med.getMedAmount(),
                      "Frequency", med.getMedFrequency(),
                      "Remarks", med.getRemarks())
              .addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      Log.d(TAG, "DocumentSnapshot successfully updated!");
                  }
              })
              .addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Log.w(TAG, "Error updating document", e);
                  }
              });
  }

    //method to add all data
  public void saveAllData (List<Medicine> medList) {
        for ( Medicine medicine: medList) {
            saveData(medicine);
        }
  }

  public List<Medicine> getMedicineList(){
        return medicineList;
  }


}
