package com.example.covidapp.MyPage;

public class booking {
    private String date;
    private String time;
    private String clinic;
    private String city;
    private String vaccine;

    public booking(String _date, String _time, String _clinic, String _city, String _vaccine){
        this.date = _date;
        this.time = _time;
        this.clinic = _clinic;
        this.city = _city;
        this.vaccine = _vaccine;

    }
}