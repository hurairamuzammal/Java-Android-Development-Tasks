package com.example.task_manager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {
    private static final String CHANNEL_ID = "task_channel";
    private static final int FOREGROUND_ID = 1001;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = intent.getStringExtra("task_title");
        String description = intent.getStringExtra("task_description");

        // Create foreground notification
        Notification foregroundNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Task Manager Running")
                .setContentText("Monitoring tasks...")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        // Start as foreground service
        startForeground(FOREGROUND_ID, foregroundNotification);

        // Create task notification
        Notification taskNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title != null ? title : "Task Reminder")
                .setContentText(description != null ? description : "You have an upcoming task")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = getSystemService(NotificationManager.class);
        }
        if (manager != null) {
            manager.notify((int) System.currentTimeMillis(), taskNotification);
        }

        // Stop the service after showing the notification
        stopSelf();
        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Task Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for task reminders");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}