package model;

public class Grade {

    private String subject;
    private String grade;
    private String rollNumber;

    public Grade(String subject, String grade, String rollNumber) {
        this.subject = subject;
        this.grade = grade;
        this.rollNumber =rollNumber;
    }

    public String getSubject() {
        return subject;
    }

    public String getGrade() {
        return grade;
    }
    
    public String getRollNumber() {
    	return rollNumber;
    }
}