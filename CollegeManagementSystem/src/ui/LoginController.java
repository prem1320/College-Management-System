package ui;



import dao.UserDAO;
import dao.StudentDAO;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private UserDAO userDao = new UserDAO();
    private StudentDAO studentDao = new StudentDAO();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter username and password.");
            return;
        }

        if ("student".equals(username) && "1234".equals(password)) {
            openStudentRollLogin();
        } else {
            User user = userDao.validateLogin(username, password);
            if (user != null) {
                if ("faculty".equalsIgnoreCase(user.getRole())) {
                    openFacultyDashboard();
                } else if ("admin".equalsIgnoreCase(user.getRole())) {
                    openAdminDashboard();
                } else {
                    statusLabel.setText("Unknown role: " + user.getRole());
                }
            } else {
                statusLabel.setText("Invalid credentials.");
            }
        }
    }
    private void openStudentRollLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentRollLogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Student Roll Login");
            stage.setScene(scene);
            stage.show();
            ((Stage) usernameField.getScene().getWindow()).close();
        } catch (Exception e) {
            statusLabel.setText("Error opening student login.");
            e.printStackTrace();
        }
    }

    private void openFacultyDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FacultyDashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Faculty Dashboard");
            stage.setScene(scene);
            stage.show();
            ((Stage) usernameField.getScene().getWindow()).close();
        } catch (Exception e) {
            statusLabel.setText("Error opening faculty dashboard.");
            e.printStackTrace();
        }
    }

    private void openAdminDashboard() {
        // Placeholder for admin dashboard
        statusLabel.setText("Admin dashboard coming soon...");
    }
}