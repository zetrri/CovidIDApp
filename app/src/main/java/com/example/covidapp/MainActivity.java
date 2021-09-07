package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covidapp.Dashboard.MainDashboard;

public class MainActivity extends AppCompatActivity {

    Button buttonDashboard;
    Button buttonMyPage;
    Button buttonUserLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDashboard = findViewById(R.id.buttonDashboard);
        buttonMyPage = findViewById(R.id.buttonMyPage);
        buttonUserLogin = findViewById(R.id.buttonUserLogin);

        buttonDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainDashboard.class);
                startActivity(intent);

            }
        });
    }
}