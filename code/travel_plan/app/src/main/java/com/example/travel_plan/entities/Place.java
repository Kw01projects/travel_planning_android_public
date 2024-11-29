package com.example.travel_plan.entities;

public class Place extends BaseEntity {

    private Long id;
    private String place;


    public static final String TBL_NAME = "tbl_place";
    public static final String ID_FIELD = "id";
    public static final String PLACE_FIELD = "place";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " ( " +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            PLACE_FIELD + " TEXT," +
            CREATED_AT_FIELD + " TEXT," +
            UPDATED_AT_FIELD + " TEXT" +
            ");";

    public Place() {
    }

    public Place(String place) {
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
