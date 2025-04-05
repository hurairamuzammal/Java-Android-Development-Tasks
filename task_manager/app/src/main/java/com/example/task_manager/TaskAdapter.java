package com.example.task_manager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_manager.TaskModel;

import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private List<TaskModel> taskList;
    private OnItemClickListener onItemClickListener;

    // Interface for item clicks
    public interface OnItemClickListener {
        void onItemClick(TaskModel task);
    }

    public TaskAdapter(Context context, List<TaskModel> taskList, OnItemClickListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskDate.setText(task.getDate());

        // Set priority color indicator (optional)
        int priorityColor = getPriorityColor(task.getPriority());
        holder.priorityIndicator.setBackgroundColor(priorityColor);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDate;
        View priorityIndicator;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDate = itemView.findViewById(R.id.taskDueDate);
            priorityIndicator = itemView.findViewById(R.id.taskPriority);
        }
    }

    private int getPriorityColor(int priority) {
        switch (priority) {
            case 1: return Color.RED;
            case 2: return Color.YELLOW;
            case 3: return Color.GREEN;
            default: return Color.GRAY;
        }
    }
}
