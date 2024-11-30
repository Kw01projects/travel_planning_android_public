package com.example.travel_plan.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travel_plan.config.SQLiteConfig;
import com.example.travel_plan.entities.MapPlace;

import java.util.ArrayList;
import java.util.List;

public class MapPlaceRepository extends SQLiteOpenHelper {

    public MapPlaceRepository(Context context) {
        super(context, SQLiteConfig.DB_NAME, null, SQLiteConfig.DB_VERSION);
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(MapPlace.CREATE_TBL_SQL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private MapPlace mapCursor(Cursor cursor) {
        MapPlace task = new MapPlace();
        task.setId(cursor.getLong(0));
        task.setPlace(cursor.getString(1));
        task.setAddress(cursor.getString(2));
        task.setLatitude(cursor.getString(3));
        task.setLongitude(cursor.getString(4));
        task.setType(cursor.getString(5));
        return task;
    }

    public List<MapPlace> list(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {type};
        Cursor cursor = db.rawQuery("select * from " + MapPlace.TBL_NAME + " where " + MapPlace.TYPE_FIELD + " = ?", args);
        List<MapPlace> placeList = new ArrayList<>();
        while (cursor.moveToNext())
            placeList.add(mapCursor(cursor));
        cursor.close();
        return placeList;
    }

    public MapPlace save(MapPlace place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapPlace.ADDRESS_FIELD, place.getAddress());
        contentValues.put(MapPlace.LATITUDE_FIELD, place.getLatitude());
        contentValues.put(MapPlace.LONGITUDE_FIELD, place.getLongitude());
        contentValues.put(MapPlace.TYPE_FIELD, place.getType());

        if (place.getId() == null) {
            place.setId(db.insert(MapPlace.TBL_NAME, null, contentValues));
        } else {
            String[] whereArgs = {place.getId().toString()};
            db.update(MapPlace.TBL_NAME, contentValues, MapPlace.ID_FIELD + " = ?", whereArgs);
        }
        return place;
    }

    public MapPlace findByAddress(String type, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {type, address};
        Cursor cursor = db.rawQuery("select * from " + MapPlace.TBL_NAME + " where " + MapPlace.TYPE_FIELD + " = ? and " + MapPlace.ADDRESS_FIELD + " = ?", args);

        if (cursor.getCount() == 0) return null;
        cursor.moveToNext();
        MapPlace mapPlace = mapCursor(cursor);
        cursor.close();
        return mapPlace;
    }

    public void deleteById(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {id.toString()};
        db.delete(MapPlace.TBL_NAME, MapPlace.ID_FIELD + " = ?", args);
    }
}
