package com.example.myapplication;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Yaroslav on 05.01.14.
 */



public class import_parser extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";


    private final Context fContext;
    private static final String DATABASE_NAME = "App_database";
    public static final String TABLE_NAME = "timetable";

    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_PLACE = "place";
    private static final String KEY_DAY = "day";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_WEEK = "week";


    public import_parser(Context context) {
        super(context, DATABASE_NAME, null, 1);
        fContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Инициализируем наш класс-обёртку
        DatabaseHandler dbh = new DatabaseHandler(fContext);
        Log.d(LOG_TAG,"before creation" );
        // База нам нужна для записи и чтения
//        SQLiteDatabase db = dbh.getWritableDatabase();
        // TODO Auto-generated method stub
        String CREATE_TIMETABLE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
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
        Log.d(LOG_TAG,"after creation" );
        // Добавляем записи в таблицу
        ContentValues cv = new ContentValues();

        // Получим файл из ресурсов
        Resources res = fContext.getResources();

        // Открываем xml-файл
        XmlResourceParser _xml = res.getXml(R.xml.import_data);
        try {
            // Ищем конец документа
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Ищем теги record
                if ((eventType == XmlPullParser.START_TAG)
                        && (_xml.getName().equals("record"))) {
                    // Тег Record найден, теперь получим его атрибуты и
                    // вставляем в таблицу
                    String name = _xml.getAttributeValue(0);
                    String type = _xml.getAttributeValue(1);
                    String subject = _xml.getAttributeValue(2);
                    String place = _xml.getAttributeValue(3);
                    String day = _xml.getAttributeValue(4);
                    String start_time = _xml.getAttributeValue(5);
                    String end_time = _xml.getAttributeValue(6);
                    String week = _xml.getAttributeValue(7);

                    cv.put(KEY_NAME, name );
                    cv.put(KEY_PLACE, type);
                    cv.put(KEY_TYPE, subject);
                    cv.put(KEY_SUBJECT, place);
                    cv.put(KEY_DAY, day);
                    cv.put(KEY_START_TIME, start_time);
                    cv.put(KEY_END_TIME, end_time);
                    cv.put(KEY_WEEK, week);
                    Log.d(LOG_TAG,", name = " + name +
                                  ", type = " + type +
                            ", subject = " + subject +
                            ", place = " + place +
                            ", day = " + day +
                            ", start_time = " + start_time +
                            ", end_time = " + end_time +
                            ", week = " + week );
                   db.insert(TABLE_NAME, null, cv);
                }
                eventType = _xml.next();
            }
        }
        // Catch errors
        catch (XmlPullParserException e) {
            Log.e("Test", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("Test", e.getMessage(), e);

        } finally {
            // Close the xml file
            _xml.close();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.w("TestBase", "Upgrading database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

