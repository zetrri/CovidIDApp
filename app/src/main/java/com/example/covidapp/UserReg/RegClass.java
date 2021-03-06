package com.example.covidapp.UserReg;

public class RegClass {
    String Mail;
    String Firstname;
    String Lastname;
    String Adress;
    String Persnr;
    String Phonenr;
    String UserID;
    Boolean admin;
    long dosOne;
    long dosTwo;

    public RegClass(){}
    public RegClass(String mail, String firstname, String lastname, String adress, String persnr, String phonenr, String userID, Boolean isAdmin,long dos1input, long dos2input) {
        Mail = mail;
        Firstname = firstname;
        Lastname = lastname;
        Adress = adress;
        Persnr = persnr;
        Phonenr = phonenr;
        UserID = userID;
        admin = isAdmin;
        this.dosOne = dos1input;
        this.dosTwo = dos2input;
    }

    public long getDosOne() {
        return dosOne;
    }

    public void setDosOne(long dos1input) {
        this.dosOne = dos1input;
    }

    public long getDosTwo() {
        return dosTwo;
    }

    public void setDosTwo(long dos2input) {
        this.dosTwo = dos2input;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPersnr() {
        return Persnr;
    }

    public void setPersnr(String persnr) {
        Persnr = persnr;
    }

    public String getPhonenr() {
        return Phonenr;
    }

    public void setPhonenr(String phonenr) {
        Phonenr = phonenr;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}

