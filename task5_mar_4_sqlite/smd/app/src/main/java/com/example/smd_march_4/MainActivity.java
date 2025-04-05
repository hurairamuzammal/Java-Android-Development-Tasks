package com.example.smd_march_4;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    DBClass dbClass;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBClass  dbClass=new DBClass(this);
        dbClass.addinfo("kanwal","12345");
        dbClass.addinfo("ahmad","12345");
        dbClass.addinfo("akram","12345");
        dbClass.addinfo("saleem","12345");
        dbClass.addinfo("muraad","12345");


        //cursor fetch data

        cursor = dbClass.getAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                Log.d("DB_FETCH", "ID: " + id + ", Name: " + name + ", Phone: " + phone);
            }
        }

        boolean isUpdated = dbClass.updateData(4, "New Name", "1234567890");

        if (isUpdated) {
            Log.d("DB_UPDATE", "Data updated successfully!");
        } else {
            Log.d("DB_UPDATE", "Failed to update data.");
        }


        boolean isDeleted = dbClass.deleteData(1);

        if (isDeleted) {
            Log.d("DB_DELETE", "Data deleted successfully!");
        } else {
            Log.d("DB_DELETE", "Failed to delete data.");
        }

        cursor.close();
        dbClass.close();


        // home task display all the value after update and drop table

        dbClass.viewAllData();
        dbClass.deleteAllData();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}