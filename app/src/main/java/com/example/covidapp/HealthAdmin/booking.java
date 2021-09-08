package com.example.covidapp.HealthAdmin;

public class booking {
    public String date;
    public String time;
    public String clinic;
    public String city;
    public String vaccine;
    public String message;
    public String allergy;
    public String personNr;

    public booking(String _date, String _time, String _clinic, String _city, String _vaccine, String _message, String _allergy, String _personNr){
        this.date = _date;
        this.time = _time;
        this.clinic = _clinic;
        this.city = _city;
        this.vaccine = _vaccine;
        this.message = _message;
        this.allergy = _allergy;
        this.personNr = _personNr;
    }
}