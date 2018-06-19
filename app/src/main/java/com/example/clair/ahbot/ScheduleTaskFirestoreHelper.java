package com.example.clair.ahbot;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ScheduleTaskFirestoreHelper {
    CollectionReference scheduleCollection = FirebaseFirestore.getInstance().collection("Schedule");
    List<ScheduleTask> Task=new ArrayList<>();
    Map<String, Object> Tasks = new HashMap<>();

    public ScheduleTaskFirestoreHelper(){}

    public ScheduleTaskFirestoreHelper(Schedule s){

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

                        reference.setTaskList(allTasks);
                    }
                });
    }

    public void getTasks(Schedule s){
        final Schedule ref=s;
        scheduleCollection.get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String userID = document.getString("userID");
                                String taskName = document.getString("taskName");
                                String dueDate = document.getString("Date");
                                String time = document.getString("Time");

                                ScheduleTask t = new ScheduleTask(userID,taskName, dueDate, time);
                                Task.add(t);

                            }
                            ref.setTaskList(Task);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            return;
                        }
                    }
                });
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
