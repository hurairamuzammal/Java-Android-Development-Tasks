package com.example.task_manager;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class Notification extends Service {


    MediaPlayer ringtone;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ringtone = MediaPlayer.create(this,
                Settings.System.DEFAULT_NOTIFICATION_URI);
//        ringtone.setLooping(true); // Loop the ringtone indefinitely
        ringtone.start(); // Start the ringtone
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        ringtone.stop(); // Stop the ringtone
        super.onDestroy(); // Call superclass method for cleanup
    }
}
