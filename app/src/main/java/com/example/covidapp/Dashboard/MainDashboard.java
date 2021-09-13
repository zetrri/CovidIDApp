package com.example.covidapp.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covidapp.R;

public class MainDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main_activity);
        Button button,button_deathsandcases,buttonAgeProduct,buttonCumelative;
        button_deathsandcases = findViewById(R.id.button_deathsandCases);
        button = findViewById(R.id.buttonDisDoses);
        buttonCumelative = findViewById(R.id.button_cumelative);
        buttonAgeProduct = findViewById(R.id.button);
        buttonCumelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ActivityDistrubatedDoses.class);
                intent.putExtra("Mode",false);
                startActivity(intent);
            }
        });
        buttonAgeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ActivityDosesAgeProduct.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ActivityDistrubatedDoses.class);

                intent.putExtra("mode",true);
                startActivity(intent);
            }
        });
        button_deathsandcases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ActivityDeathsAndCases.class);
                startActivity(intent);
            }
        });
    }
}