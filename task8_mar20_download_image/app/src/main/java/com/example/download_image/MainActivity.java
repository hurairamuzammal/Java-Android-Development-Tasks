package com.example.download_image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    Button btn;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView=findViewById(R.id.imageDown);
        btn=findViewById(R.id.btnDown);
        btn2=findViewById(R.id.webBtn);

        btn.setOnClickListener(v -> {


            // Create and execute the AsyncTask
          imageDownload download =new imageDownload();
            Bitmap bitmap = null;
            try {
            // Execute AsyncTask and get the result (image)
                bitmap = download.execute("https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg").get();
                imageView.setImageBitmap(bitmap); // Set the downloaded image in the ImageView
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        btn2.setOnClickListener(v -> {
            Intent newIntent=new Intent(MainActivity.this, WebContentActivity.class);
            startActivity(newIntent);

        });
    }
}