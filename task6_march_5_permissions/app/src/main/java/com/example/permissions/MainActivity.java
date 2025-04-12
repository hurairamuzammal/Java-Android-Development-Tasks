package com.example.permissions;

import android.os.Bundle;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int
            CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int
            LOCATION_PERMISSION_REQUEST_CODE = 2;
    private static final int
            INTERNET_PERMISSION_REQUEST_CODE = 3;
    private static final int
            STORAGE_PERMISSION_REQUEST_CODE = 4;
    Button cameraButton, locationBtn, internetBtn,
            storageBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle
                                    savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
// Initialize buttons
        cameraButton = findViewById(R.id.button);
        locationBtn = findViewById(R.id.button2);
        internetBtn = findViewById(R.id.button3);
        storageBtn = findViewById(R.id.button4);
//        Add a button for storage permission
// Add padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars
                                    ());
                    v.setPadding(systemBars.left,
                            systemBars.top, systemBars.right,
                            systemBars.bottom);
                    return insets;
                });
// Set an OnClickListener for each button
//        to request permissions
        cameraButton.setOnClickListener(v ->
                requestCameraPermission());
        locationBtn.setOnClickListener(v ->
                requestLocationPermission());
        internetBtn.setOnClickListener(v ->
                requestInternetPermission());
        storageBtn.setOnClickListener(v ->
                requestStoragePermission());
    }
    // Request camera permission

    private void requestCameraPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
        {
ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

//    private void requestCameraPermission() {
//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA},
//                    CAMERA_PERMISSION_REQUEST_CODE);
//        } else {
//            Toast.makeText(this, "Camera permission already granted", Toast.LENGTH_SHORT).show();
//        }
//    }
    // Request location permission
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED &&ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Location permission already granted",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // Inform user that internet permission is
//    granted by default
    private void requestInternetPermission() {
        Toast.makeText(this, "Internet permission is granted by default", Toast.LENGTH_SHORT).show();
    }
    // Request external storage permission
    private void requestStoragePermission() {
// For Android 9 (API 28) and below,
//        request both read and write external storage
//                permissions
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, STORAGE_PERMISSION_REQUEST_CODE);
            Toast.makeText(this, "Requesting storage permissions", Toast.LENGTH_SHORT).show();
// Add a toast here
        } else {
            Toast.makeText(this, "Storage permission already granted",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // Handle the user's response to permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,uhuhuh permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] ==
                                PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] ==
                                PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readGranted =
                            grantResults[0] ==
                                    PackageManager.PERMISSION_GRANTED;
                    boolean writeGranted =
                            grantResults[1] ==
                                    PackageManager.PERMISSION_GRANTED;
                    if (readGranted &&
                            writeGranted) {
                        Toast.makeText(this,
                                "Storage permission granted",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,
                                "Storage permission denied",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,
                        permissions, grantResults);
        }
    }
}