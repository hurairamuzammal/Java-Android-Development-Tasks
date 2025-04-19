package com.example.android_firebase_task;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Storage reference to the 'data' directory
        storageReference = FirebaseStorage.getInstance().getReference().child("data");

        fetchData();
    }

    private void fetchData() {
        // List all files in the 'data' directory
        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        List<DataModel> list = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            // Get file name and download URL
                            String fileName = item.getName();
                            item.getDownloadUrl().addOnSuccessListener(uri -> {
                                // Create DataModel with file name as title and URL as description
                                DataModel model = new DataModel(fileName, fileName, uri.toString());
                                list.add(model);
                                // Update adapter after all URLs are fetched
                                if (list.size() == listResult.getItems().size()) {
                                    adapter.setData(list);
                                    Toast.makeText(ViewDataActivity.this, "📥 Data loaded (" + list.size() + " items)", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(e -> {
                                Log.e("FirebaseStorage", "Failed to get URL for " + fileName + ": " + e.getMessage());
                            });
                        }
                        // Handle case where no files are found
                        if (listResult.getItems().isEmpty()) {
                            adapter.setData(list);
                            Toast.makeText(ViewDataActivity.this, "📥 No files found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewDataActivity.this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseStorage", "Failed to list files: " + e.getMessage());
                    }
                });
    }
}