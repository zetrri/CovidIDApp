package com.example.covidapp.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.covidapp.R;

public class ActivityDosesAgeProduct extends AppCompatActivity {

    Spinner spinnerAge,spinnerProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_doses_age_product);
        spinnerAge = findViewById(R.id.spinnerAgeInDashProductAndAge);
        spinnerProduct = findViewById(R.id.spinnerProductInDashProductAge);

        //just examples, set correct values later
        //TODO insert correct spinner values
        String[] ages = new String[]{"All","0-9","10-19","20-29"};
        String[] Products = new String[]{"All","Astra","Pfizer","Johnnson"};

        setSpinnerAge(ages);
        setSpinnerProduct(Products);


    }

    public void setSpinnerAge(String[] ages){
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ages);
        spinnerAge.setAdapter(adapter);
    }

    public void setSpinnerProduct(String[] product){
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, product);
        spinnerProduct.setAdapter(adapter);
    }


}