package com.example.user.my_medicine_separatisti.mData;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.my_medicine_separatisti.mData.Model.Frequency;
import com.example.user.my_medicine_separatisti.mData.Model.Medicine;
import com.example.user.my_medicine_separatisti.mData.Model.Reminder;
import com.example.user.my_medicine_separatisti.mData.Model.Schedule;
import com.example.user.my_medicine_separatisti.mData.Model.Treatment;

public class DataBaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    Context mContext;

    // Database Name
    private static final String DATABASE_NAME = "MyMed_db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            // create Medicine table
            db.execSQL(Medicine.CREATE_TABLE);
            // create Schedule table
            db.execSQL(Schedule.CREATE_TABLE);
            // create Frequency table
            db.execSQL(Frequency.CREATE_TABLE);
            // create Treatment table
            db.execSQL(Treatment.CREATE_TABLE);
            // create Reminder table
            db.execSQL(Reminder.CREATE_TABLE);
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        // insert in frequency

        // insert in state

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Medicine.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Schedule.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Frequency.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Treatment.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Reminder.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }}
