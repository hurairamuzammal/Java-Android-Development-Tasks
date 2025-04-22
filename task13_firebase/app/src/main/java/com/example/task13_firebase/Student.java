package com.example.task13_firebase;
public class Student {
    String Name, Url;

    public String getName() {
        return Name;


    }

    public void setName(String name) {
        Name = name;


    }

    public String getUrl() {
        return Url;
    }


    public void setUrl(String url) {
        Url = url;


    }

    public Student(String name, String url) {
        Name = name;
        Url = url;

    }
}