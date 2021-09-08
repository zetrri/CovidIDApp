package com.example.covidapp.Dashboard;

import java.io.Serializable;

public class Monthly implements Serializable {
    String Month;
    int Amount;

    public Monthly(String month, int amount) {
        Month = month;
        Amount = amount;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    @Override
    public String toString(){
        return this.getMonth();
    }
}
