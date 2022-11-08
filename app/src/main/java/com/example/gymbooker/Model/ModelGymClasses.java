package com.example.gymbooker.Model;

// represents the data model being displayed by the RecyclerView

public class ModelGymClasses {

    private String inClassName, inClassDetails, inClassTrainer, inClassDuration, inClassDay, inClassTime, inClassLimit, inClassCategory, inMaxLimit; // Java object

    // Constructors
    public ModelGymClasses(String ClassName, String ClassDetails, String ClassTrainer, String ClassDuration, String ClassDay, String ClassTime, String ClassLimit, String ClassCategory, String MaxLimit) {

        inClassName = ClassName;
        inClassDetails = ClassDetails;
        inClassTrainer = ClassTrainer;
        inClassDuration = ClassDuration;
        inClassDay = ClassDay;
        inClassTime = ClassTime;
        inClassLimit = ClassLimit;
        inClassCategory = ClassCategory;
        inMaxLimit = MaxLimit;
    }

    // access the model with getter and setter methods
    public String getInClassName() { return inClassName; }

    public void setInClassName(String inClassName) { this.inClassName = inClassName; } // referring to the field (String) on the object that this method was called

    public String getInClassDetails() { return inClassDetails; }

    public void setInClassDetails(String inClassDetails) { this.inClassDetails = inClassDetails; }

    public String getInClassTrainer() { return inClassTrainer; }

    public void setInClassTrainer(String inClassTrainer) { this.inClassTrainer = inClassTrainer; }

    public String getInClassDuration() { return inClassDuration; }

    public void setInClassDuration(String inClassDuration) { this.inClassDuration = inClassDuration; }

    public String getInClassDay() {
        return inClassDay;
    }

    public void setInClassDay(String inClassDay) {
        this.inClassDay = inClassDay;
    }

    public String getInClassTime() { return inClassTime; }

    public void setInClassTime(String inClassTime) { this.inClassTime = inClassTime; }

    public String getInClassLimit() {
        return inClassLimit;
    }

    public void setInClassLimit(String inClassLimit) {
        this.inClassLimit = inClassLimit;
    }

    public String getInClassCategory() {
        return inClassCategory;
    }

    public void setInClassCategory(String inClassCategory) {
        this.inClassCategory = inClassCategory;
    }

    public String getInMaxLimit() {
        return inMaxLimit;
    }

    public void setInMaxLimit(String inMaxLimit) {
        this.inMaxLimit = inMaxLimit;
    }
}
