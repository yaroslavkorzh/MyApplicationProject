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
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.concurrent.TimeUnit;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
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
import android.widget.RadioGroup;
//import android.widget.SimpleCursorAdapter

public class tasks extends FragmentActivity implements LoaderCallbacks<Cursor> {
    private static final int CM_DELETE_ID = 1;
    ListView lvTasks;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    DB db;
    final String LOG_TAG = "myLogs";
    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;



    /** Called when the activity is first created. */
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

//        btnAdd = (Button) findViewById(R.id.btnAdd);
//        btnAdd.setOnClickListener(this);
//
//        btnRead = (Button) findViewById(R.id.btnRead);
//        btnRead.setOnClickListener(this);
//
//        btnClear = (Button) findViewById(R.id.btnClear);
//        btnClear.setOnClickListener(this);
//
//        etName = (EditText) findViewById(R.id.etName);
//        etEmail = (EditText) findViewById(R.id.etEmail);

        // открываем подключение к БД
        db = new DB(this);
        db.open();
        // формируем столбцы сопоставления
        String[] from = new String[] { DB.KEY_NAME, DB.KEY_TYPE, DB.KEY_SUBJECT, DB.KEY_SUBMIT_DATE, DB.KEY_COMMENT};
        int[] to = new int[] { R.id.taskName, R.id.taskType, R.id.taskSubject, R.id.taskDueDate, R.id.taskComment};
        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.tasks_item, null, from, to, 0);
        lvTasks = (ListView) findViewById(R.id.lvTasks);
        lvTasks.setAdapter(scAdapter);
        // добавляем контекстное меню к списку
        registerForContextMenu(lvTasks);
        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);


    }

    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String type = etEmail.getText().toString();

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

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            // получаем новый курсор с данными
            getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onResume() {
        // получаем новый курсор с данными
        getSupportLoaderManager().getLoader(0).forceLoad();
        super.onResume();
        Log.d(LOG_TAG, "MainActivity: onResume()");
    }
    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            // открываем подключение к БД
            db.open();
            Cursor cursor = db.getTasks();
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return cursor;
        }

    }





}











