package com.example.covidapp.HealthAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.covidapp.R;

import java.util.Calendar;
import java.util.Date;

public class AdminAddAvailableTimes extends AppCompatActivity {

    Button buttonAddTimes;
    TimePicker timePicker;
    DatePicker datePicker;
    ListView listView;
    int day,month,year,minute,hour;
    Calendar date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_available_times);
        buttonAddTimes = findViewById(R.id.buttonAddTime);
        timePicker = findViewById(R.id.datePickerAdminAddTimes);
        datePicker = findViewById(R.id.calendarViewChooseDate);
        listView = findViewById(R.id.listviewAdminAddedTimes);

        buttonAddTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();
                minute = timePicker.getMinute();
                hour = timePicker.getHour();
                date = Calendar.getInstance();
                date.set(year,month,day,hour,minute);

            }
        });
    }
}