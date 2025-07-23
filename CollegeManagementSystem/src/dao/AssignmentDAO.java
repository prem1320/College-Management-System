package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;
import util.DBConnection;

public class AssignmentDAO {

    public List<Assignment> getAllAssignments() {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignments ORDER BY dueDate";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Assignment assignment = new Assignment(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("dueDate")
                );
                assignments.add(assignment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    public List<Assignment> getDueAssignmentsForStudent(String rollNumber) {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignments WHERE dueDate >= CURRENT_DATE ORDER BY dueDate";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Uncomment below if you have a column for studentRollNumber
            // stmt.setString(1, rollNumber);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Assignment assignment = new Assignment(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("dueDate")
                );
                assignments.add(assignment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }
}