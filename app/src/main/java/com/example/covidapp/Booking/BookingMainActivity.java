package com.example.covidapp.Booking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.covidapp.MyPage.MainMyPage;
import com.example.covidapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingMainActivity extends AppCompatActivity {
//Bois
    BookingMainActivity this_obj = this;
    private Booking booking = new Booking("", "", "", "", "", "", "", "", "", "", "");
    private String[] cities;
    private String[] clinics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_main);
        // Kod för dom olika dropdown menyerna över län, stad och klinik
        Spinner county_dropdown = findViewById(R.id.dropdown_county);
        String[] counties = new String[] {"Värmland", "Västra Götaland"}; // TODO kommer hämtas från databas
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, counties);
        county_dropdown.setAdapter(adapter1);

        //***************** Exempel på att rätt län har rätt städer ****************
        // Kommer ändras när vi implementerar databaser
        county_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                booking.county = (String) adapterView.getItemAtPosition(position); // sparar län
                //TODO jämför med databas och hämta rätt städer
                if(booking.county == "Västra Götaland"){
                    cities =  new String[] {"Göteborg", "Alingsås"};
                }else{
                    cities = new String[] {"Karlstad"};
                }
                Spinner city_dropdown = findViewById(R.id.dropdown_city);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, cities);
                city_dropdown.setAdapter(adapter2);

                city_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        booking.city = (String) adapterView.getItemAtPosition(position); // sparar stad
                        //TODO jämför med databas
                        if(booking.city.equals("Karlstad")){
                            clinics =  new String[] {"Gripen", "Kronoparken", "Rud"};
                        }else{
                            clinics = new String[] {"Sollebrunn", "Nolhaga"};
                        }
                        Spinner clinic_dropdown = findViewById(R.id.dropdown_clinic);
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, clinics);
                        clinic_dropdown.setAdapter(adapter3);

                        clinic_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                booking.clinic = (String) adapterView.getItemAtPosition(position); // sparar klinik
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //************************************************************************************

        //dropdown för att välja vaccin
        Spinner vaccine_dropdown = findViewById(R.id.dropdown_vaccine);
        String[] vaccines = new String[] {"Phizer", "Moderna", "NovaVax"}; //TODO hämta från databas
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vaccines);
        vaccine_dropdown.setAdapter(adapter4);

        //lyssna på dropdownen
        vaccine_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                booking.vaccine = vaccine_dropdown.getItemAtPosition(position).toString(); // sparar vaccinet valt
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        RadioGroup radioGroup = findViewById(R.id.radGroup1);

        // få ut datum från kalendern
        CalendarView Kalender = findViewById(R.id.calendarView);
        TextView error = findViewById(R.id.errormessage);
        Kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                booking.date = day + "/" + month + "-" + year; // valt datum
                String curDate = String.valueOf(day);
                List<String> freetimes = new ArrayList<>();

                // exempel på att hitta rätt tider för rätt dag, kommer ändras när databaser implementeras
                if(curDate.equals("1")) {
                    radioGroup.removeAllViews();
                    String[] dates = new String[]{"11:00", "11:15", "12:30", "13:00", "13:45", "14:15"}; //TODO hämta från databas
                    Collections.addAll(freetimes, dates);

                }
                else if(curDate.equals("2")){
                    radioGroup.removeAllViews();
                    String[] dates= new String[]{"11:30", "13:15", "14:00"}; //TODO hämta från databas
                    Collections.addAll(freetimes, dates);
                }

                //Skapa Radiobuttons
                int stringcount = freetimes.size(); // hur många tider den dagen
                for (int i=0; i<stringcount; i++){
                    RadioButton newRadioButton = new RadioButton(this_obj);
                    newRadioButton.setText(freetimes.get(i));
                    newRadioButton.setId(View.generateViewId());
                    radioGroup.addView(newRadioButton);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                error.setVisibility(View.INVISIBLE);
            }
        });


        //när man trycker på boka vaccin
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                if(radioButton==null){ //försöker boka utan någon tid vald
                    error.setVisibility(View.VISIBLE);
                    Log.i("FEL", "Ingen tid vald");
                    return;
                }

                booking.time = (String) radioButton.getText(); //tiden som är vald
                EditText text_allergies = (EditText) findViewById(R.id.editText_allergies);
                EditText text_medicines = (EditText) findViewById(R.id.editText_medicine);
                EditText text_comments = (EditText) findViewById(R.id.editText_comments);
                booking.allergy = text_allergies.getText().toString(); //sträng med allergier
                booking.meds = text_medicines.getText().toString(); // sträng med mediciner
                booking.message = text_comments.getText().toString(); // sträng med kommentarer

                String information = "Datum: " + booking.date + "\nTid: " + booking.time + "\nVaccin: " + booking.vaccine + "\nKlinik: " + booking.clinic + " " + booking.city;

                new AlertDialog.Builder(this_obj)
                        .setTitle("Din bokning")
                        .setMessage(information+"\n\nVill du bekräfta?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext(), MainMyPage.class);
                                startActivity(intent);
                                // TODO spara informationen i en databas och gå till min sida
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

            }
        });
    }
}