package com.example.travel_plan.entities;

import java.util.Date;

public class User extends BaseEntity {
    private Long id;
    private String nickName;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;

    public static final String TBL_NAME = "tbl_user";
    public static final String ID_FIELD = "id";
    public static final String NICKNAME_FIELD = "nickName";
    public static final String START_DATE_FIELD = "startDate";
    public static final String END_DATE_FIELD = "endDate";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " ( " +
            ID_FIELD + " INTEGER NOT NULL," +
            NICKNAME_FIELD + " TEXT," +
            START_DATE_FIELD + " TEXT," +
            END_DATE_FIELD + " TEXT," +
            CREATED_AT_FIELD + " TEXT," +
            UPDATED_AT_FIELD + " TEXT" +
            ");";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
