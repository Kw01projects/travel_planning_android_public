package com.example.travel_plan.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.travel_plan.config.SQLiteConfig;
import com.example.travel_plan.entities.Task;
import com.example.travel_plan.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskRepository extends SQLiteOpenHelper {

    public TaskRepository(@Nullable Context context) {
        super(context, SQLiteConfig.DB_NAME, null, SQLiteConfig.DB_VERSION);
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
//            db.execSQL("DROP TABLE IF EXISTS " + Task.TBL_NAME + ";");
            db.execSQL(Task.CREATE_TBL_SQL);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private Task mapCursor(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setTitle(cursor.getString(1));
        task.setHasDone(cursor.getInt(2) != 0);
        task.setStartDate(DateUtils.parseDbDate(cursor.getString(3)));
        task.setStartTime(cursor.getString(4));
        return task;
    }

    public List<Task> getTaskListByDate(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {DateUtils.formatDbDate(date)};
        Cursor cursor = db.rawQuery("select * from " + Task.TBL_NAME + " where " + Task.START_DATE_AT_FIELD + " = ? ",
                args
        );
        List<Task> taskLists = new ArrayList<>();
        while (cursor.moveToNext())
            taskLists.add(mapCursor(cursor));
        cursor.close();
        Collections.sort(taskLists, (a, b) -> a.getStartTime().compareTo(b.getStartTime()));
        return taskLists;
    }

    public Task findByDate(Date date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {DateUtils.formatDbDate(date)};
        Cursor cursor = db.rawQuery("select * from " + Task.TBL_NAME + " where " + Task.START_DATE_AT_FIELD + " = ? ",
                args
        );
        if (cursor.getCount() == 0) return null;
        cursor.moveToNext();
        Task task = mapCursor(cursor);
        cursor.close();
        return task;
    }

    public Task findById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {id.toString()};
        Cursor cursor = db.rawQuery("select * from " + Task.TBL_NAME + " where " + Task.ID_FIELD + " = ? ",
                args
        );
        if (cursor.getCount() == 0) return null;
        cursor.moveToNext();
        Task task = mapCursor(cursor);
        cursor.close();
        return task;
    }

    public Task save(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date currentDate = Calendar.getInstance().getTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task.TITLE_FIELD, task.getTitle());
        contentValues.put(Task.HAS_DONE_FIELD, task.getHasDone());
        contentValues.put(Task.START_DATE_AT_FIELD, DateUtils.formatDbDate(task.getStartDate()));
        contentValues.put(Task.START_TIME_AT_FIELD, task.getStartTime());
        contentValues.put(Task.END_TIME_AT_FIELD, task.getEndTime());
        contentValues.put(Task.UPDATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));

        if (task.getId() == null) {
            contentValues.put(Task.CREATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));
            task.setId(db.insert(Task.TBL_NAME, null, contentValues));
        } else {
            String[] whereArgs = {task.getId().toString()};
            db.update(Task.TBL_NAME, contentValues, Task.ID_FIELD + " = ?", whereArgs);
        }
        return task;
    }

    public Boolean deleteById(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        db.delete(Task.TBL_NAME, Task.ID_FIELD + " = ?", whereArgs);
        return true;
    }

    public boolean changeTaskStatus(Long id, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date currentDate = Calendar.getInstance().getTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task.HAS_DONE_FIELD, status);
        contentValues.put(Task.UPDATED_AT_FIELD, DateUtils.formatDbDatetime(currentDate));
        String[] whereArgs = {id.toString()};
        db.update(Task.TBL_NAME, contentValues, Task.ID_FIELD + " = ?", whereArgs);
        return true;
    }
}
