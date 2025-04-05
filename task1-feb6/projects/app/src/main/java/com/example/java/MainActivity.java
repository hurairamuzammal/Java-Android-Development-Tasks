package com.example.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    EditText view1;

    Button moveBtn;

    private static final int  REQUEST_CODE=22;
    ImageView imagev1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn1=findViewById(R.id.cameraBtn);
        imagev1=findViewById(R.id.imageView);
        Toast.makeText(this,"Create method of First Screen Called.",Toast.LENGTH_SHORT).show();
        moveBtn=findViewById(R.id.clickBtn);
        view1=findViewById(R.id.editText);
        btn2=findViewById(R.id.sendBtn);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,REQUEST_CODE);
            }
        });

        moveBtn.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);

        });

        btn2.setOnClickListener(v -> {
            Intent newItent=new Intent(this,SecondActivity.class);
            newItent.putExtra("data",view1.getText().toString());
            startActivity(newItent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.back), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        Toast.makeText(this,"Start method of First Screen Called.",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Resume method of First Screen Called.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Pause method of First Screen Called.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode==REQUEST_CODE&& resultCode==RESULT_OK)
       {
           Bitmap photo=(Bitmap) data.getExtras().get("data");
           imagev1.setImageBitmap(photo);
       }
       super.onActivityResult(requestCode,resultCode,data);
    }
}