package ui;

import dao.GradeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    @FXML
    private void handleSubmit() {
        String roll = rollNumberField.getText().trim();
        String subject = subjectField.getText().trim();
        String grade = gradeField.getText().trim();

        if (roll.isEmpty() || subject.isEmpty() || grade.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
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