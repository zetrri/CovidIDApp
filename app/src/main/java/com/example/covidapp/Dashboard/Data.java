package com.example.covidapp.Dashboard;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    //Name of county
    private String name;
    private int id;
    private int number;
    private ArrayList<Weekly> weekly;
    private ArrayList<Monthly> monthly;


    public Data (String name, int id, int number, ArrayList<Weekly> weekly,ArrayList<Monthly> monthly){
        this.id = id;
        this.name = name;
        this.number = number;
        this.weekly = weekly;
        this.monthly = monthly;
    }

    public ArrayList<Monthly> getMonthly() {
        return monthly;
    }

    public void setMonthly(ArrayList<Monthly> monthly) {
        this.monthly = monthly;
    }

    public ArrayList<Weekly> getWeeklyDist() {
        return weekly;
    }

    public void setWeeklyDist(ArrayList<Weekly> weekly) {
        this.weekly = weekly;
    }

    public int getId(){
        return id;
    }
    public void setId(){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return name;
    }
}
