package com.example.task_manager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;



import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 4;

    private static final int MANAGE_STORAGE_PERMISSION_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        requestStoragePermission();
        db_class db = new db_class(this, "MYDATABASE", null, 1);
        SQLiteDatabase database = db.getReadableDatabase(); // Forces the database to open

        Log.d("DB_CHECK", "Database opened successfully!");

        Cursor cursor2 = db.getAllTasks();
        while (cursor2.moveToNext()) {
            String taskDate = cursor2.getString(cursor2.getColumnIndexOrThrow("date"));
            String taskTime = cursor2.getString(cursor2.getColumnIndexOrThrow("time"));
            String taskTitle = cursor2.getString(cursor2.getColumnIndexOrThrow("title"));
            String taskDescription = cursor2.getString(cursor2.getColumnIndexOrThrow("description"));

            // Check if task is due now

            if (isTaskDueNow(taskDate, taskTime)) {
                Intent serviceIntent = new Intent(MainActivity.this, Notification.class);
                serviceIntent.putExtra("task_title", taskTitle);
                serviceIntent.putExtra("task_description", taskDescription);
                serviceIntent.putExtra("task_time", taskTime);

                startService(serviceIntent);

            }

        }

        cursor2.close();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set default fragment when app starts

        loadFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {

                    selectedFragment = new HomeFragment();

                } else if (item.getItemId() == R.id.nav_task) {

                    selectedFragment = new TaskListFragment();

                } else if (item.getItemId() == R.id.nav_settings) {

                    selectedFragment = new SettingsFragment();

                }



                if (selectedFragment != null) {

                    loadFragment(selectedFragment);

                }

                return true;

            }


        });

    }

    private boolean isTaskDueNow(String taskDate, String taskTime) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());

            Date taskDateTime = sdf.parse(taskDate + " " + taskTime);

            Calendar now = Calendar.getInstance();

            long currentTime = now.getTimeInMillis();

            long taskTimeInMillis = taskDateTime.getTime();

            // Check if task is due in 30 minutes (1800000 ms)

            long difference = taskTimeInMillis - currentTime;

            return difference >= 0 && difference <= 1800000; // between now and next 30 minutes

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.frameLayout, fragment)

                .commit();

    }



    private void requestStoragePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            if (!android.os.Environment.isExternalStorageManager()) {

                // Show permission request toast
                Toast.makeText(this, "Requesting Manage Storage Permission", Toast.LENGTH_SHORT).show();



                // Open settings to grant permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, MANAGE_STORAGE_PERMISSION_REQUEST_CODE);

            } else {

                Toast.makeText(this, "Manage Storage Permission already granted", Toast.LENGTH_SHORT).show();

            }

        } else { // Android 10 and below

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||

                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                   ActivityCompat.requestPermissions(this, new String[]{

                        Manifest.permission.READ_EXTERNAL_STORAGE,

                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                }, STORAGE_PERMISSION_REQUEST_CODE);


                Toast.makeText(this, "Requesting storage permissions", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "Storage permission already granted", Toast.LENGTH_SHORT).show();

            }

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean readGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean writeGranted = grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (readGranted && writeGranted) {

                    Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();

                }

            }

        }

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == MANAGE_STORAGE_PERMISSION_REQUEST_CODE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                if (Environment.isExternalStorageManager()) {

                    Toast.makeText(this, "Manage Storage Permission granted", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "Manage Storage Permission denied", Toast.LENGTH_SHORT).show();

                }

            }

        }

    }

}

