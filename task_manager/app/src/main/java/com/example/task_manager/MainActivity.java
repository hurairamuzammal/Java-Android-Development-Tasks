package com.example.task_manager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, "Notifications permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }

        // Database and task processing
        db_class db = new db_class(this, "MYDATABASE", null, 1);
        SQLiteDatabase database = db.getReadableDatabase();

        Cursor cursor = db.getAllTasks();
        long currentTime = System.currentTimeMillis();
        Date nearestTaskTime = null;
        String nearestTitle = "", nearestDesc = "", nearestTime = "";

        while (cursor.moveToNext()) {
            String taskDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String taskTime = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String taskTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String taskDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
                Date taskDateTime = sdf.parse(taskDate + " " + taskTime);

                long timeDiff = taskDateTime.getTime() - currentTime;

                // Notify for tasks within 30 minutes
                if (timeDiff >= 0 && timeDiff <= 1800000) {
                    Intent serviceIntent = new Intent(MainActivity.this, NotificationService.class);
                    serviceIntent.putExtra("task_title", "[Within 30 Min] " + taskTitle);
                    serviceIntent.putExtra("task_description", taskDescription);
                    ContextCompat.startForegroundService(this, serviceIntent);
                }

                // Track nearest upcoming task
                if (taskDateTime.getTime() > currentTime &&
                        (nearestTaskTime == null || taskDateTime.before(nearestTaskTime))) {
                    nearestTaskTime = taskDateTime;
                    nearestTitle = taskTitle;
                    nearestDesc = taskDescription;
                    nearestTime = taskTime;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        // Notify for the nearest upcoming task
        if (nearestTaskTime != null) {
            Intent serviceIntent = new Intent(MainActivity.this, NotificationService.class);
            serviceIntent.putExtra("task_title", "[Upcoming Task] " + nearestTitle);
            serviceIntent.putExtra("task_description", nearestDesc);
            ContextCompat.startForegroundService(this, serviceIntent);
        }

        // Bottom navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        loadFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}