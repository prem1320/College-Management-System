package dao;

import model.Attendance;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Attendance a = new Attendance(
                    rs.getString("roll_number"),
                    rs.getString("date"),
                    rs.getString("status")
                );
                list.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        }

        return list;
    }

    /**
     * Returns the attendance percentage of a student.
     * Example: 80.0 means 80%.
     */
    public double getAttendancePercentage(String rollNumber) {
        String sqlTotal = "SELECT COUNT(*) FROM attendance WHERE roll_number = ?";
        String sqlPresent = "SELECT COUNT(*) FROM attendance WHERE roll_number = ? AND status = 'Present'";

        int total = 0;
        int present = 0;

        try (Connection conn = DBConnection.getConnection()) {

            // Get total
            try (PreparedStatement stmt = conn.prepareStatement(sqlTotal)) {
                stmt.setString(1, rollNumber);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

            // Get present
            try (PreparedStatement stmt = conn.prepareStatement(sqlPresent)) {
                stmt.setString(1, rollNumber);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    present = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error calculating attendance percentage: " + e.getMessage());
        }

        if (total == 0) {
            return 0.0;
        }

        return (present * 100.0) / total;
    }
}