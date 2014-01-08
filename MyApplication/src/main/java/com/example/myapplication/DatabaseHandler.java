package com.example.myapplication;
/**
 * Created by Yaroslav on 05.01.14.
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "App_database";
    // Tables name
    public static final String TABLE_TIMETABLE = "timetable";
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_SUBJECTS = "subjects";
    // Tables Columns names
    // Tasks Table
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_SUBMIT_DATE = "submit_date";
    public static final String KEY_COMMENT = "comment";
    // Subjects Table
    /*private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";*/
    public static final String KEY_LECTOR = "lector";
    public static final String KEY_LECTOR_CONTACTS = "lector_contacts";
    // Timetable table
    /*private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "name";
    private static final String KEY_SUBJECT = "name";*/
    public static final String KEY_PLACE = "place";
    public static final String KEY_DAY = "day";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_WEEK = "week";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TIMETABLE_TABLE = "CREATE TABLE " + TABLE_TIMETABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_PLACE + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_SUBJECT + " TEXT,"
                + KEY_DAY + " TEXT,"
                + KEY_START_TIME + " TIME,"
                + KEY_END_TIME + " TIME,"
                + KEY_WEEK + " TEXT" + ")";

        db.execSQL(CREATE_TIMETABLE_TABLE);

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_SUBJECT + " TEXT,"
                + KEY_SUBMIT_DATE + " DATE,"
                + KEY_COMMENT + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);

        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_LECTOR + " TEXT,"
                + KEY_LECTOR_CONTACTS + " TEXT"+ ")";
        db.execSQL(CREATE_SUBJECTS_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

//    // Adding new contact
//    void addContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName()); // Contact Name
//        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
//
//        // Inserting Row
//        db.insert(TABLE_CONTACTS, null, values);
//        db.close(); // Closing database connection
//    }
//
//    // Getting single contact
//    Contact getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
//                KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return contact
//        return contact;
//    }
//
//    // Getting All Contacts
//    public List<Contact> getAllContacts() {
//        List<Contact> contactList = new ArrayList<Contact>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Contact contact = new Contact();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
//                contact.setName(cursor.getString(1));
//                contact.setPhoneNumber(cursor.getString(2));
//                // Adding contact to list
//                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }
//
//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }
//
//
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }

}