package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidapp.Booking.BookingMainActivity;
import com.example.covidapp.Dashboard.MainDashboard;
import com.example.covidapp.HealthAdmin.AdminAddAvailableTimes;
import com.example.covidapp.HealthAdmin.AdminMenu;
import com.example.covidapp.HealthAdmin.QuestionnaireResponseActivity;
import com.example.covidapp.LogIn.MainLogInActivity;
import com.example.covidapp.MyPage.MainMyPage;
import com.example.covidapp.Passport.PassportMainActivity;
import com.example.covidapp.UserReg.MainUserRegActivity;
import com.example.covidapp.faq.Faq_main;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button buttonDashboard,buttonfaq,buttonUserLogin,buttonMainscreen2,buttonMyPage,buttonMyAppointments,buttonpassport,buttonuserreg,buttonquestresp, logout, buttonadminaddtimes;
    private FirebaseAuth firebaseAuth;

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
        buttonadminaddtimes = findViewById(R.id.buttonAddMoreTimesTest);

        //logout
        logout = findViewById(R.id.btnLogout);

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
                Intent intent = new Intent(getBaseContext(), AdminMenu.class);
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

        buttonquestresp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), QuestionnaireResponseActivity.class);
                startActivity(intent);
            }
        });

        //test logout
        firebaseAuth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
               // startActivity(new Intent(MainActivity.this, MainActivity.class));
                Log.i("Error", "User successfully logged out!"); //logging
                Toast.makeText(getBaseContext(), "You are now logged out!", Toast.LENGTH_SHORT).show(); // print that the user logged out.
            }
        });
        buttonadminaddtimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AdminAddAvailableTimes.class);
                startActivity(intent);
            }
        });

    }
}