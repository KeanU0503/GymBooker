package com.example.gymbooker.Model;

public class ModelGymHistory {
    private String inDuration, inDate, inTime, inGymType, inGymTrainer, inClientName, inDay, inBookTime, inContactNumber;

    public ModelGymHistory(String Duration, String Date, String Time, String GymType, String GymTrainer, String ClientName, String Day, String BookTime, String ContactNumber) {
        inDuration = Duration;
        inDate = Date;
        inTime = Time;
        inGymType = GymType;
        inGymTrainer = GymTrainer;
        inClientName = ClientName;
        inDay = Day;
        inBookTime = BookTime;
        inContactNumber = ContactNumber;
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

    public String getInGymTrainer() {
        return inGymTrainer;
    }

    public void setInGymTrainer(String inGymTrainer) {
        this.inGymTrainer = inGymTrainer;
    }

    public String getInClientName() {
        return inClientName;
    }

    public void setInClientName(String inClientName) {
        this.inClientName = inClientName;
    }

    public String getInDay() {
        return inDay;
    }

    public void setInDay(String inDay) {
        this.inDay = inDay;
    }

    public String getInBookTime() {
        return inBookTime;
    }

    public void setInBookTime(String inBookTime) {
        this.inBookTime = inBookTime;
    }

    public String getInContactNumber() {
        return inContactNumber;
    }

    public void setInContactNumber(String inContactNumber) {
        this.inContactNumber = inContactNumber;
    }
}

