package com.example.myapplication;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.TabActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        TabHost.TabSpec tabSpec;
        // получаем TabHost
        TabHost tabHost = getTabHost();

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Домашняя страница");
        tabSpec.setContent(new Intent(this, homepage.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Расписание");
        tabSpec.setContent(new Intent(this, timetable.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        // создаем View из layout-файла
//        View v = getLayoutInflater().inflate(R.layout.tasks, null);
//        tabSpec.setIndicator(v);
        tabSpec.setIndicator("Задачи");
        tabSpec.setContent(new Intent(this, tasks.class));
        tabHost.addTab(tabSpec);

        // первая вкладка будет выбрана по умолчанию
        tabHost.setCurrentTabByTag("tag1");

        // обработчик переключения вкладок
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tabId) {
//                вывод сообщения при переключении вкладок
//                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super .onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, settings.class);
                startActivity(intent);
                return true;
            case R.id.import_timetable:
                Intent import_timetable = new Intent(MainActivity.this, import_timetable.class);
                startActivity(import_timetable);
                return true;
            case R.id.add_timetable_item:
                Intent add_timetable_item = new Intent(MainActivity.this, add_timetable_item.class);
                startActivity(add_timetable_item);
                return true;
            case R.id.add_tasks_item:
                Intent add_tasks_item = new Intent(MainActivity.this, add_tasks_item.class);
                startActivity(add_tasks_item);
                return true;
            case R.id.add_subjects_item:
                Intent add_subjects_item = new Intent(MainActivity.this, add_timetable_item.class);
                startActivity(add_subjects_item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}


