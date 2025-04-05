package com.example.feb27_task1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Student_adapter extends RecyclerView.Adapter<Student_adapter.ViewHolder> {

    private List<Student> Student_list;

    // Constructor
    Student_adapter(List<Student> list) {
        this.Student_list = list;
    }

    @NonNull
    @Override
    public Student_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Student_adapter.ViewHolder holder, int position) {
        Student student = Student_list.get(position);
        holder.nameText.setText(student.getName());
        holder.rollText.setText(student.getRollNo());
        holder.imageView.setImageResource(student.getimage()); // Set image
    }

    @Override
    public int getItemCount() {
        return Student_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, rollText;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textView);
            rollText = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView2);
            cardView = itemView.findViewById(R.id.c1);
        }
    }
}
