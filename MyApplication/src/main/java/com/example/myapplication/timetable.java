package com.example.myapplication;
import android.os.Bundle;
import android.app.Activity;
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

public class timetable extends  Activity {
    String[] name = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь" };
    String[] position = { "Программер", "Бухгалтер", "Программер",
            "Программер", "Бухгалтер", "Директор", "Программер", "Охранник" };
    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000 };

    int[] colors = new int[2];


    // названия компаний (групп)
    String[] groups = new String[] {"HTC", "Samsung", "LG"};

    // названия телефонов (элементов)
    String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    // коллекция для групп
    ArrayList<Map<String, String>> groupData;

    // коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список аттрибутов группы или элемента
    Map<String, String> m;

    ExpandableListView expandableListView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);



        // заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            // заполняем список аттрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {"groupName"};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};


        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список аттрибутов для каждого элемента
        for (String phone : phonesHTC) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : phonesSams) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // создаем коллекцию элементов для третьей группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : phonesLG) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);



        }
        childData.add(childDataItem);

        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {"phoneName"};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setAdapter(adapter);



        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);

        LayoutInflater ltInflater = getLayoutInflater();

        for (int i = 0; i < name.length; i++) {
            Log.d("myLogs", "i = " + i);
            View item = ltInflater.inflate(R.layout.timetable_item, linLayout, false);
            TextView tvName = (TextView) item.findViewById(R.id.lessonName);
            tvName.setText(name[i]);
            TextView tvPosition = (TextView) item.findViewById(R.id.lessonSubject);
            tvPosition.setText("Должность: " + position[i]);
            TextView tvSalary = (TextView) item.findViewById(R.id.lessonAuditory);
            tvSalary.setText("Оклад: " + String.valueOf(salary[i]));
            item.getLayoutParams().width = LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);
            linLayout.addView(item);
        }




    }

}



