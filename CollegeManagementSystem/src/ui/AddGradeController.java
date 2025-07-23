package ui;

import dao.GradeDAO;
import dao.StudentDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Student;

public class AddGradeController {

    @FXML
    private TextField rollNumberField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField gradeField;

    @FXML
    private Label statusLabel;

    private GradeDAO gradeDao = new GradeDAO();
    private StudentDAO studentDao = new StudentDAO();

    @FXML
    private void handleSubmit() {
        String roll = rollNumberField.getText().trim().toUpperCase();  // Normalize
        String subject = subjectField.getText().trim();
        String grade = gradeField.getText().trim();

        if (roll.isEmpty() || subject.isEmpty() || grade.isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Please fill all fields.");
            return;
        }

        // ✅ Check if student exists
        Student student = studentDao.getStudentByRoll(roll);
        if (student == null) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Roll number does not exist.");
            return;
        }

        boolean success = gradeDao.insertGrade(roll, subject, grade);
        if (success) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Grade added successfully.");
            rollNumberField.clear();
            subjectField.clear();
            gradeField.clear();
        } else {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Error adding grade.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FacultyDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) rollNumberField.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}