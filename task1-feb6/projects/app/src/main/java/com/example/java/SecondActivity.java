package com.example.java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    Button backBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this,"Create method of Second Screen Called.",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        backBtn=findViewById(R.id.back);
        textView=findViewById(R.id.textView3);
        backBtn.setOnClickListener(v -> {
            Intent intent=new Intent(SecondActivity.this,MainActivity.class);
            startActivity(intent);
                });

//        get put extra and display in text view
Intent previousIntent=getIntent();
String data=previousIntent.getStringExtra("data");
textView.setText(data);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.back), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Destroy method of Second Screen Called.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {

        super.onStart();
        Toast.makeText(this,"Start method of Second Screen Called.",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Resume method of Second Screen Called.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Pause method of Second Screen Called.",Toast.LENGTH_SHORT).show();
    }
}