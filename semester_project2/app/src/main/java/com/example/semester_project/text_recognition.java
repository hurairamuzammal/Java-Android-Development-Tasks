package com.example.semester_project;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class text_recognition extends AppCompatActivity {

    ImageView imageView;
    Button capturebutton;
    Bitmap finalBitmap;
    TextView textView;
    Button button2;

    private static final int REQUEST_IMAGE_CAPTURE = 1;


    String currentPhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_text_recognition);
        imageView = findViewById(R.id.cameraImage);
        capturebutton = findViewById(R.id.captureImage);
        textView = findViewById(R.id.resultText);
        button2 = findViewById(R.id.copyTextBtn);


        capturebutton.setOnClickListener(v -> {
            captureImage();  // call function
        });

    }

    private File createImageFile() throws IOException {
        String timestamp = String.valueOf(new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()));

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile("JPEG_${timestamp}_" + timestamp + "_", "jpg", storageDir).getAbsoluteFile();


    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                currentPhotoPath = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.semester_project.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //    public Bitmap flipBitmapHorizontally(Bitmap original) {
//        Matrix matrix = new Matrix();
//        matrix.preScale(1.0f, -1.0f); // Horizontal flip
//        return Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
//    }
    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap; // no rotation needed
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);

            try {
                ExifInterface exif = new ExifInterface(currentPhotoPath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
                imageView.setImageBitmap(rotatedBitmap);
                finalBitmap = rotatedBitmap;
                recognizeText(rotatedBitmap);  // 👈 call OCR after rotating


            } catch (IOException e) {
                e.printStackTrace();
                textView.setText("Error loading image ❌");
            }
        }
    }


    private void recognizeText(Bitmap bitmap) {

        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        recognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    textView.setText(visionText.getText());
                    button2.setVisibility(VISIBLE);  // Show copy button
                })
                .addOnFailureListener(e -> {
                    textView.setText("OCR failed ❌");
                });

//            button2 is copy button pressing on that copy the text to clipboard
        button2.setOnClickListener(v -> {
            String text = textView.getText().toString();
            if (!text.isEmpty()) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("OCR Text", text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(text_recognition.this, "Text copied to clipboard ✅", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(text_recognition.this, "No text to copy ⚠️", Toast.LENGTH_SHORT).show();
            }
        });

    }


}