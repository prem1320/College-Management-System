package model;

import javafx.beans.property.*;

public class Student {
   
    private final StringProperty rollNumber;
    private final StringProperty name;
    private final StringProperty email;
    private final IntegerProperty courseId;
    private final StringProperty courseName; // optional for display
    private final IntegerProperty yearNumber; // NEW

    // Constructor
    public Student( String rollNumber, String name, String email, int courseId, String courseName, int yearNumber) {
        
        this.rollNumber = new SimpleStringProperty(rollNumber);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.courseId = new SimpleIntegerProperty(courseId);
        this.courseName = new SimpleStringProperty(courseName);
        this.yearNumber = new SimpleIntegerProperty(yearNumber);
    }

    // Getters and Setters
    

    public String getRollNumber() {
        return rollNumber.get();
    }
    public void setRollNumber(String value) {
        rollNumber.set(value);
    }
    public StringProperty rollNumberProperty() {
        return rollNumber;
    }

    public String getName() {
        return name.get();
    }
    public void setName(String value) {
        name.set(value);
    }
    public StringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }
    public void setEmail(String value) {
        email.set(value);
    }
    public StringProperty emailProperty() {
        return email;
    }

    public int getCourseId() {
        return courseId.get();
    }
    public void setCourseId(int value) {
        courseId.set(value);
    }
    public IntegerProperty courseIdProperty() {
        return courseId;
    }

    public String getCourseName() {
        return courseName.get();
    }
    public void setCourseName(String value) {
        courseName.set(value);
    }
    public StringProperty courseNameProperty() {
        return courseName;
    }

    public int getYearNumber() {
        return yearNumber.get();
    }
    public void setYearNumber(int value) {
        yearNumber.set(value);
    }
    public IntegerProperty yearNumberProperty() {
        return yearNumber;
    }
}