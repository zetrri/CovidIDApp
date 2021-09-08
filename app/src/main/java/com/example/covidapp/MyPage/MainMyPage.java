package com.example.covidapp.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.covidapp.R;

public class MainMyPage extends AppCompatActivity {
    int number_of_bookings = 1; //TODO hämta från databas
    booking [] arr_bookings = new booking[32];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_my_page);
        int i=0;
        while(i<number_of_bookings){
            arr_bookings[i] = new booking("6/1-2021", "14:30", "Gripen","Karlstad","Phizer"); // TODO hämta från databas
            i++;
        }
    }
}