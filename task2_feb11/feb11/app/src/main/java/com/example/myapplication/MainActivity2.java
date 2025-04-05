package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    EditText university,grade;

    final  static int  REQUEST_CODE=1;
    Button send;
    TextView display1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        display1=findViewById(R.id.textView);
        university=findViewById(R.id.university);
        grade=findViewById(R.id.grade);
        send=findViewById(R.id.send_sc2);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String roll_num=intent.getStringExtra("Roll number");
        display1.setText("Name: "+name+" Roll number: "+roll_num);


        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name1=university.getText().toString();
                        String grade1=grade.getText().toString();

                        Intent returnintent=new Intent();
                        returnintent.putExtra("university",name1);
                        returnintent.putExtra("grade",grade1);
                        setResult(MainActivity.RESULT_OK,returnintent);
                        finish();



                    }
                }
        );




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}