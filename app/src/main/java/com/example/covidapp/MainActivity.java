package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covidapp.Booking.BookingMainActivity;
import com.example.covidapp.Dashboard.MainDashboard;
import com.example.covidapp.LogIn.MainLogInActivity;
import com.example.covidapp.MyPage.MainMyPage;
import com.example.covidapp.Passport.PassportMainActivity;
import com.example.covidapp.UserReg.MainUserRegActivity;
import com.example.covidapp.faq.Faq_main;

public class MainActivity extends AppCompatActivity {

    Button buttonDashboard,buttonfaq,buttonUserLogin,buttonMainscreen2,buttonMyPage,buttonMyAppointments,buttonpassport,buttonuserreg,buttonquestresp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDashboard = findViewById(R.id.buttonDashboard);
        buttonfaq = findViewById(R.id.buttonFaq);
        buttonUserLogin = findViewById(R.id.buttonUserLogin);
        buttonMainscreen2= findViewById(R.id.buttonMainscreen2);
        buttonMyPage = findViewById(R.id.buttonMyPage);
        buttonMyAppointments = findViewById(R.id.buttonMyAppointments);
        buttonpassport = findViewById(R.id.buttonPassport);
        buttonuserreg = findViewById(R.id.buttonUserReg);
        buttonquestresp = findViewById(R.id.buttonQuestRes);

        buttonDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainDashboard.class);
                startActivity(intent);

            }
        });
        buttonfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Faq_main.class);
                startActivity(intent);
            }
        });
        buttonUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainLogInActivity.class);
                startActivity(intent);
            }
        });
        buttonMainscreen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity2.class);
                startActivity(intent);

            }

        });
        buttonMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainMyPage.class);
                startActivity(intent);
            }
        });
        buttonMyAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BookingMainActivity.class);
                startActivity(intent);
            }
        });
        buttonpassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PassportMainActivity.class);
                startActivity(intent);
            }
        });
        buttonuserreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainUserRegActivity.class);
                startActivity(intent);
            }
        });



    }
}