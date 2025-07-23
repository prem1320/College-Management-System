package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddAttendanceController {

    @FXML
    private TextField rollNumberField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        statusChoiceBox.getItems().addAll("Present", "Absent");
        statusChoiceBox.setValue("Present");
    }

    @FXML
    private void handleSubmit() {
        String rollNumber = rollNumberField.getText().trim();
        LocalDate date = datePicker.getValue();
        String status = statusChoiceBox.getValue();

        if (rollNumber.isEmpty()) {
            statusLabel.setText("Roll number is required.");
            return;
        }
        if (date == null) {
            statusLabel.setText("Please select a date.");
            return;
        }

        String sql = "INSERT INTO attendance (roll_number, date, status) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rollNumber);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setString(3, status);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                statusLabel.setStyle("-fx-text-fill: green;");
                statusLabel.setText("Attendance successfully recorded.");

                rollNumberField.clear();
                datePicker.setValue(null);
                statusChoiceBox.setValue("Present");
            } else {
                statusLabel.setText("Failed to add attendance.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error: " + e.getMessage());
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
