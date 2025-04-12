package com.example.foregroundservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button startBtn,stopBtn;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
      startBtn=findViewById(R.id.btn1);
      stopBtn=findViewById(R.id.button3);

      startBtn.setOnClickListener(view->startService(new Intent(this,DownloadService.class)));
      stopBtn.setOnClickListener(view->stopService(new Intent(this, DownloadService.class)));
    }
}