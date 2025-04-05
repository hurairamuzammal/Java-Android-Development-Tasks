package com.example.myapplication;

import android.app.ComponentCaller;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText name,roll_number;

    final  static int  REQUEST_CODE=1;
    Button send;
    TextView display1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        display1=findViewById(R.id.field);
        name=findViewById(R.id.name);
        roll_number=findViewById(R.id.roll_num);
        send=findViewById(R.id.send_sc1);


        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name1=name.getText().toString();
                        String roll_num=roll_number.getText().toString().trim();
                        Intent intent=new Intent(MainActivity.this, MainActivity2.class);
                        intent.putExtra("name",name1);
                        intent.putExtra("Roll number",roll_num);
                        startActivityForResult(intent,REQUEST_CODE);
                    }
                }
        );
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);
        if(requestCode==REQUEST_CODE&& resultCode==MainActivity2.RESULT_OK)
        {

//            Intent intent=getIntent();
            String university=data.getStringExtra("name");
            String grade=data.getStringExtra("Roll number");
            String name1=name.getText().toString();
            String roll_num=roll_number.getText().toString();

            String displayText="Name: "+ name1+" Roll number: "+ roll_num+ "University: "+university+" Grade: "+grade;
            display1.setText(displayText);


        }
    }
}