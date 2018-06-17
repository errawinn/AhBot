package com.example.clair.ahbot;

import android.media.MediaExtractor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by user on 21/3/2018.
 */

public class FirestoreHelper {
    List<Medicine> medicineList;
    static CollectionReference medicineCollection = FirebaseFirestore.getInstance().collection("users").document("MedicineList").collection("Medicine");
    CollectionReference scheduleCollection = FirebaseFirestore.getInstance().collection("Schedule");
    List<ScheduleTask> Task=new ArrayList<>();
    Map<String, Object> Tasks = new HashMap<>();

    public FirestoreHelper() {}
    //final MainActivity reference = r

    public void storeMedicine() {
        FirebaseFirestore.getInstance().collection("users").document("MedicineList").collection("Medicine")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               medicineList = new ArrayList<>();

                                               if (task.isSuccessful()) {
                                                   // List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                                                   //} else {
                                                   for (DocumentSnapshot document : task.getResult()) {
                                                       if (document != null) {
                                                           String id = (String) document.getId();
                                                           String medName = (String) document.get("MedicineName");
                                                           String amt = (String) document.get("Amount");
                                                           String freq = (String) document.get("Frequency");
                                                           String remarks = (String) document.get("Remarks");

                                                           Log.d(TAG, "MedicineName: " + medName);

                                                           Medicine medicine = new Medicine(id, medName, amt, freq, remarks);
                                                           medicineList.add(medicine);

                                                           setMedicineList(medicineList);
                                                           Log.d(TAG, "Medicine added");

                                                       } else {
                                                           Log.d(TAG, "No such document");
                                                       }

                                                       //reference.UpdateList(medicineList);
                                                       // reference.updateTasks();

                                                   }
                                               }
                                           }


                                       }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
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

    public static void updateData(Medicine med) {
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
    public void saveAllData(List<Medicine> medList) {
        for (Medicine medicine : medList) {
            saveData(medicine);
        }
    }

    public List<Medicine> getMedicineList() {
        storeMedicine();
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }



public void saveTask(Schedule s){
    final Schedule reference = s;

        scheduleCollection
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

            List<ScheduleTask> allTasks = new ArrayList<>();

            for (DocumentSnapshot document : value)
            {
                String userID = document.getString("userID");
                String taskName = document.getString("taskName");
                String dueDate = document.getString("Date");
                String time = document.getString("Time");

                ScheduleTask scheduleTask = new ScheduleTask(userID,taskName, dueDate, time);
                allTasks.add(scheduleTask);
            }

            reference.getTaskList(allTasks);
        }
    });
}
public List<ScheduleTask> getTasks(){
        scheduleCollection.get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String userID = document.getString("userID");
                                String taskName = document.getString("taskName");
                                String dueDate = document.getString("Date");
                                String time = document.getString("Time");

                                ScheduleTask t = new ScheduleTask(userID,taskName, dueDate, time);
                                Task.add(t);
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            return;
                        }
                    }
                });
    return Task;
    }

    public void addTask(ScheduleTask st) {
        Tasks.put("userID", st.getUserID());
        Tasks.put("taskName", st.getTaskName());
        Tasks.put("Date", st.getTaskDueDate());
        Tasks.put("Time",st.getTaskDueTime());


        scheduleCollection.document().set(Tasks).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + scheduleCollection.document().getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NotNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


    }
}
