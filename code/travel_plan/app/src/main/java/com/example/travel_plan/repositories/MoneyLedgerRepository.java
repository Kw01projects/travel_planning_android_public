package com.example.travel_plan.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travel_plan.config.SQLiteConfig;
import com.example.travel_plan.entities.MoneyLedger;

import java.util.ArrayList;
import java.util.List;

public class MoneyLedgerRepository extends SQLiteOpenHelper {
    public MoneyLedgerRepository(Context context) {
        super(context, SQLiteConfig.DB_NAME, null, SQLiteConfig.DB_VERSION);
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(MoneyLedger.CREATE_TBL_SQL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private MoneyLedger mapCursor(Cursor cursor) {
        MoneyLedger task = new MoneyLedger();
        task.setId(cursor.getLong(0));
        task.setTitle(cursor.getString(1));
        task.setAmount(cursor.getLong(2));
        task.setDate(cursor.getString(3));
        task.setType(cursor.getString(4));
        return task;
    }

    public List<MoneyLedger> findByDateAndType(String date, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {date, type};
        Cursor cursor = db.rawQuery("select * from " + MoneyLedger.TBL_NAME + " where "
                        + MoneyLedger.DATE_FIELD + " = ? and " + MoneyLedger.TYPE_FIELD + " = ?",
                args);
        List<MoneyLedger> moneyLedgers = new ArrayList<>();
        while (cursor.moveToNext())
            moneyLedgers.add(mapCursor(cursor));
        cursor.close();
        return moneyLedgers;
    }

    public MoneyLedger save(MoneyLedger moneyLedger) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoneyLedger.TITLE_FIELD, moneyLedger.getTitle());
        contentValues.put(MoneyLedger.AMOUNT_FIELD, moneyLedger.getAmount());
        contentValues.put(MoneyLedger.DATE_FIELD, moneyLedger.getDate());
        contentValues.put(MoneyLedger.TYPE_FIELD, moneyLedger.getType());

        if (moneyLedger.getId() == null) {
            moneyLedger.setId(db.insert(MoneyLedger.TBL_NAME, null, contentValues));
        } else {
            String[] whereArgs = {moneyLedger.getId().toString()};
            db.update(MoneyLedger.TBL_NAME, contentValues, MoneyLedger.ID_FIELD + " = ?", whereArgs);
        }
        return moneyLedger;
    }
}
