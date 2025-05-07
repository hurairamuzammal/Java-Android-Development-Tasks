package com.example.semester_project;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextureView backgroundTextureView;
    private MediaPlayer mediaPlayer;

    Button buttonModel1, buttonModel2, buttonModel3, buttonModel4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // XML includes TextureView

        // Buttons
        buttonModel1 = findViewById(R.id.buttonModel1);
        buttonModel2 = findViewById(R.id.buttonModel2);


        // TextureView for video background
        backgroundTextureView = findViewById(R.id.textureView);

        backgroundTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                playVideo(new Surface(surfaceTexture));
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {}
            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) { return true; }
            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {}
        });

        // Button click
        buttonModel1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, text_recognition.class);
            startActivity(intent);
        });

        buttonModel2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, qr_scanner.class);
            startActivity(intent);
        });


    }

    private void playVideo(Surface surface) {
        mediaPlayer = MediaPlayer.create(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background));
        mediaPlayer.setSurface(surface);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0f, 0f); // mute

        mediaPlayer.setOnVideoSizeChangedListener((mp, videoWidth, videoHeight) -> {
            float scaleX = (float) backgroundTextureView.getWidth() / videoWidth;
            float scaleY = (float) backgroundTextureView.getHeight() / videoHeight;

            float scale = Math.max(scaleX, scaleY); // stretch to fill
            float scaledWidth = scale * videoWidth;
            float scaledHeight = scale * videoHeight;
            float dx = (backgroundTextureView.getWidth() - scaledWidth) / 2;
            float dy = (backgroundTextureView.getHeight() - scaledHeight) / 2;

            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postTranslate(dx, dy);
            backgroundTextureView.setTransform(matrix);
        });

        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
