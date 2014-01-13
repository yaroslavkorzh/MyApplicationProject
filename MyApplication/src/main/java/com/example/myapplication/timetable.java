package com.example.myapplication;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import java.util.concurrent.TimeUnit;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
//import android.widget.SimpleCursorAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class timetable extends FragmentActivity implements LoaderCallbacks<Cursor> {
    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    DB db;
    final String LOG_TAG = "myLogs";
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
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
            db.open();
            Cursor cursor = db.getAllData();
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }
}



