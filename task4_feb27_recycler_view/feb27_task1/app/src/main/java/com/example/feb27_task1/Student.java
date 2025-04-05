package com.example.feb27_task1;




public class Student {
    private String name;
    private String rollNo;
    private int image;

    public Student(String name, String rollNo, int image) {
        this.name = name;
        this.rollNo = rollNo;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public int getimage() {
        return image;
    }
}

