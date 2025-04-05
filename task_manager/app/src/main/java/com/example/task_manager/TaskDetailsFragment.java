package com.example.task_manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.Serializable;
public class TaskDetailsFragment extends Fragment {
    private TextView titleTextView, descriptionTextView, dateTextView, timeTextView, priorityTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        titleTextView = view.findViewById(R.id.taskTitle);
        descriptionTextView = view.findViewById(R.id.taskDescription);
        dateTextView = view.findViewById(R.id.taskDueDate);
        timeTextView = view.findViewById(R.id.taskTime);
        priorityTextView = view.findViewById(R.id.taskPriority);

        Bundle bundle = getArguments();
        if (bundle != null) {
            titleTextView.setText(bundle.getString("task_title"));
            descriptionTextView.setText(bundle.getString("task_description"));
            dateTextView.setText(bundle.getString("task_date"));
            timeTextView.setText(bundle.getString("task_time"));

            int priority = bundle.getInt("task_priority");
            priorityTextView.setText("Priority: " + priority);
        }

        return view;
    }
}
