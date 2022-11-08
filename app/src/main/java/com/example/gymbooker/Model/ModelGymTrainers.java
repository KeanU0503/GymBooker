package com.example.gymbooker.Model;

public class ModelGymTrainers { String inTrainerName, inGymType, inTrainerWeekly, inTrainerDuration, inTrainerCategory, inLimit, inDetails, inTime;


    public ModelGymTrainers(String TrainerName, String GymType, String TrainerWeekly, String TrainerDuration, String TrainerCategory, String SlotLimit, String Details, String Time) {
        this.inTrainerName = TrainerName;
        this.inGymType = GymType;
        this.inTrainerWeekly = TrainerWeekly;
        this.inTrainerDuration = TrainerDuration;
        this.inTrainerCategory = TrainerCategory;
        this.inLimit = SlotLimit;
        this.inDetails = Details;
        this.inTime = Time;
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

    public String getInTrainerWeekly() {
        return inTrainerWeekly;
    }

    public void setInTrainerWeekly(String inTrainerWeekly) {
        this.inTrainerWeekly = inTrainerWeekly;
    }

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
