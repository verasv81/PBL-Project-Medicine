package com.example.user.my_medicine_separatisti.mAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.user.my_medicine_separatisti.mData.DataBaseHelper;
import com.example.user.my_medicine_separatisti.mData.Model.Frequency;
import com.example.user.my_medicine_separatisti.mData.Model.Medicine;
import com.example.user.my_medicine_separatisti.mData.Model.Reminder;
import com.example.user.my_medicine_separatisti.mData.Model.Schedule;
import com.example.user.my_medicine_separatisti.mData.Model.Treatment;
import com.example.user.my_medicine_separatisti.mData.Model.TreatmentState;

public class DataBaseAdapter {
    Context c;
    SQLiteDatabase db;
    DataBaseHelper helper;
    // Logcat tag
    private static final String LOG = "DataBaseHelper";

    public DataBaseAdapter(Context c) {
        this.c = c;
        helper = new DataBaseHelper(c);
    }

    //--------------------------------------Medicines---------------------------

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = Medicine.COLUMN_MEDICINE_NAME + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Medicine.TABLE_NAME);

        Cursor cursor = builder.query(helper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    //insert a Medicine
    public boolean insertMedicine(Medicine med){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Medicine.COLUMN_MEDICINE_NAME, med.getName());
        values.put(Medicine.COLUMN_MEDICINE_EXPIRATION_DATE, med.getExpiration_date());
        values.put(Medicine.COLUMN_MEDICINE_QUANTITY, med.getQuantity());
        values.put(Medicine.COLUMN_MEDICINE_COMMENTS, med.getComments());

        // insert row
        long id = db.insert(Medicine.TABLE_NAME, null, values);

        if(id==-1) return false;
        else return true;
    }

    //delete medicine
    public void deleteMedicine(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Medicine.TABLE_NAME, Medicine._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //get one medicine
    public Medicine getMedicine(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Medicine.TABLE_NAME + " WHERE "
                + Medicine._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Medicine medicine = new Medicine();
        medicine.setId(c.getInt(c.getColumnIndex(Medicine._ID)));
        medicine.setName((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_NAME))));
        medicine.setExpiration_date((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_EXPIRATION_DATE))));
        medicine.setQuantity((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_QUANTITY))));
        medicine.setComments((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_COMMENTS))));

        return medicine;
    }
    //get all medicines
    public Cursor getAllMedicines(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ Medicine.TABLE_NAME,null);
        return c;
    }

    public int getNofMedicines() {
        SQLiteDatabase db = helper.getWritableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,Medicine.TABLE_NAME);
    }

    public int updateMedicines(Medicine medicine) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Medicine.COLUMN_MEDICINE_NAME, medicine.getName());
        values.put(Medicine.COLUMN_MEDICINE_EXPIRATION_DATE,medicine.getExpiration_date());
        values.put(Medicine.COLUMN_MEDICINE_QUANTITY,medicine.getQuantity());
        values.put(Medicine.COLUMN_MEDICINE_COMMENTS,medicine.getComments());

        // updating row
        return db.update(Medicine.TABLE_NAME, values, Medicine._ID+ " = ?",
                new String[]{String.valueOf(medicine.getId())});
    }

    //--------------------------------------Frequency--------------------------
    //insert frequency
    public boolean insertFrequency(Frequency frequency){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Frequency.COLUMN_FREQUENCY, frequency.getName());

        // insert row
        long id = db.insert(Frequency.TABLE_NAME, null, values);

        if(id==-1) return false;
        else return true;
    }

    //delete frequency
    public void deleteFrequency(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Frequency.TABLE_NAME, Frequency._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select frequency
    public Frequency getFrequency(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Frequency.TABLE_NAME + " WHERE "
                + Frequency._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Frequency frequency=new Frequency();
        frequency.setId(c.getInt(c.getColumnIndex(Frequency._ID)));
        frequency.setName((c.getString(c.getColumnIndex(Frequency.COLUMN_FREQUENCY))));

        return frequency;
    }

    //get all frequencies
    public Cursor getAllFrequencies(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ Frequency.TABLE_NAME,null);
        return c;
    }

    public int updateFrequency(Frequency frequency) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Frequency.COLUMN_FREQUENCY, frequency.getName());

        // updating row
        return db.update(Frequency.TABLE_NAME, values, Frequency._ID+ " = ?",
                new String[]{String.valueOf(frequency.getId())});
    }

    //--------------------------------------Treatment---------------------------
    //insert Treatment
    public boolean insertTreatment(Treatment treatment){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Treatment.COLUMN_TREATMENT_NAME,treatment.getName());
        values.put(Treatment.COLUMN_TREATMENT_START_DATE,treatment.getStart_date());
        values.put(Treatment.COLUMN_TREATMENT_END_DATE,treatment.getEnd_date());
        values.put(Treatment.COLUMN_TREATMENT_STATE_ID,treatment.getState_id());
        values.put(Treatment.COLUMN_TREATMENT_DESCRIPTION,treatment.getDescription());
        // insert row
        long id = db.insert(Treatment.TABLE_NAME, null, values);

        if(id==-1) return false;
        else return true;
    }

    //delete treatment
    public void deleteTreatment(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Treatment.TABLE_NAME, Treatment._ID + " = ?",
                new String[] { String.valueOf(id) });
    }


    //select treatment
    public Treatment getTreatment(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Treatment.TABLE_NAME + " WHERE "
                + Treatment._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Treatment treatment=new Treatment();
        treatment.setId(c.getInt(c.getColumnIndex(Treatment._ID)));
        treatment.setName((c.getString(c.getColumnIndex(Treatment.COLUMN_TREATMENT_NAME))));
        treatment.setStart_date((c.getString(c.getColumnIndex(Treatment.COLUMN_TREATMENT_START_DATE))));
        treatment.setEnd_date((c.getString(c.getColumnIndex(Treatment.COLUMN_TREATMENT_END_DATE))));
        treatment.setState_id((c.getString(c.getColumnIndex(Treatment.COLUMN_TREATMENT_STATE_ID))));
        treatment.setDescription((c.getString(c.getColumnIndex(Treatment.COLUMN_TREATMENT_DESCRIPTION))));

        return treatment;
    }

    public Cursor getAllTreatments(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ Treatment.TABLE_NAME,null);
        return c;
    }

    public int updateTreatment(Treatment treatment) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Treatment.COLUMN_TREATMENT_NAME, treatment.getName());
        values.put(Treatment.COLUMN_TREATMENT_START_DATE,treatment.getStart_date());
        values.put(Treatment.COLUMN_TREATMENT_END_DATE,treatment.getEnd_date());
        values.put(Treatment.COLUMN_TREATMENT_STATE_ID,treatment.getState_id());
        values.put(Treatment.COLUMN_TREATMENT_DESCRIPTION,treatment.getDescription());

        // updating row
        return db.update(Treatment.TABLE_NAME, values, Treatment._ID+ " = ?",
                new String[]{String.valueOf(treatment.getId())});
    }

    //--------------------------------------STATE OF TREATMENT---------------------------
    //insert state
    public boolean insertState(TreatmentState treatmentState){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TreatmentState.COLUMN_STATE_NAME,treatmentState.getName());
        // insert row
        long id = db.insert(TreatmentState.TABLE_NAME, null, values);

        if(id==-1) return false;
        else return true;
    }

    //delete State
    public void deleteState(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TreatmentState.TABLE_NAME, TreatmentState._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select state
    public TreatmentState getStates(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TreatmentState.TABLE_NAME + " WHERE "
                + TreatmentState._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        TreatmentState state=new TreatmentState();
        state.setId(c.getInt(c.getColumnIndex(TreatmentState._ID)));
        state.setName((c.getString(c.getColumnIndex(TreatmentState.COLUMN_STATE_NAME))));

        return state;
    }

    //get all states
    public Cursor getAllStates(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ TreatmentState.TABLE_NAME,null);
        return c;
    }

    public int updateState(TreatmentState state) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TreatmentState.COLUMN_STATE_NAME, state.getName());

        // updating row
        return db.update(TreatmentState.TABLE_NAME, values, TreatmentState._ID+ " = ?",
                new String[]{String.valueOf(state.getId())});
    }

    //--------------------------------------SCHEDULE---------------------------
    //insert schedule
    public boolean insertSchedule(Schedule schedule){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Schedule.COLUMN_HOUR,schedule.getHour());

        // insert row
        long id = db.insert(Schedule.TABLE_NAME, null, values);

        if(id==-1) return false;
        else return true;
    }

    //delete schedule
    public void deleteSchedule(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Schedule.TABLE_NAME, Schedule._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select schedule
    public Schedule getSchedule(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Schedule.TABLE_NAME + " WHERE "
                + Schedule._ID + " = " + id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Schedule schedule=new Schedule();
        schedule.setId(c.getInt(c.getColumnIndex(Schedule._ID)));
        schedule.setHour((c.getString(c.getColumnIndex(Schedule.COLUMN_HOUR))));

        return schedule;
    }

    //get all schedules
    public Cursor getAllSchedule(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ Schedule.TABLE_NAME,null);
        return c;
    }

    public int updateSchedule(Schedule schedule) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Schedule.COLUMN_HOUR, schedule.getHour());

        // updating row
        return db.update(Schedule.TABLE_NAME, values, Schedule._ID+ " = ?",
                new String[]{String.valueOf(schedule.getId())});
    }

    //--------------------------------------REMINDER---------------------------
    //set reminder
    public boolean setReminder(Reminder reminder){
        db = helper.getWritableDatabase();// get writable database as we want to write data

        ContentValues values = new ContentValues();
        values.put(Reminder.COLUMN_TREATMENT_ID,reminder.getTreatment_id());
        values.put(Reminder.COLUMN_MEDICINE_ID,reminder.getMedicine_id());
        values.put(Reminder.COLUMN_FREQUENCY_ID,reminder.getFrequency_id());
        values.put(Reminder.COLUMN_SCHEDULE_ID,reminder.getSchedule_id());
        values.put(Reminder.COLUMN_TIMES,reminder.getTimes());

        // insert row
        long id = db.insert(Reminder.TABLE_NAME, null, values);

        if(id==-1) return false;
        else return true;
    }

    //delete reminder
    public void deleteReminder(long id) {
        db = helper.getWritableDatabase();
        db.delete(Reminder.TABLE_NAME, Reminder.COLUMN_MEDICINE_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select reminder
    public Reminder getReminder(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Reminder.TABLE_NAME + " WHERE "
                + Reminder.COLUMN_MEDICINE_ID+ " = " + id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Reminder reminder=new Reminder();
        reminder.setMedicine_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_MEDICINE_ID))));
        reminder.setTreatment_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_TREATMENT_ID))));
        reminder.setFrequency_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_FREQUENCY_ID))));
        reminder.setSchedule_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_SCHEDULE_ID))));
        reminder.setTimes((c.getInt(c.getColumnIndex(Reminder.COLUMN_TIMES))));

        return reminder;
    }

    //get all reminders
    public Cursor getAllReminder(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ Reminder.TABLE_NAME,null);
        return c;
    }

    public int updateReminder(Reminder reminder) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reminder.COLUMN_TREATMENT_ID, reminder.getTreatment_id());
        values.put(Reminder.COLUMN_MEDICINE_ID, reminder.getMedicine_id());
        values.put(Reminder.COLUMN_SCHEDULE_ID, reminder.getSchedule_id());
        values.put(Reminder.COLUMN_FREQUENCY_ID, reminder.getFrequency_id());
        values.put(Reminder.COLUMN_TIMES, reminder.getTimes());


        // updating row
        return db.update(Reminder.TABLE_NAME, values, Reminder.COLUMN_MEDICINE_ID+ " = ?",
                new String[]{String.valueOf(reminder.getMedicine_id())});
    }

}
