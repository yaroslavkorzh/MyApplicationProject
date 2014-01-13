package com.example.myapplication;
/**
 * Created by Yaroslav on 03.01.14.
 */
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.myapplication.import_parser;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class import_timetable extends Activity  {
    final String LOG_TAG = "myLogs";
    private static final String DATABASE_NAME = "App_database";
    public static final String TABLE_NAME = "timetable";
    // Tables name
    public static final String TABLE_TIMETABLE = "timetable";
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_SUBJECTS = "subjects";

    private static final String KEY_ID = "_id";
    private static final String KEY_PLACE = "place";
    private static final String KEY_DAY = "day";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_WEEK = "week";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_SUBMIT_DATE = "submit_date";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_LECTOR = "lector";
    public static final String KEY_LECTOR_CONTACTS = "lector_contacts";
    /** Called when the activity is first created. */
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_timetable);

        String tmp = "";
        // Добавляем записи в таблицу
        ContentValues cv = new ContentValues();
//        // подключаемся к БД
        // Инициализируем наш класс-обёртку
        DatabaseHandler dbh = new DatabaseHandler(this);
        // База нам нужна для записи и чтения
        SQLiteDatabase db = dbh.getWritableDatabase();

        try {
            XmlPullParser xpp = prepareXpp();
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                + ", depth = " + xpp.getDepth() + ", attrCount = "
                                + xpp.getAttributeCount());
                        tmp = "";
                        if (xpp.getName().equals("record")) {
                                // Тег Record найден, теперь получим его атрибуты и
                                // вставляем в таблицу
                                String name = xpp.getAttributeValue(0);
                                String type = xpp.getAttributeValue(1);
                                String subject = xpp.getAttributeValue(2);
                                String place = xpp.getAttributeValue(3);
                                String day = xpp.getAttributeValue(4);
                                String start_time = xpp.getAttributeValue(5);
                                String end_time = xpp.getAttributeValue(6);
                                String week = xpp.getAttributeValue(7);
                                cv = new ContentValues();
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
                                db.insert(TABLE_TIMETABLE, null, cv);
                        }
                        if (xpp.getName().equals("task")) {
                            // Тег task найден, теперь получим его атрибуты и
                            // вставляем в таблицу
                            String name = xpp.getAttributeValue(0);
                            String type = xpp.getAttributeValue(1);
                            String subject = xpp.getAttributeValue(2);
                            String submit_date = xpp.getAttributeValue(3);
                            String comment = xpp.getAttributeValue(4);
                            cv = new ContentValues();
                            cv.put(KEY_NAME, name );
                            cv.put(KEY_TYPE, type);
                            cv.put(KEY_SUBJECT, subject);
                            cv.put(KEY_SUBMIT_DATE, submit_date);
                            cv.put(KEY_COMMENT, comment);



                            Log.d(LOG_TAG,", name = " + name +
                                    ", type = " + type +
                                    ", subject = " + subject +
                                    ", submit_date = " + submit_date +
                                    ", comment = " + comment  );
                            db.insert(TABLE_TASKS, null, cv);

                        }
                        if (xpp.getName().equals("subject")) {
                            // Тег subject найден, теперь получим его атрибуты и
                            // вставляем в таблицу
                            String name = xpp.getAttributeValue(0);
                            String lector = xpp.getAttributeValue(1);
                            String lector_contacts = xpp.getAttributeValue(2);
                            cv = new ContentValues();
                            cv.put(KEY_NAME, name );
                            cv.put(KEY_LECTOR, lector);
                            cv.put(KEY_LECTOR_CONTACTS, lector_contacts);

                            Log.d(LOG_TAG,", name = " + name +
                                    ", type = " + lector +
                                    ", subject = " + lector_contacts );
                            db.insert(TABLE_SUBJECTS, null, cv);
                        }

                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            tmp = tmp + xpp.getAttributeName(i) + " = "
                                    + xpp.getAttributeValue(i) + ", ";
                        }
                        if (!TextUtils.isEmpty(tmp))
                            Log.d(LOG_TAG, "Attributes: " + tmp);
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // закрываем подключение к БД
        // закрываем соединения с базой данных
        db.close();
        dbh.close();
    }

    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода

//        // подключаемся к БД
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Инициализируем наш класс-обёртку
        DatabaseHandler dbh = new DatabaseHandler(this);
        // База нам нужна для записи и чтения
        SQLiteDatabase db = dbh.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnImport:
                Log.d(LOG_TAG, "--- Insert in timetable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                // вставляем запись и получаем ее ID
                long rowID = db.insert("timetable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in timetable: ---");
                // делаем запрос всех данных из таблицы timetable, получаем Cursor
                Cursor c = db.query("timetable", null, null, null, null, null, null);
                Log.d(LOG_TAG, "--- Rows in timetable: ---");
                // делаем запрос всех данных из таблицы timetable, получаем Cursor
                Cursor b = db.query("tasks", null, null, null, null, null, null);
                Log.d(LOG_TAG, "--- Rows in timetable: ---");
                // делаем запрос всех данных из таблицы timetable, получаем Cursor
                Cursor e = db.query("subjects", null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("_id");
                    int nameColIndex = c.getColumnIndex("name");
                    int typeColIndex = c.getColumnIndex("type");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", type = " + c.getString(typeColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                if (b.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = b.getColumnIndex("_id");
                    int nameColIndex = b.getColumnIndex("name");
                    int typeColIndex = b.getColumnIndex("type");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + b.getInt(idColIndex) +
                                        ", name = " + b.getString(nameColIndex) +
                                        ", type = " + b.getString(typeColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (b.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                b.close();
                if (e.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = e.getColumnIndex("_id");
                    int nameColIndex = e.getColumnIndex("name");


                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + e.getInt(idColIndex) +
                                        ", name = " + e.getString(nameColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (e.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                e.close();
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("timetable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
        }
        // закрываем подключение к БД
        // закрываем соединения с базой данных
        db.close();
        dbh.close();
    }


    XmlPullParser prepareXpp() {
        return getResources().getXml(R.xml.new_timetable);
    }
//    public void onDestroy(){
//
//    }
}

