package com.example.task_manager;

import java.io.Serializable;

public class TaskModel implements Serializable {
    private int id;
    private String title;
    private String description;
    private String date;
    private String time;
    private int priority;

    public TaskModel(int id, String title, String description, String date, String time, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getPriority() { return priority; }
}
