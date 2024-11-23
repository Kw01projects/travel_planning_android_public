package com.example.travel_plan.entities;

import java.util.Date;

public class Travel extends BaseEntity {
    private Long id;
    private String title;
    private String place;
    private String memo;
    private String expenseNote;
    private Date startDate;

    public Travel() {}

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getExpenseNote() {
        return expenseNote;
    }

    public void setExpenseNote(String expenseNote) {
        this.expenseNote = expenseNote;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public static final String TBL_NAME = "tbl_travel";
    public static final String ID_FIELD = "id";
    public static final String TITLE_FIELD = "title";
    public static final String PLACE_FIELD = "place";
    public static final String MEMO_FIELD = "memo";
    public static final String EXPENSE_NOTE_FIELD = "expenseNote";
    public static final String START_DATE_AT_FIELD = "startDate";
    public static final String END_DATE_AT_FIELD = "endDate";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " ( " +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            TITLE_FIELD + " TEXT," +
            PLACE_FIELD + " TEXT," +
            MEMO_FIELD + " TEXT," +
            EXPENSE_NOTE_FIELD + " TEXT," +
            START_DATE_AT_FIELD + " TEXT," +
            END_DATE_AT_FIELD + " TEXT," +
            CREATED_AT_FIELD + " TEXT," +
            UPDATED_AT_FIELD + " TEXT" +
            ");";
}

