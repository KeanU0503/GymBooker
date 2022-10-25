package com.example.gymbooker.Model;

public class ModelGymHistory {
    private String inDuration, inDate, inTime, inGymType;

    public ModelGymHistory(String Duration, String Date, String Time, String GymType) {
        inDuration = Duration;
        inDate = Date;
        inTime = Time;
        inGymType = GymType;
    }

    public String getInDuration() {
        return inDuration;
    }

    public void setInDuration(String inDuration) {
        this.inDuration = inDuration;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getInGymType() {
        return inGymType;
    }

    public void setInGymType(String inGymType) {
        this.inGymType = inGymType;
    }
}

