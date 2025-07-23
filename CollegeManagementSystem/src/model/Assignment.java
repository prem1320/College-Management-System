package model;

public class Assignment {
    
    private String title;
    private String description;
    private String dueDate;

    public Assignment(String title, String description, String dueDate) {
        
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getters only (JavaFX TableView requires getters)
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDueDate() {
        return dueDate;
    }
}