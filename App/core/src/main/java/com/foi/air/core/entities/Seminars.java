package com.foi.air.core.entities;

public class Seminars {
    private int id;
    private String title, day, time, classroom;

    public Seminars(int id, String title, String day, String time, String classroom) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.time = time;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getClassroom() {
        return classroom;
    }
}
