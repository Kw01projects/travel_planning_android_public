package com.example.travel_plan.entities;

import java.util.Date;

public class Task extends BaseEntity {
    private Long id;
    private String title;
    private Boolean hasDone;
    private Date startDate;
    private String startTime;
    private String endTime;

    public static final String TBL_NAME = "tbl_tasks";
    public static final String ID_FIELD = "id";
    public static final String TITLE_FIELD = "title";
    public static final String HAS_DONE_FIELD = "hasDone";
    public static final String START_DATE_AT_FIELD = "startDate";
    public static final String START_TIME_AT_FIELD = "startTime";
    public static final String END_TIME_AT_FIELD = "endTime";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " (" +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            TITLE_FIELD + " TEXT NOT NULL," +
            HAS_DONE_FIELD + " INTEGER DEFAULT FALSE," +
            START_DATE_AT_FIELD + " TEXT," +
            START_TIME_AT_FIELD + " TEXT," +
            END_TIME_AT_FIELD + " TEXT," +
            CREATED_AT_FIELD + " TEXT," +
            UPDATED_AT_FIELD + " TEXT" +
            ")";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getHasDone() {
        return hasDone;
    }

    public void setHasDone(Boolean hasDone) {
        this.hasDone = hasDone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStartHour(){
        return Integer.parseInt(this.startTime.substring(0, 2));
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", hasDone=" + hasDone +
                ", startDate=" + startDate +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
