package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attendance {
    private final StringProperty rollNumber;
    private final StringProperty date;
    private final StringProperty status;

    public Attendance(String rollNumber, String date, String status) {
        this.rollNumber = new SimpleStringProperty(rollNumber);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
    }

    public String getRollNumber() {
        return rollNumber.get();
    }

    public StringProperty rollNumberProperty() {
        return rollNumber;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }
}