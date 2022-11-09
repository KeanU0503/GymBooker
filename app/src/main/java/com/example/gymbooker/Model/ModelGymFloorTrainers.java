package com.example.gymbooker.Model;

public class ModelGymFloorTrainers { String inTrainerName, inGymType, inTrainerDay, inTrainerDuration, inTrainerCategory, inLimit, inDetails, inTime;


    public ModelGymFloorTrainers(String TrainerName, String GymType, String TrainerDay, String TrainerDuration, String TrainerCategory, String Limit, String Details, String Time) {
        inTrainerName = TrainerName;
        inGymType = GymType;
        inTrainerDay = TrainerDay;
        inTrainerDuration = TrainerDuration;
        inTrainerCategory = TrainerCategory;
        inLimit = Limit;
        inDetails = Details;
        inTime = Time;
    }

    public String getInTrainerName() {
        return inTrainerName;
    }

    public void setInTrainerName(String inTrainerName) {
        this.inTrainerName = inTrainerName;
    }

    public String getInGymType() {
        return inGymType;
    }

    public void setInGymType(String inGymType) {
        this.inGymType = inGymType;
    }

    public String getInTrainerDay() { return inTrainerDay; }

    public void setInTrainerDay(String inTrainerDay) { this.inTrainerDay = inTrainerDay; }

    public String getInTrainerDuration() {
        return inTrainerDuration;
    }

    public void setInTrainerDuration(String inTrainerDuration) {
        this.inTrainerDuration = inTrainerDuration;
    }

    public String getInTrainerCategory() {
        return inTrainerCategory;
    }

    public void setInTrainerCategory(String inTrainerCategory) {
        this.inTrainerCategory = inTrainerCategory;
    }

    public String getInLimit() {
        return inLimit;
    }

    public void setInLimit(String inLimit) {
        this.inLimit = inLimit;
    }

    public String getInDetails() {
        return inDetails;
    }

    public void setInDetails(String inDetails) {
        this.inDetails = inDetails;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }
}
