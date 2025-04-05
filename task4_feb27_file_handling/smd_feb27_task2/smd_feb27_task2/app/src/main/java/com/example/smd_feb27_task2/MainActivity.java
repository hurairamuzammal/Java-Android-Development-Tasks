package com.example.smd_feb27_task2;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView2);
        String data="This is Simple file in internal Storage.";
        button.setOnClickListener(v -> {
            // Read file from assets and display content in TextView
            String fileContent = FileReader.readFileFromAssets(MainActivity.this, "inputFile.txt");
            textView.setText(fileContent);
        });


        try {
            FileOutputStream fos=openFileOutput("myfile.text", Context.MODE_PRIVATE);

            fos.write(data.getBytes());

            fos.close();

            Toast.makeText(this,"File write complete",Toast.LENGTH_SHORT).show();

        }catch (FileNotFoundException e)
        {
            throw  new RuntimeException(e);
        } catch (IOException e) {
            Toast.makeText(this,"Error writing file",Toast.LENGTH_SHORT).show();
        }

        String filePath=getFilesDir().getPath()+"/myfile.txt";

        MediaScannerConnection.scanFile(this,new String[]{filePath},null,((path, uri) -> Toast.makeText(this,"File Scanned",Toast.LENGTH_SHORT).show()));
    }
}