package com.example.travel_plan.entities;

public class MapPlace {
    private Long id;
    private String place;
    private String address;
    private String latitude;
    private String longitude;
    private String type;


    public static final String TBL_NAME = "tbl_map_place";
    public static final String ID_FIELD = "id";
    public static final String PLACE_FIELD = "place";
    public static final String ADDRESS_FIELD = "address";
    public static final String LATITUDE_FIELD = "latitude";
    public static final String LONGITUDE_FIELD = "longitude";
    public static final String TYPE_FIELD = "type";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " ( " +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            PLACE_FIELD + " TEXT," +
            ADDRESS_FIELD + " TEXT," +
            LATITUDE_FIELD + " TEXT," +
            LONGITUDE_FIELD + " TEXT," +
            TYPE_FIELD + " TEXT" +
            ");";

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MapPlace{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
