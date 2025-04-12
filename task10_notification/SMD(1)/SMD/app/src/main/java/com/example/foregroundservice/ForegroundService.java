package com.example.foregroundservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ForegroundService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification=new Notification
                .Builder(this,"channel_id").setContentTitle("Foreground Service").setContentText("Running....").setSmallIcon(R.drawable.ic_launcher_foreground).build();
    startForeground(1,notification);
    new Thread(()->{}).start();
    return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
