package com.example.foregroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class DownloadService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("services","Service onstartCommand");

        new Thread(()->{
            for  (int i=0;i<=100;i+=100)
            {
                Log.d("services","Download Progress"+i+"%");

                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return START_STICKY;
    }
}
