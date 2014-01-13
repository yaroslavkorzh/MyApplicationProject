package com.example.myapplication;
/**
 * Created by Yaroslav on 06.01.14.
 */
        import android.app.Activity;
        import android.app.Dialog;
        import android.app.TimePickerDialog;
        import android.app.TimePickerDialog.OnTimeSetListener;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.Spinner;

public class add_timetable_item extends Activity implements TextWatcher {

    int START_TIME = 1;
    int END_TIME = 2;
    int myHour = 14;
    int myMinute = 35;
    TextView tvTime, tvStartTime, tvEndTime;

    TextView mText;
    AutoCompleteTextView mAutoComplete;
    final String[] mContacts = {
            "Jacob Anderson", "Emily Duncan", "Michael Fuller",
            "Emma Greenman", "Joshua Harrison", "Madison Johnson",
            "Matthew Cotman", "Olivia Lawson", "Andrew Chapman",
            "Michael Honeyman", "Isabella Jackson", "William Patterson",
            "Joseph Godwin", "Samantha Bush", "Christopher Gateman"};



    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_timetable_item);

        tvTime = (TextView) findViewById(R.id.tvStartTime);
        add_timetable_item prevActivity = (add_timetable_item)getLastNonConfigurationInstance();
        if(prevActivity!= null) {
            // So the orientation did change
            // Restore some field for example
            this.tvStartTime = prevActivity.tvStartTime;
            this.tvEndTime = prevActivity.tvEndTime;
        }
        else {
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        }

        mText = (TextView)findViewById(R.id.text);
        mAutoComplete=(AutoCompleteTextView)findViewById(
                R.id.autoCompleteTextView);
        mAutoComplete.addTextChangedListener(this);
        mAutoComplete.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mContacts));




        // Получаем экземпляр элемента Spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Настраиваем адаптер
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(1);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
//                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                }

            }
        );
}



    public void onStartTimeClick(View view) {
        showDialog(START_TIME);
    }
    public void onEndTimeClick(View view) {
        showDialog(END_TIME);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == START_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, startTimeCallBack, myHour, myMinute, true);
            return tpd;
        }
        if (id == END_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, endTimeCallBack, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    OnTimeSetListener startTimeCallBack = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            tvStartTime.setText(myHour + ":" + myMinute );
        }
    };
    OnTimeSetListener endTimeCallBack = new OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            tvEndTime.setText(myHour + ":" + myMinute );
        }
    };


    public void onTextChanged(
            CharSequence s, int start, int before, int count) {
        mText.setText(mAutoComplete.getText());
    }

    public void beforeTextChanged(
            CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {

    }

    public Object onRetainNonConfigurationInstance() {
                return this;
    }


}