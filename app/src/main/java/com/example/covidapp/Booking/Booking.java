package com.example.covidapp.Booking;

public class Booking {
    public String date;
    public String time;
    public String clinic;
    public String city;
    public String county;
    public String vaccine;
    public String allergy;
    public String meds;
    public String message;
    public String personNr;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
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

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getMeds() {
        return meds;
    }

    public void setMeds(String meds) {
        this.meds = meds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPersonNr() {
        return personNr;
    }

    public void setPersonNr(String personNr) {
        this.personNr = personNr;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String namn;


    public Booking(String _date, String _time, String _clinic, String _city, String _county, String _vaccine, String _allergy, String _meds, String _message, String _personNr, String _namn){
        this.date = _date;
        this.time = _time;
        this.clinic = _clinic;
        this.city = _city;
        this.county = _county;
        this.vaccine = _vaccine;
        this.allergy = _allergy;
        this.meds = _meds;
        this.message = _message;
        this.personNr = _personNr;
        this.namn = _namn;
    }
}
