package com.example.travel_plan.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.travel_plan.config.SQLiteConfig;
import com.example.travel_plan.entities.Travel;

import java.util.Arrays;
import java.util.Optional;


public class TravelRepository extends SQLiteOpenHelper {

    public TravelRepository(@Nullable Context context) {
        super(context, SQLiteConfig.DB_NAME, null, SQLiteConfig.DB_VERSION);
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Travel.CREATE_TBL_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Travel.TBL_NAME + ";");
        this.onCreate(db);
    }

    public Travel save(Travel travel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Travel.TITLE_FIELD, travel.getTitle());
        contentValues.put(Travel.CONTENT_FIELD, travel.getContent());

        long id = db.insert(Travel.TBL_NAME, null, contentValues);
        travel.setId(id);
        System.out.println("Inserted new record: " + id);
        System.out.println(travel);
        return travel;
    }

    public Travel findById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {id.toString()};
        Cursor cursor = db.rawQuery("select * from " + Travel.TBL_NAME + " where " + Travel.ID_FIELD + " = ? ",
                args
        );
        System.out.println("====result: "+ cursor.toString());
        if(cursor.getCount() == 0) return null;
        cursor.moveToNext();
        Travel travel = new Travel();
        travel.setId(cursor.getLong(0));
        travel.setTitle(cursor.getString(1));
        travel.setContent(cursor.getString(2));
//        System.out.println("created At: " + cursor.getString(3));
//        travel.setCreatedAt(cursor.getString(3));
//        travel.setUpdatedAt(cursor.getString(4));

        System.out.println(travel);
        return travel;
    }
}
