package com.example.covidapp.Dashboard;

import java.io.Serializable;

public class Weekly implements Serializable {
    int Week;
    int amount;

    public Weekly(int week, int amount) {
        this.Week = week;
        this.amount = amount;
    }

    public int getWeek() {
        return Week;
    }

    public void setWeek(int week) {
        this.Week = week;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Override
    public String toString(){
        return "Vecka "+Integer.toString(this.getWeek());
    }
}
