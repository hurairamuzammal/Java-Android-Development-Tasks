package com.example.task13_firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirebaseExampleOneMainActivity extends AppCompatActivity {
    TextView textView;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.txtFirebase);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://abc-8872b-default-rtdb.firebaseio.com/");
        mReference = database.getReference("Student2");
    }

    public void Send(View view) {
        mReference.child(Objects.requireNonNull(mReference.push().getKey())).setValue("HelloWorld");
        Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
    }

    public void Retrieve(View view) {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    StringBuilder val = new StringBuilder();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        val.append((String) dataSnapshot.getValue()).append("\n");
                    }
                    textView.setText    (val.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}