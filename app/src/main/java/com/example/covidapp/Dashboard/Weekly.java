package com.example.covidapp.Dashboard;

import java.io.Serializable;

public class Weekly implements Serializable {
    int Week;
    int amountDist;
    int amountAdm;
    Double amountPercent;

    public Weekly(int week, int amountDist, int amountAdm, Double amountPercent) {
        this.Week = week;
        this.amountDist = amountDist;
        this.amountAdm = amountAdm;
        this.amountPercent = amountPercent;
    }

    public int getAmountAdm() {
        return amountAdm;
    }

    public void setAmountAdm(int amountAdm) {
        this.amountAdm = amountAdm;
    }

    public Double getAmountPercent() {
        return amountPercent;
    }

    public void setAmountPercent(Double amountPercent) {
        this.amountPercent = amountPercent;
    }

    public int getWeek() {
        return Week;
    }

    public void setWeek(int week) {
        this.Week = week;
    }

    public int getAmountDist() {
        return amountDist;
    }

    public void setAmountDist(int amountDist) {
        this.amountDist = amountDist;
    }
    @Override
    public String toString(){
        return "Vecka "+Integer.toString(this.getWeek());
    }
}
