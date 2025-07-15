package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import util.DBConnection;

import java.sql.*;

public class CourseManagementController {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, Integer> idColumn;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, Integer> durationColumn;
    @FXML private TextField courseNameField;
    @FXML private TextField durationField;

    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        loadCourses();
    }

    private void loadCourses() {
        courseList.clear();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Course")) {

            while (rs.next()) {
                courseList.add(new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("duration")
                ));
            }
            courseTable.setItems(courseList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        String name = courseNameField.getText();
        int duration = Integer.parseInt(durationField.getText());
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO Course (course_name, duration) VALUES (?, ?)")) {

            ps.setString(1, name);
            ps.setInt(2, duration);
            ps.executeUpdate();
            loadCourses();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String name = courseNameField.getText();
            int duration = Integer.parseInt(durationField.getText());
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                     "UPDATE Course SET course_name=?, duration=? WHERE course_id=?")) {

                ps.setString(1, name);
                ps.setInt(2, duration);
                ps.setInt(3, selected.getId());
                ps.executeUpdate();
                loadCourses();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDelete() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM Course WHERE course_id=?")) {

                ps.setInt(1, selected.getId());
                ps.executeUpdate();
                loadCourses();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleRefresh() {
        loadCourses();
    }

    private void clearFields() {
        courseNameField.clear();
        durationField.clear();
    }
}
