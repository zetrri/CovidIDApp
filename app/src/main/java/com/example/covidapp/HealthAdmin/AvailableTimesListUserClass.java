package com.example.covidapp.HealthAdmin;

import java.util.Calendar;

public class AvailableTimesListUserClass {
    String city;
    String county;
    String clinic;
    Calendar date;

    public AvailableTimesListUserClass(){}
    public AvailableTimesListUserClass(String city, String county, String clinic, Calendar date) {
        this.city = city;
        this.county = county;
        this.clinic = clinic;
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}

