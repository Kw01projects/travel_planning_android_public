package com.example.travel_plan;

public class WeeklyScheduleItem {
    private int day; // 0 = Sunday, 6 = Saturday
    private int hour; // 0~23
    private String task;

    public WeeklyScheduleItem(int day, int hour, String task) {
        this.day = day;
        this.hour = hour;
        this.task = task;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}