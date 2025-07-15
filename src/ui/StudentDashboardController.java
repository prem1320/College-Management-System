package ui;

import java.util.List;

import dao.AssignmentDAO;
import dao.AttendanceDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Assignment;
import model.Student;

public class StudentDashboardController {

    private String rollNumber;

    private AssignmentDAO assignmentDAO = new AssignmentDAO();
    private AttendanceDAO attendanceDAO = new AttendanceDAO();

    @FXML
    private VBox notificationBox;

    @FXML
    private Button logoutButton;

    private Student student; // add this field

    public void initData(Student student) {
        this.student = student;
        System.out.println("Student logged in: " + student.getRollNumber());

        // If you previously had
        // this.rollNumber = rollNumber;
        // remove it, you don't need that anymore.

        loadNotifications();
    }

    @FXML
    private void handleViewProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewProfile.fxml"));
            Parent root = loader.load();

            ViewProfileController controller = loader.getController();
            controller.initData(student);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewGrades() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewGrades.fxml"));
            Parent root = loader.load();

            ViewGradesController controller = loader.getController();
            controller.initData(student);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewAssignments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewAssignment.fxml"));
            Parent root = loader.load();

            ViewAssignmentsController controller = loader.getController();
            controller.initData(student);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewAttendance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewAttendance.fxml"));
            Parent root = loader.load();

            ViewAttendanceController controller = loader.getController();
            controller.initData(student);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadNotifications() {
        notificationBox.getChildren().clear();

        // Load assignment notifications
        List<Assignment> dueAssignments = assignmentDAO.getDueAssignmentsForStudent(rollNumber);
        for (Assignment a : dueAssignments) {
            Label lbl = new Label("Assignment \"" + a.getTitle() + "\" due on " + a.getDueDate());
            lbl.setStyle("-fx-text-fill: #d35400; -fx-font-weight: bold;");
            notificationBox.getChildren().add(lbl);
        }

        // Load attendance notification
        double attendance = attendanceDAO.getAttendancePercentage(rollNumber);
        if (attendance < 75) {
            Label lbl = new Label("Your attendance is below 75% (" + String.format("%.2f", attendance) + "%).");
            lbl.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            notificationBox.getChildren().add(lbl);
        }
    }
}