package com.example.hellofx;

public class Student {
    private final String id, name, major;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public Student(String id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
    }
}
