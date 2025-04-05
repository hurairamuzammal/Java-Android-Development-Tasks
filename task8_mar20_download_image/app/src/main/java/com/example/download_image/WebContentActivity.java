package com.example.download_image;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class WebContentActivity extends AppCompatActivity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_content);

        button = findViewById(R.id.button8);
        editText = findViewById(R.id.tbtMulltiLineWeb);

        button.setOnClickListener(v -> {
            try {
                // Execute AsyncTask to download web content
                String content = new DownloadWebContent().execute("https://www.google.com").get();
                Log.d("TAG", "Back in MainActivity");
                editText.setText(content); // Set the downloaded content in the EditText
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // AsyncTask to download the web content
    private static class DownloadWebContent extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Log.d("Tag", "doInBackground in Progress");
            StringBuilder webContent = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);

                int data;
                while ((data = reader.read()) != -1) {
                    webContent.append((char) data);
                }

            } catch (IOException e) {
                return "Error: " + e.getMessage();
            }

            return webContent.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Tag", "Download Completed");
        }
    }
}
