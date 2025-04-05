package com.example.march13;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Configuration configuration=getResources().getConfiguration();
        FragmentManager fm=getSupportFragmentManager();

        FragmentTransaction ft=fm.beginTransaction();


        if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            firstFragment firstFragment=new firstFragment();


            ft.replace(android.R.id.content,firstFragment);
        }
        else {

            secondFragment secondFragment=new secondFragment();

            ft.replace(android.R.id.content,secondFragment);
        }
    }
}