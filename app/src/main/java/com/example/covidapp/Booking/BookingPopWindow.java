package com.example.covidapp.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.covidapp.R;

public class BookingPopWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_pop_window);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height *.6));

        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        String date = intent.getStringExtra("date");
        String vaccine = intent.getStringExtra("vaccine");
        String clinic = intent.getStringExtra("clinic");
        String city = intent.getStringExtra("city");
        String textInfo = "Datum: " + date + "\nTid: " + time + "\nKlinik: " + clinic + "\nVaccin: " + vaccine;

        TextView text = findViewById(R.id.textView2);
        text.setText(textInfo);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: sparar informationen i databasen och går till mina bokade tider
            }
        });


    }
}