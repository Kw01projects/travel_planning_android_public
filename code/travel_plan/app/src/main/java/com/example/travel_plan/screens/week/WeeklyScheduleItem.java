package com.example.travel_plan.screens.week;

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

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}