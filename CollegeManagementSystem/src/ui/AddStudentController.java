package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import dao.StudentDAO;
import util.DBConnection;

public class AddStudentController {

    @FXML
    private TextField rollNumberField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label statusLabel;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    private Map<String, Integer> courseMap = new HashMap<>();

    private StudentDAO studentDao = new StudentDAO();

    @FXML
    private void handleAddStudent(ActionEvent event) {
        String roll = rollNumberField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String selectedCourse = courseComboBox.getValue();
        Integer selectedYear = yearComboBox.getValue();

        if (roll.isEmpty() || name.isEmpty() || email.isEmpty() || selectedCourse == null || selectedYear == null) {
            statusLabel.setText("All fields are required, including course and year.");
            return;
        }

        int courseId = courseMap.get(selectedCourse);

        boolean inserted = studentDao.addStudent(roll, name, email, courseId, selectedYear);
        if (inserted) {
            statusLabel.setText("Student added successfully.");
            clearFields();
        } else {
            statusLabel.setText("Failed to add student (duplicate roll number?).");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
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

    @FXML
    public void initialize() {
        loadCourses();
        loadYears();
    }

    private void loadCourses() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Course")) {

            while (rs.next()) {
                int id = rs.getInt("course_id");
                String name = rs.getString("course_name");
                courseComboBox.getItems().add(name);
                courseMap.put(name, id);  // map course name to course_id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadYears() {
        yearComboBox.getItems().addAll(1, 2, 3, 4);
    }

    private void clearFields() {
        rollNumberField.clear();
        nameField.clear();
        emailField.clear();
        courseComboBox.getSelectionModel().clearSelection();
        yearComboBox.getSelectionModel().clearSelection();
    }
}