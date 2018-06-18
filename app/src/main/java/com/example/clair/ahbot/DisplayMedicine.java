package com.example.clair.ahbot;

import android.media.RemoteController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayMedicine extends AppCompatActivity {

    FirestoreHelper db;
    TextView result;
    List<Medicine> medicine;
    String list = "";
    public String medName = "start, ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine);
        db = new FirestoreHelper();
        medicine =new ArrayList<>();

        for (Medicine medicine: medicine) {
            medName = medicine.getMedName();
            }
        result.setText(medName);

    }

}
