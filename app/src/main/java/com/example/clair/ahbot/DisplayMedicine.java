package com.example.clair.ahbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMedicine extends AppCompatActivity {

    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine);

        result = findViewById(R.id.results);
        String results = getIntent().getStringExtra("barcode");
        result.setText(results);
    }
}
