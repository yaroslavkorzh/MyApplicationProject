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

    public static final String DB_NAME = "App_database";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "timetable";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_PLACE = "place";
    public static final String KEY_DAY = "day";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_WEEK = "week";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NAME + " TEXT,"
                    + KEY_PLACE + " TEXT,"
                    + KEY_TYPE + " TEXT,"
                    + KEY_SUBJECT + " TEXT,"
                    + KEY_DAY + " TEXT,"
                    + KEY_START_TIME + " TIME,"
                    + KEY_END_TIME + " TIME,"
                    + KEY_WEEK + " TEXT" + ")";

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
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
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
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv.put(KEY_NAME, "sometext " + i);
                cv.put(KEY_PLACE, "sometext " + i);
                cv.put(KEY_TYPE, "sometext " + i);
                cv.put(KEY_SUBJECT, "sometext " + i);
                cv.put(KEY_DAY, "sometext " + i);
                cv.put(KEY_START_TIME, "sometext " + i);
                cv.put(KEY_END_TIME, "sometext " + i);
                cv.put(KEY_WEEK, "sometext " + i);
                db.insert(DB_TABLE, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}