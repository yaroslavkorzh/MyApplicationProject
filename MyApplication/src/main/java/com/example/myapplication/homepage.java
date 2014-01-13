package com.example.myapplication;
/**
 * Created by Yaroslav on 03.01.14.
 */
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
import android.widget.Toast;
import java.util.Calendar;
import java.util.Locale;
import 	java.text.SimpleDateFormat;
import 	java.text.DateFormat;
//import android.widget.SimpleCursorAdapter;

public class homepage extends FragmentActivity implements LoaderCallbacks<Cursor> {
    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    DB db;
    final String LOG_TAG = "myLogs";
    Spinner spinner;
    String day;
    public static String[] namesOfDays =  {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        // открываем подключение к БД
        db = new DB(this);
        db.open();
        // формируем столбцы сопоставления
        String[] from = new String[] { DB.KEY_NAME, DB.KEY_PLACE, DB.KEY_SUBJECT, DB.KEY_START_TIME, DB.KEY_END_TIME, DB.KEY_DAY };
        int[] to = new int[] { R.id.lessonName, R.id.lessonAuditory, R.id.lessonSubject, R.id.lessonStart, R.id.lessonEnd, R.id.lessonDay};
    // создааем адаптер и настраиваем список
    scAdapter = new SimpleCursorAdapter(this, R.layout.timetable_item, null, from, to, 0);
    lvData = (ListView) findViewById(R.id.lvData);
    lvData.setAdapter(scAdapter);
    // добавляем контекстное меню к списку
    registerForContextMenu(lvData);
    // создаем лоадер для чтения данных
    getSupportLoaderManager().initLoader(0, null, this);

//        Date now = new Date();
//// EEE gives short day names, EEEE would be full length.
//        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
//        String asWeek = dateFormat.format(now);

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        System.out.println("Day := "+namesOfDays[day-1]);
        String curr_day = namesOfDays[day-1];
        Log.d(LOG_TAG, "Day := " + curr_day + " " + weekDay);


        spinner = (Spinner) findViewById(R.id.spinner);
        // Настраиваем адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(1);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + view, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        }

        );
}
    // обработка нажатия кнопки
    public void onButtonClick(View view) {
        // добавляем запись
        db.addRec("sometext " + (scAdapter.getCount() + 1), R.drawable.ic_launcher);
        // получаем новый курсор с данными
        getSupportLoaderManager().getLoader(0).forceLoad();
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
//    public static int getDayOfWeek(int year, int, month, int day) {
//        Calendar c = Calendar.getInstance();
//        c.set(year, month, day);
//        int dow = c.get(Calendar.DAY_OF_WEEK);
//        return dow;
//    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DB db;
        public static String[] namesOfDays =  {"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }
        public static int getDayOfWeek(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        int dow = c.get(Calendar.DAY_OF_WEEK);
        return dow;
        }
        @Override
        public Cursor loadInBackground() {
            // открываем подключение к БД
            db.open();
            int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            System.out.println("Day := "+namesOfDays[day-1]);
            String curr_day = namesOfDays[day-1];

//            String day = "Вторник";
            Cursor cursor = db.getByDay(curr_day);
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return cursor;
        }

    }
}


















