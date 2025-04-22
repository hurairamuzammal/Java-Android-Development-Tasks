package com.example.task13_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.ViewHolder> {
    ArrayList<Student> studentArrayList;
    Context context;

    public FirebaseAdapter(ArrayList<Student> studentArrayList, Context context) {
        this.studentArrayList = studentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.firebase_student_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(studentArrayList.get(position).getName());
        Glide.with(context).load(studentArrayList.get(position).getURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.firebaseImageView);
            textView = itemView.findViewById(R.id.firebaseTextView);
        }
    }
}