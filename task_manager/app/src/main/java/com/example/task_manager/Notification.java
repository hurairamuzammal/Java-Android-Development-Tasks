package com.example.task_manager;



import android.annotation.SuppressLint;
import android.app.NotificationChannel;

import android.app.NotificationManager;

import android.app.Service;

import android.content.Intent;

import android.media.MediaPlayer;

import android.os.Build;

import android.os.IBinder;

import android.provider.Settings;



import androidx.annotation.Nullable;

import androidx.core.app.NotificationCompat;



public class Notification extends Service {



    private MediaPlayer ringtone;

    private static final String CHANNEL_ID = "task_notification_channel";

    private static final int NOTIFICATION_ID = 1;



    @Override

    public void onCreate() {

        super.onCreate();



        // Create notification channel for Android 8.0+

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(

                    CHANNEL_ID,

                    "Task Notifications",

                    NotificationManager.IMPORTANCE_DEFAULT

            );

            channel.setDescription("Notifications for tasks due soon");

            NotificationManager manager = getSystemService(NotificationManager.class);

            if (manager != null) {

                manager.createNotificationChannel(channel);

            }

        }

    }



    @Nullable

    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }



    @SuppressLint("ForegroundServiceType")
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        // Extract task details from intent

        String taskTitle = intent.getStringExtra("task_title");

        String taskDescription = intent.getStringExtra("task_description");

        String taskTime = intent.getStringExtra("task_time");



        if (taskTitle == null) {

            taskTitle = "Task Reminder";

        }

        if (taskDescription == null) {

            taskDescription = "A task is due soon.";

        }

        if (taskTime == null) {

            taskTime = "Now";

        }



        // Build the foreground notification

        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)

                .setContentTitle(taskTitle)

                .setContentText(taskDescription + " at " + taskTime)

                .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure this icon exists

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                .build();



        // Start service as foreground

        startForeground(NOTIFICATION_ID, notification);



        // Play notification sound

        ringtone = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);

        ringtone.setLooping(false); // Play once

        ringtone.start();



        return START_NOT_STICKY;

    }



    @Override

    public void onDestroy() {

        if (ringtone != null) {

            ringtone.stop();

            ringtone.release();

        }

        stopForeground(true);

        super.onDestroy();

    }

}