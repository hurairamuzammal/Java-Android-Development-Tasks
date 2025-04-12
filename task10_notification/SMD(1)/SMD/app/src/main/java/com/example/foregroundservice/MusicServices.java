package com.example.foregroundservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MusicServices extends Service {

    private MediaPlayer mediaPlayer;
    private static final String TAG="MusicService";

    private final IBinder binder=new MusicBinder();


    public class MusicBinder extends  Binder {
        public MusicServices getService(){
            return MusicServices.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "music_channel_id",
                    "Music Playback",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Channel for music playback service");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = new NotificationCompat.Builder(this, "music_channel_id")
                .setContentTitle("Playing Music")
                .setContentText("Your tune is playing in the background.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)  // Must be a valid icon
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
       if(mediaPlayer!=null)
       {
           mediaPlayer.release();
           mediaPlayer=null;
       }
       super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"Service Bound");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"Service unbound");
        return super.onUnbind(intent);
    }

    public void play()
    {
        if(mediaPlayer==null)
        {
            mediaPlayer=MediaPlayer.create(this,R.raw.tune);
            mediaPlayer.setLooping(true);

        }
        mediaPlayer.start();
    }

    public void pause()
    {
        if(mediaPlayer!=null&& mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }
        stopForeground(true);
        super.onDestroy();
    }

}