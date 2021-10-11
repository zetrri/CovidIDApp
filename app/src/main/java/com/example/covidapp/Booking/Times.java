package com.example.covidapp.Booking;

public class Times {
    int minute;
    int hour;

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Times(int hour, int minute) {
        this.minute = minute;
        this.hour = hour;
    }

    @Override
    public String toString(){
        return String.valueOf(getHour()) +":"+ String.valueOf(getMinute());
    }
}
