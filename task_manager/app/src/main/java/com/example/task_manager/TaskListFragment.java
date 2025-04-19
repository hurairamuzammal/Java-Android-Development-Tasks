package com.example.task_manager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class TaskListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskModel> taskList;
    private db_class db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new db_class(getContext(), "MYDATABASE", null, 1);
        loadTasks();

        return view;
    }

    private void loadTasks() {
        taskList = new ArrayList<>();
        Cursor cursor = db.getAllTasks();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));  // Fix
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));  // Fix
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
                TaskModel task = new TaskModel(id, title, description, date, time, priority);
                taskList.add(task);
            } while (cursor.moveToNext());
            cursor.close();
        }

        taskAdapter = new TaskAdapter(getContext(), taskList, task -> openTaskDetails(task));
        recyclerView.setAdapter(taskAdapter);
    }

    private void openTaskDetails(TaskModel task) {
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("task_id", task.getId());
        bundle.putString("task_title", task.getTitle());
        bundle.putString("task_description", task.getDescription());
        bundle.putString("task_date", task.getDate());
        bundle.putString("task_time", task.getTime());
        bundle.putInt("task_priority", task.getPriority());

        taskDetailsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, taskDetailsFragment)
                .addToBackStack(null)
                .commit();


    }


}
