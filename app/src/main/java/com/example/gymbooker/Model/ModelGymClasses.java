package com.example.gymbooker.Model;

// represents the data model being displayed by the RecyclerView

public class ModelGymClasses {

    private String inClassName, inClassDetails, inClassTrainer, inClassDuration, inClassDate, inClassTime; // Java object

    // For Firestore // Constructors
    public ModelGymClasses(String ClassName, String ClassDetails, String ClassTrainer, String ClassDuration, String ClassDate, String ClassTime) {

        inClassName = ClassName;
        inClassDetails = ClassDetails;
        inClassTrainer = ClassTrainer;
        inClassDuration = ClassDuration;
        inClassDate = ClassDate;
        inClassTime = ClassTime;

    }

    public String getInClassName() { return inClassName; }

    public void setInClassName(String inClassName) { this.inClassName = inClassName; } // referring to te field (String) on the object that this method was called

    public String getInClassDetails() { return inClassDetails; }

    public void setInClassDetails(String inClassDetails) { this.inClassDetails = inClassDetails; }

    public String getInClassTrainer() { return inClassTrainer; }

    public void setInClassTrainer(String inClassTrainer) { this.inClassTrainer = inClassTrainer; }

    public String getInClassDuration() { return inClassDuration; }

    public void setInClassDuration(String inClassDuration) { this.inClassDuration = inClassDuration; }

    public String getInClassDate() { return inClassDate; }

    public void setInClassDate(String inClassDate) { this.inClassDate = inClassDate; }

    public String getInClassTime() { return inClassTime; }

    public void setInClassTime(String inClassTime) { this.inClassTime = inClassTime; }





}
