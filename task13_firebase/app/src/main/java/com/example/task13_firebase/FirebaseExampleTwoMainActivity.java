package com.example.task13_firebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseExampleTwoMainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference reference;
    FirebaseDatabase database;
    ArrayList<Student> arrayList;
    FirebaseAdapter firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.firebaseRecyclerView);
        database = FirebaseDatabase.getInstance("https://abc-8872b-default-rtdb.firebaseio.com/").getReference().getDatabase();
        reference = database.getReference("Student");
        arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        firebaseAdapter = new FirebaseAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(firebaseAdapter);
        LoadData();
    }

    public void LoadData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Student obj = new Student((String) dataSnapshot.child("Name").getValue(), (String) dataSnapshot.child("PICTURE").getValue());
                    arrayList.add(obj);
                }
                firebaseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}