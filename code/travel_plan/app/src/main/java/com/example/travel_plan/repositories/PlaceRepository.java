package com.example.travel_plan.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travel_plan.config.SQLiteConfig;
import com.example.travel_plan.entities.Place;
import com.example.travel_plan.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlaceRepository extends SQLiteOpenHelper {

    public PlaceRepository(Context context) {
        super(context, SQLiteConfig.DB_NAME, null, SQLiteConfig.DB_VERSION);
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(Place.CREATE_TBL_SQL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private Place mapCursor(Cursor cursor) {
        Place task = new Place();
        task.setId(cursor.getLong(0));
        task.setPlace(cursor.getString(1));
        return task;
    }

    public List<Place> list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {};
        Cursor cursor = db.rawQuery("select * from " + Place.TBL_NAME, args);
        List<Place> placeList = new ArrayList<>();
        while (cursor.moveToNext())
            placeList.add(mapCursor(cursor));
        cursor.close();
        return placeList;
    }

    public Place save(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date currentDate = Calendar.getInstance().getTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Place.PLACE_FIELD, place.getPlace());
        contentValues.put(Place.UPDATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));

        if (place.getId() == null) {
            contentValues.put(Place.CREATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));
            place.setId(db.insert(Place.TBL_NAME, null, contentValues));
        } else {
            String[] whereArgs = {place.getId().toString()};
            db.update(Place.TBL_NAME, contentValues, Place.ID_FIELD + " = ?", whereArgs);
        }
        return place;
    }

    public void deleteById(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {id.toString()};
        db.delete(Place.TBL_NAME, Place.ID_FIELD + " = ?", args);
    }
}
