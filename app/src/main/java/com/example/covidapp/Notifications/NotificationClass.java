package com.example.covidapp.Notifications;

public class NotificationClass {
    String userID;
    String sender;
    String message;
    String date;

    public NotificationClass(){}
    public NotificationClass(String userID, String sender, String message, String date) {
        this.userID = userID;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    public NotificationClass getNotification() {
        return this;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

