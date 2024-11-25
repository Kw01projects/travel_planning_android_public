package com.example.travel_plan;

public class ScheduleItem {
    private String task;
    private String startTime;
    private String endTime;
    private boolean isCompleted;

    public ScheduleItem(String task, String startTime, String endTime, boolean isCompleted) {
        this.task = task;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
    }

    // Getter 및 Setter
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}