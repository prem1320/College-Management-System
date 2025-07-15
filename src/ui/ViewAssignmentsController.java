package ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.Assignment;
import model.Student; // ✅ Added import
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAssignmentsController {

    @FXML
    private TableView<Assignment> assignmentTable;

    @FXML
    private TableColumn<Assignment, String> titleColumn;

    @FXML
    private TableColumn<Assignment, String> descriptionColumn;

    @FXML
    private TableColumn<Assignment, String> dueDateColumn;

    @FXML
    private Button backButton;

    private Student student; // ✅ Store the Student object

    // ✅ This method is called by StudentDashboardController
    public void initData(Student student) {
        this.student = student;
        loadAssignments();
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }

    private void loadAssignments() {
        ObservableList<Assignment> assignments = FXCollections.observableArrayList();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT * FROM assignments WHERE roll_number = ?")) {
            stmt.setString(1, student.getRollNumber());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(new Assignment(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("dueDate")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assignmentTable.setItems(assignments);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentDashboard.fxml"));
            Parent root = loader.load();

            // Get the controller of the dashboard
            StudentDashboardController controller = loader.getController();
            // IMPORTANT: pass the Student object, not String
            controller.initData(student);

            // Switch scene
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
  
  