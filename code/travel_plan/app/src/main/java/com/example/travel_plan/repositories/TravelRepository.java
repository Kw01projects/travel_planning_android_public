package com.example.travel_plan.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.travel_plan.config.SQLiteConfig;
import com.example.travel_plan.entities.BaseEntity;
import com.example.travel_plan.entities.Travel;
import com.example.travel_plan.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;


public class TravelRepository extends SQLiteOpenHelper {

    public TravelRepository(@Nullable Context context) {
        super(context, SQLiteConfig.DB_NAME, null, SQLiteConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //        db.execSQL("DROP TABLE IF EXISTS " + Travel.TBL_NAME + ";");
            db.execSQL(Travel.CREATE_TBL_SQL);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Travel save(Travel travel) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date currentDate = Calendar.getInstance().getTime();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Travel.TITLE_FIELD, travel.getTitle());
        contentValues.put(Travel.PLACE_FIELD, travel.getPlace());
        contentValues.put(Travel.MEMO_FIELD, travel.getMemo());
        contentValues.put(Travel.EXPENSE_NOTE_FIELD, travel.getExpenseNote());
        contentValues.put(Travel.START_DATE_AT_FIELD, DateUtils.formatDbDate(travel.getStartDate()));
        contentValues.put(BaseEntity.UPDATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));

        if (travel.getId() == null) {
            contentValues.put(BaseEntity.CREATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));
            travel.setId(db.insert(Travel.TBL_NAME, null, contentValues));
        } else {
            String[] whereArgs = {travel.getId().toString()};
            db.update(Travel.TBL_NAME, contentValues, Travel.ID_FIELD + " = ?", whereArgs);
        }
        return travel;
    }

    public Travel findByDate(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {DateUtils.formatDbDate(date)};
        Cursor cursor = db.rawQuery("select * from " + Travel.TBL_NAME + " where " + Travel.START_DATE_AT_FIELD + " = ? ",
                args
        );
        if (cursor.getCount() == 0) return null;
        cursor.moveToNext();
        Travel travel = this.mapCursor(cursor);
        cursor.close();
        return travel;
    }

    public Travel findById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {id.toString()};
        Cursor cursor = db.rawQuery("select * from " + Travel.TBL_NAME + " where " + Travel.ID_FIELD + " = ? ",
                args
        );
        if (cursor.getCount() == 0) return null;
        cursor.moveToNext();
        Travel travel = this.mapCursor(cursor);
        cursor.close();
        return travel;
    }

    private Travel mapCursor(Cursor cursor) {
        Travel travel = new Travel();
        travel.setId(cursor.getLong(0));
        travel.setTitle(cursor.getString(1));
        travel.setPlace(cursor.getString(2));
        travel.setMemo(cursor.getString(3));
        travel.setExpenseNote(cursor.getString(4));
        travel.setStartDate(DateUtils.parseDbDate(cursor.getString(5)));
        travel.setCreatedAt(DateUtils.parseDbDatetime(cursor.getString(6)));
        travel.setUpdatedAt(DateUtils.parseDbDatetime(cursor.getString(7)));
        return travel;
    }
}
