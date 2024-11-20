package com.example.travel_plan.entities;


import java.util.Date;

public class TaskList {
    private Integer id;
    private Integer travelId;
    private String title;
    private Boolean hasDone;
    private Date createdAt;
    private Date updatedAt;

    public static final String TBL_NAME = "tbl_travel";
    public static final String ID_FIELD = "id";
    public static final String TRAVEL_ID_FIELD = "travelId";
    public static final String TITLE_FIELD = "title";
    public static final String HAS_DONE_FIELD = "hasDone";
    public static final String CREATED_AT_FIELD = "createdAt";
    public static final String UPDATED_AT_FIELD = "updatedAt";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " (" +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            TRAVEL_ID_FIELD + " INTEGER NOT NULL," +
            TITLE_FIELD + " TEXT NOT NULL," +
            HAS_DONE_FIELD + " INTEGER DEFAULT FALSE," +
            CREATED_AT_FIELD + " TEXT DEFAULT datetime()," +
            UPDATED_AT_FIELD + " TEXT DEFAULT datetime()," +
            ")";
}
