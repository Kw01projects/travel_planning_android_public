package com.example.travel_plan.entities;

import java.util.Date;

public class Travel {
    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public static Travel builder(){
        return new Travel();
    }

    @Override
    public String toString() {
        return "Travel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public static final String TBL_NAME = "tbl_travel";
    public static final String ID_FIELD = "id";
    public static final String TITLE_FIELD = "title";
    public static final String CONTENT_FIELD = "content";
    public static final String CREATED_AT_FIELD = "createdAt";
    public static final String UPDATED_AT_FIELD = "updatedAt";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " ( " +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            TITLE_FIELD + " TEXT," +
            CONTENT_FIELD + " TEXT," +
            CREATED_AT_FIELD + " TEXT," +
            UPDATED_AT_FIELD + " TEXT" +
            ");";
}

