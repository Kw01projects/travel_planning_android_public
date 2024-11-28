package com.example.travel_plan.dtos;

import com.example.travel_plan.entities.Task;

import java.util.Date;

public class ScheduleItem {
    private String task;
    private String startTime;
    private String endTime;
    private boolean isCompleted;
    private Task taskInstance;

    public ScheduleItem(Task _taskInstance) {
        this.taskInstance = _taskInstance;
        this.task = taskInstance.getTitle();
        this.startTime = taskInstance.getStartTime();
        this.endTime = taskInstance.getEndTime();
        this.isCompleted = taskInstance.getHasDone();
    }

    public ScheduleItem(String task, String startTime, String endTime, boolean isCompleted, Date selectedDate) {
        this.task = task;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
        taskInstance = new Task();
        taskInstance.setTitle(task);
        taskInstance.setStartDate(selectedDate);
        taskInstance.setStartTime(startTime);
        taskInstance.setEndTime(endTime);
        taskInstance.setHasDone(isCompleted);
    }

    // Getter 및 Setter
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; this.taskInstance.setHasDone(completed); }

    public Task getTaskInstance() {
        return taskInstance;
    }
}