package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dao.GradeDAO;
import model.Grade;
import model.Student;

import java.util.List;

public class ViewGradesController {

    @FXML
    private TableView<Grade> gradeTable;

    @FXML
    private TableColumn<Grade, String> rollNumberColumn;

    @FXML
    private TableColumn<Grade, String> subjectColumn;

    @FXML
    private TableColumn<Grade, String> gradeColumn;

    private Student student;

    public void initData(Student student) {
        this.student = student;
        GradeDAO gradeDao = new GradeDAO();
        List<Grade> grades = gradeDao.getGradesForStudent(student.getRollNumber());
        ObservableList<Grade> observableGrades = FXCollections.observableArrayList(grades);
        gradeTable.setItems(observableGrades);
    }

    @FXML
    private void initialize() {
        rollNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRollNumber()));
        subjectColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSubject()));
        gradeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGrade()));
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentDashboard.fxml"));
            Parent root = loader.load();

            StudentDashboardController controller = loader.getController();
            controller.initData(student);  // Passing full student object

            Stage stage = (Stage) gradeTable.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}