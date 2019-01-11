package com.foi.air.studentattendancesystem.adapters_prof;

public class Courses {
    private int id;
    private String title, day, startTime, endTime,  classroom;

    public Courses (int id, String title, String day, String startTime, String endTime, String classroom) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getClassroom() {
        return classroom;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
