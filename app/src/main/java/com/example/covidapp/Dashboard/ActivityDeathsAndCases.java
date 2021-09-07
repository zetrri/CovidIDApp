package com.example.covidapp.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.covidapp.R;

public class ActivityDeathsAndCases extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deaths_and_cases);
        Spinner spinner = findViewById(R.id.spinnerAge);
        Spinner spinnerDoses = findViewById(R.id.SpinnerDoses);
        Spinner spinnerConties = findViewById(R.id.SpinnerCounty);
        Spinner spinnermode = findViewById(R.id.Spinnermode);
        String[] items = new String[]{"All","1-19","20-40","40-60"};
        String[] Counties = new String[]{"All","Örebro","Värmland","Stockholm"};
        String[] Doses = new String[]{"None","1","2"};
        String[] mode = new String[]{"Both","Cases","Deaths"};
        //hello
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> adapterCounites = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Counties);
        ArrayAdapter<String> adapterDoses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Doses);
        ArrayAdapter<String> adaptermode = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mode);
        spinner.setAdapter(adapter);
        spinnerConties.setAdapter(adapterCounites);
        spinnerDoses.setAdapter(adapterDoses);
        spinnermode.setAdapter(adaptermode);
    }
}