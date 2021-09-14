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

    Spinner spinnerage, spinnerDoses, spinnerConties;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity_deaths_and_cases);
        //Declaration of spinners
        spinnerage = findViewById(R.id.spinnerAge);
        spinnerDoses = findViewById(R.id.SpinnerDoses);
        spinnerConties = findViewById(R.id.SpinnerCounty);


        //Demo items for spinners
        String[] ages = new String[]{"All","1-19","20-40","40-60"};
        String[] Counties = new String[]{"All","Örebro","Värmland","Stockholm"};
        String[] Doses = new String[]{"None","1","2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ages);
        ArrayAdapter<String> adapterCounites = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Counties);
        ArrayAdapter<String> adapterDoses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Doses);

        spinnerage.setAdapter(adapter);
        spinnerConties.setAdapter(adapterCounites);
        spinnerDoses.setAdapter(adapterDoses);



        //Intent intent = new Intent(getBaseContext(), );


        //spinner on select listener
        spinnerage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                //just to make sure the listener works
                Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //

    }


}