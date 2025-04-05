package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText1=findViewById(R.id.name);
        editText2=findViewById(R.id.age);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences=getSharedPreferences("UserPreferences",MODE_PRIVATE);
        String savedName=sharedPreferences.getString("user_name","");
        int savedAge=sharedPreferences.getInt("user_age",0);

        //populating the edittext fields with stored data

        editText1.setText(savedName);
        editText2.setText(savedAge >0? String.valueOf(savedAge):"");

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences=getSharedPreferences("UserPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("user_name",editText1.getText().toString());
        String ageInput=editText2.getText().toString();
        int userAge=ageInput.isEmpty()?0:Integer.parseInt(ageInput);
        editor.putInt("user_age",userAge);
        editor.apply();

    }
}