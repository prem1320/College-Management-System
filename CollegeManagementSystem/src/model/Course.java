package model;

import javafx.beans.property.*;

public class Course {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty duration;

    // Constructor
    public Course(int id, String name, int duration) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.duration = new SimpleIntegerProperty(duration);
    }

    // Getters and setters

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
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

    public int getDuration() {
        return duration.get();
    }

    public void setDuration(int value) {
        duration.set(value);
    }

    public IntegerProperty durationProperty() {
        return duration;
    }
}