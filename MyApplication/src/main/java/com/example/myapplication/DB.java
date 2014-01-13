package com.example.myapplication;

/**
 * Created by Yaroslav on 08.01.14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {
    // переменные для query
    String[] columns = null;
    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;

    public static final String DB_NAME = "App_database";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "timetable";

//    public static final String KEY_ID = "_id";
//    public static final String KEY_NAME = "name";
//    public static final String KEY_TYPE = "type";
//    public static final String KEY_SUBJECT = "subject";
//    public static final String KEY_PLACE = "place";
//    public static final String KEY_DAY = "day";
//    public static final String KEY_START_TIME = "start_time";
//    public static final String KEY_END_TIME = "end_time";
//    public static final String KEY_WEEK = "week";
//    public static final String KEY_SUBMIT_DATE = "submit_date";
//    public static final String KEY_COMMENT = "comment";

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


    private static final String DB_CREATE =
            "create table " + DB_TABLE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_NAME + " TEXT,"
                    + KEY_PLACE + " TEXT,"
                    + KEY_TYPE + " TEXT,"
                    + KEY_SUBJECT + " TEXT,"
                    + KEY_DAY + " TEXT,"
                    + KEY_START_TIME + " TIME,"
                    + KEY_END_TIME + " TIME,"
                    + KEY_WEEK + " TEXT" + ")";

    private static final String CREATE_TIMETABLE_TABLE = "CREATE TABLE " + TABLE_TIMETABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY ,"
            + KEY_NAME + " TEXT,"
            + KEY_PLACE + " TEXT,"
            + KEY_TYPE + " TEXT,"
            + KEY_SUBJECT + " TEXT,"
            + KEY_DAY + " TEXT,"
            + KEY_START_TIME + " TIME,"
            + KEY_END_TIME + " TIME,"
            + KEY_WEEK + " TEXT" + ")";

    private static final  String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
            + KEY_ID + " INTEGER PRIMARY KEY ,"
            + KEY_NAME + " TEXT,"
            + KEY_TYPE + " TEXT,"
            + KEY_SUBJECT + " TEXT,"
            + KEY_SUBMIT_DATE + " DATE,"
            + KEY_COMMENT + " TEXT" + ")";

    private static final String  CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY ,"
            + KEY_NAME + " TEXT,"
            + KEY_LECTOR + " TEXT,"
            + KEY_LECTOR_CONTACTS + " TEXT"+ ")";


    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        orderBy = "day";// немного сортировки, но увы по алфавиту
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getByDay(String sDay) {
        selection = "day == ?";
        selectionArgs = new String[] { sDay };
        return mDB.query(DB_TABLE, null, selection, selectionArgs, null, null, null);
    }
    public Cursor getTasks() {
        return mDB.query(TABLE_TASKS, null, null, null, null, null, null);
    }
    // добавить запись в DB_TABLE
    public void addRec(String txt, int img) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, "sometext " + 0);
        cv.put(KEY_PLACE, "sometext " + 0);
        cv.put(KEY_TYPE, "sometext " + 0);
        cv.put(KEY_SUBJECT, "sometext " + 0);
        cv.put(KEY_DAY, "sometext " + 0);
        cv.put(KEY_START_TIME, "sometext " + 0);
        cv.put(KEY_END_TIME, "sometext " + 0);
        cv.put(KEY_WEEK, "sometext " + 0);
        mDB.insert(DB_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, KEY_ID + " = " + id, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(DB_CREATE);
            db.execSQL(CREATE_TIMETABLE_TABLE);
            db.execSQL(CREATE_TASKS_TABLE);
            db.execSQL(CREATE_SUBJECTS_TABLE);
            ContentValues cv = new ContentValues();

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}