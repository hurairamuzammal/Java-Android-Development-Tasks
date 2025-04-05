package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//creating a hashmap
        HashMap<String,String>dataMap=new HashMap<>();
                dataMap.put("Name","M Abu Huraira");
                dataMap.put("Email","huraira@domain.com");
                dataMap.put("Phone","03322220003");
        List<DataItem> DataItems =new ArrayList<>();
        for (Map.Entry<String,String>entry:dataMap.entrySet())
        {
            DataItems.add(new DataItem(entry.getKey(),entry.getValue()));

        }

//        inialize
        customAdapter adapter = new customAdapter(this, DataItems);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener((parent,view,position,id)->{
            DataItem selectedItem=adapter.getItem(position);
            showToast("Selected: "+selectedItem.getKey());

        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }
}