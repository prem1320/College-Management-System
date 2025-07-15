package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.StudentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import model.Student;
import util.DBConnection;

public class StudentRollLoginController {

    @FXML
    private TextField rollNumberField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private StudentDAO studentDao = new StudentDAO();

    // ✅ This method must exist for FXML
    @FXML
    private void handleStudentLogin() {
        String roll = rollNumberField.getText().trim();
        String pass = passwordField.getText().trim();

        if (roll.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("Please enter roll number and password.");
            return;
        }

        // Validate password (example: fixed password)
        if (!"student123".equals(pass)) {
            statusLabel.setText("Invalid password.");
            return;
        }

        // Retrieve student
        Student student = getStudentByRoll(roll);
        if (student != null) {
            openStudentDashboard(student);
        } else {
            statusLabel.setText("Invalid roll number.");
        }
    }

    public Student getStudentByRoll(String rollNumber) {
        String sql = "SELECT s.*, c.course_name FROM students s " +
                     "LEFT JOIN course c ON s.course_id = c.course_id " +
                     "WHERE s.roll_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(
                    
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("year_number")
                );
            }
        } catch (SQLException e) {
            System.out.println("Get Error: " + e.getMessage());
        }
        return null;
    }

    private void openStudentDashboard(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentDashboard.fxml"));
            Parent root = loader.load();

            ui.StudentDashboardController controller = loader.getController();
            controller.initData(student);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Student Dashboard");
            stage.setScene(scene);
            stage.show();

            // Close login window
            ((Stage) rollNumberField.getScene().getWindow()).close();
        } catch (Exception e) {
            statusLabel.setText("Error opening student dashboard.");
            e.printStackTrace();
        }
    }
}