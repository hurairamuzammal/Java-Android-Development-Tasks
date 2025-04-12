package com.example.foregroundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    private MusicServices musicServices;
    private  boolean isBound=false;
    private Button playBtn,pauseBtn;


    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicServices.MusicBinder binder=(MusicServices.MusicBinder)service;
            musicServices=binder.getService();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        playBtn=findViewById(R.id.button5);
        pauseBtn=findViewById(R.id.button6);


        Intent intent=new Intent(this, MusicServices.class);
        Intent serviceIntent = new Intent(this, MusicServices.class);
        startService(serviceIntent); // This triggers onStartCommand()
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

        playBtn.setOnClickListener(v->{if(isBound)musicServices.play(); Intent startIntent = new Intent(this, MusicServices.class);
            startService(startIntent);});
        pauseBtn.setOnClickListener(v->{if (isBound)musicServices.pause(); Intent stopIntent = new Intent(this, MusicServices.class);
            stopService(stopIntent);});

    }
}