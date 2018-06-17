package com.example.clair.ahbot;

import android.media.RemoteController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayMedicine extends AppCompatActivity {

    FirestoreHelper db = new FirestoreHelper();
    TextView result;
    List<Medicine> medList = db.getMedicineList();
    String list = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine);

        result = findViewById(R.id.results);
        String results = getIntent().getStringExtra("barcode");

        for (Medicine medicine: medList) {
            result.setText(medicine.getMedName());
        }

    }
}
