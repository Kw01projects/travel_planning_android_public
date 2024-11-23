package com.example.travel_plan.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import com.example.travel_plan.config.SQLiteConfig;
import com.example.travel_plan.entities.BaseEntity;
import com.example.travel_plan.entities.User;
import com.example.travel_plan.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class UserRepository extends SQLiteOpenHelper {
    public UserRepository(@Nullable Context context) {
        super(context, SQLiteConfig.DB_NAME, null, SQLiteConfig.DB_VERSION);

        this.onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(User.CREATE_TBL_SQL);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + User.TBL_NAME + ";");
    }

    public User getMyInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {"1"};
        Cursor cursor = db.rawQuery("select * from " + User.TBL_NAME + " where " + User.ID_FIELD + " = ? ",
                args
        );
        if (cursor.getCount() == 0) {
            User newUser = new User();
            newUser.setNickName("");
            this.save(newUser);
        }
        cursor.moveToNext();
        User user = new User();
        user.setNickName(cursor.getString(1));
        try {
            user.setCreatedAt(DateUtils.parseDbDatetime(cursor.getString(2)));
            user.setUpdatedAt(DateUtils.parseDbDatetime(cursor.getString(3)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cursor.close();
        return user;
    }

    public User save(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date currentDate = Calendar.getInstance().getTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.ID_FIELD, 1L);
        contentValues.put(User.NICKNAME_FIELD, user.getNickName());
        contentValues.put(BaseEntity.CREATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));
        contentValues.put(BaseEntity.UPDATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));

        User userCheck = this.getMyInfo();
        if (userCheck != null) {
            String[] whereArgs = {"1"};
            db.update(User.TBL_NAME, contentValues, "", whereArgs);
            user.setUpdatedAt(currentDate);
        } else
            user.setId(db.insert(User.TBL_NAME, null, contentValues));
        return user;
    }
}
