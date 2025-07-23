package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Student;

public class ViewProfileController {

    @FXML
    private Label rollNumberLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private Label yearLabel;

    private Student student;

    // Now accepts the full Student object
    public void initData(Student student) {
        this.student = student;

        // Set labels from Student object
        rollNumberLabel.setText(student.getRollNumber());
        nameLabel.setText(student.getName());
        emailLabel.setText(student.getEmail());
        courseLabel.setText(student.getCourseName());
        yearLabel.setText("Year: " + student.getYearNumber());
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentDashboard.fxml"));
            Parent root = loader.load();

            StudentDashboardController controller = loader.getController();
            controller.initData(student);  // Passing Student back

            Stage stage = (Stage) rollNumberLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}