// MainActivity.java
package com.example.android_firebase_task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("data");

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Add Data Button
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> showAddDataDialog());

        // Read Data from Firebase
        readData();
    }

    private void showAddDataDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Data");

        // Inflate the layout for the dialog
        View view = LayoutInflater.from(this).inflate(R.layout.activity_add_data, null);
        EditText etName = view.findViewById(R.id.et_name);
        EditText etDescription = view.findViewById(R.id.et_description);

        builder.setView(view);

        // Set up the buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (!name.isEmpty() && !description.isEmpty()) {
                addDataToFirebase(name, description);
            } else {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addDataToFirebase(String name, String description) {
        String id = databaseReference.push().getKey();
        DataModel data = new DataModel(id, name, description);

        if (id != null) {
            databaseReference.child(id).setValue(data)
                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to add data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void readData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DataModel> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataModel data = dataSnapshot.getValue(DataModel.class);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
                adapter.setData(dataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
