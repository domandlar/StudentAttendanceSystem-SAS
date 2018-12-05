package com.foi.air.core.entities;

public class Seminar {
    private int id;
    private String title, day, time, classroom;

    public Seminar(int id, String title, String day, String time, String classroom) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.time = time;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
