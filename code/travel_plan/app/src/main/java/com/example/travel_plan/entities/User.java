package com.example.travel_plan.entities;

import java.util.Date;

public class User extends BaseEntity {
    private Long id;
    private String nickName;
    private Date createdAt;
    private Date updatedAt;

    public static final String TBL_NAME = "tbl_user";
    public static final String ID_FIELD = "id";
    public static final String NICKNAME_FIELD = "nickName";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " ( " +
            ID_FIELD + " INTEGER NOT NULL," +
            NICKNAME_FIELD + " TEXT," +
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
