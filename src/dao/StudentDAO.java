package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.AttendanceRecord;
import model.Student;
import util.DBConnection;

public class StudentDAO {

	public boolean addStudent(String rollNumber, String name, String email, int courseId, int yearNumber) {
	    String query = "INSERT INTO Student (roll_number, name, email, course_id, year_number) VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {

	        ps.setString(1, rollNumber);
	        ps.setString(2, name);
	        ps.setString(3, email);
	        ps.setInt(4, courseId);
	        ps.setInt(5, yearNumber);

	        ps.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

        

    // ✅ Update student details (optionally include year and course)
    public boolean updateStudent(Student student) {
        String sql = "UPDATE Student SET name = ?, email = ?, course_id = ?, year_number = ? WHERE roll_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, student.getCourseId());
            stmt.setInt(4, student.getYearNumber());
            stmt.setString(5, student.getRollNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
            return false;
        }
    }

    // ✅ Delete student by roll number
    public boolean deleteStudent(String rollNumber) {
        String sql = "DELETE FROM Student WHERE roll_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rollNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete Error: " + e.getMessage());
            return false;
        }
    }

 // ✅ Get a student by roll number
    public Student getStudentByRoll(String rollNumber) {
        String sql = "SELECT s.*, c.course_name FROM Student s " +
                     "LEFT JOIN Course c ON s.course_id = c.course_id " +
                     "WHERE s.roll_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String courseName = rs.getString("course_name");
                if (courseName == null) {
                    courseName = ""; // fallback if no course found
                }

                return new Student(
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("course_id"),
                    courseName,
                    rs.getInt("year_number")
                );
            }
        } catch (SQLException e) {
            System.out.println("Get Error: " + e.getMessage());
        }
        return null;
    }
    
 // ✅ Get all students
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.*, c.course_name FROM Student s " +
                     "LEFT JOIN Course c ON s.course_id = c.course_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String courseName = rs.getString("course_name");
                if (courseName == null) {
                    courseName = ""; // fallback if no course found
                }

                Student student = new Student(
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("course_id"),
                    courseName,
                    rs.getInt("year_number")
                );

                list.add(student);
            }
        } catch (SQLException e) {
            System.out.println("List Error: " + e.getMessage());
        }
        return list;
    }

    

    // ✅ Get grades for a student
    public List<String> getGradesForStudent(String rollNumber) {
        List<String> grades = new ArrayList<>();
        String sql = "SELECT subject, grade FROM grades WHERE roll_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grades.add(rs.getString("subject") + ": " + rs.getString("grade"));
            }
        } catch (SQLException e) {
            System.out.println("Grades Error: " + e.getMessage());
        }
        return grades;
    }

    // ✅ Get assignments for a student
    public List<String> getAssignmentsForStudent(String rollNumber) {
        List<String> assignments = new ArrayList<>();
        String sql = "SELECT title, due_date FROM assignments WHERE roll_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(rs.getString("title") + " (Due: " + rs.getString("due_date") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Assignments Error: " + e.getMessage());
        }
        return assignments;
    }

    // ✅ Get attendance records
    public List<AttendanceRecord> getAttendanceRecords(String rollNumber) {
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "SELECT date, status FROM attendance WHERE roll_number = ? ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                records.add(new AttendanceRecord(
                    rs.getString("date"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}