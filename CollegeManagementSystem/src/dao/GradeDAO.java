package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Grade;
import util.DBConnection;

public class GradeDAO {

    public boolean insertGrade(String roll, String subject, String grade) {
        String sql = "INSERT INTO grades (roll_number, subject, grade) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roll);
            stmt.setString(2, subject);
            stmt.setString(3, grade);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting grade: " + e.getMessage());
            return false;
        }
    }

    public List<Grade> getGradesForStudent(String rollNumber) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT roll_number, subject, grade FROM grades WHERE roll_number = ? ORDER BY subject";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Grade g = new Grade(
                        rs.getString("roll_number"),
                        rs.getString("subject"),
                        rs.getString("grade")
                );
                grades.add(g);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching grades: " + e.getMessage());
        }

        return grades;
    }
}