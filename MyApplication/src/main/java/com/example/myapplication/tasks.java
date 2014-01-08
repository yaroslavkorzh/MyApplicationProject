package com.example.myapplication;
/**
 * Created by Yaroslav on 03.01.14.
 */
import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class tasks extends Activity implements OnClickListener{
    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;



    /** Called when the activity is first created. */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);


        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);

//        // создаем объект для создания и управления версиями БД
//        dbHelper = new DBHelper(this);



    }
    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String type = etEmail.getText().toString();

//        // подключаемся к БД
//        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Инициализируем наш класс-обёртку
        DatabaseHandler dbh = new DatabaseHandler(this);

        // База нам нужна для записи и чтения
        SQLiteDatabase db = dbh.getWritableDatabase();




        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in timetable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("name", name);
                cv.put("type", type);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("timetable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in timetable: ---");
                // делаем запрос всех данных из таблицы timetable, получаем Cursor
                Cursor c = db.query("timetable", null, null, null, null, null, null);

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





}











