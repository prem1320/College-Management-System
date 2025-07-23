package ui;

import dao.StudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AttendanceRecord;
import model.Student;

import java.util.List;

public class ViewAttendanceController {

    @FXML
    private TableView<AttendanceRecord> attendanceTable;

    @FXML
    private TableColumn<AttendanceRecord, String> dateColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> statusColumn;

    @FXML
    private Button backButton;

    private Student student;

    public void initData(Student student) {
        this.student = student;

        // Set roll number from the passed student
        String rollNumber = student.getRollNumber();
        System.out.println("Loading attendance for roll number: " + rollNumber);

        // Configure columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load data from DAO
        StudentDAO dao = new StudentDAO();
        List<AttendanceRecord> records = dao.getAttendanceRecords(rollNumber);
        ObservableList<AttendanceRecord> data = FXCollections.observableArrayList(records);
        attendanceTable.setItems(data);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentDashboard.fxml"));
            Parent root = loader.load();

            // Get the controller of the dashboard
            StudentDashboardController controller = loader.getController();
            controller.initData(student); // Pass the full student object again

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
