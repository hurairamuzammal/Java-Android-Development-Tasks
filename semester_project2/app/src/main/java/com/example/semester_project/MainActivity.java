package com.example.semester_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonModel1, buttonModel2, buttonModel3, buttonModel4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Uses your XML layout

        // Link buttons
        buttonModel1 = findViewById(R.id.buttonModel1);
        buttonModel2 = findViewById(R.id.buttonModel2);
        buttonModel3 = findViewById(R.id.buttonModel3);
        buttonModel4 = findViewById(R.id.buttonModel4);

        // Open TextRecognitionActivity when Model 1 button is clicked
        buttonModel1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, text_recognition.class);
            startActivity(intent);
        });

        // Other buttons (future models)
        buttonModel2.setOnClickListener(v -> {
            // TODO: Start another activity
        });

        buttonModel3.setOnClickListener(v -> {
            // TODO: Start another activity
        });

        buttonModel4.setOnClickListener(v -> {
            // TODO: Start another activity
        });
    }
}
