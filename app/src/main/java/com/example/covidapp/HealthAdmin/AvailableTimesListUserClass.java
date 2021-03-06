package com.example.covidapp.HealthAdmin;
import java.util.Comparator;

public class AvailableTimesListUserClass implements Comparable<AvailableTimesListUserClass> {
    String city;
    String county;
    String clinic;
    //Calendar date;
    String id;
    String bookedBy;
    Boolean isAvailable;
    Long timestamp;
    String allergies;
    String medication;
    String comments;
    String vaccine;
    Boolean isApproved;

    public AvailableTimesListUserClass(){}
    public AvailableTimesListUserClass(String city, String county, String clinic, Long date,String thisid,String bookedByUser,Boolean isAvailableboolean,String allergiesinput,String medicationinput, String commentsinput,String vaccineinput, Boolean isApprovedboolean) {
        this.city = city;
        this.county = county;
        this.clinic = clinic;
        this.timestamp = date;
        this.id = thisid;
        this.bookedBy = bookedByUser;
        this.isAvailable = isAvailableboolean;
        this.allergies = allergiesinput;
        this.medication = medicationinput;
        this.comments = commentsinput;
        this.vaccine = vaccineinput;
        this.isApproved = isApprovedboolean;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString(){
        return getTimestamp().toString();
    }

    @Override
    public int compareTo(AvailableTimesListUserClass availableTimesListUserClass) {
        return timestamp.compareTo(availableTimesListUserClass.timestamp);
    }
}
