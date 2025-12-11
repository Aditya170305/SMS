package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.services.sms.model.Student;
import com.services.sms.model.TeacherAttendance;

/**
 * Service class for Teacher Attendance Management
 */
@Service
public class TeacherAttendanceService {

    /**
     * Get all students for a specific semester and subject
     */
    public List<Student> getStudentsBySemester(String semester, String subject) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT roll_no, name, course, semester FROM students WHERE semester = ? ORDER BY roll_no";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, semester);
            
            System.out.println("Fetching students for semester: " + semester);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Student student = new Student();
                student.setRollNo(rs.getInt("roll_no"));
                student.setName(rs.getString("name"));
                student.setCourse(rs.getString("course"));
                student.setSemester(rs.getString("semester"));
                students.add(student);
            }
            
            System.out.println("Students found: " + students.size());
            
        } catch (SQLException e) {
            System.err.println("Error in getStudentsBySemester: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return students;
    }

    /**
     * Save attendance for multiple students
     */
    public boolean saveAttendance(List<TeacherAttendance> attendanceList) {
        if (attendanceList == null || attendanceList.isEmpty()) {
            System.out.println("No attendance data to save");
            return false;
        }

        // Add ::date cast to the queries
        String checkQuery = "SELECT id FROM teacher_attendance WHERE roll_no = ? AND subject = ? AND date = ?::date";
        String insertQuery = "INSERT INTO teacher_attendance (roll_no, student_name, subject, semester, date, status, teacher_id) " +
                           "VALUES (?, ?, ?, ?, ?::date, ?, ?)";
        String updateQuery = "UPDATE teacher_attendance SET status = ?, teacher_id = ? WHERE roll_no = ? AND subject = ? AND date = ?::date";
        
        Database d = new Database();
        Connection conn = null;
        int successCount = 0;

        try {
            conn = d.db();
            conn.setAutoCommit(false);
            
            for (TeacherAttendance attendance : attendanceList) {
                PreparedStatement checkSt = conn.prepareStatement(checkQuery);
                checkSt.setInt(1, attendance.getRollNo());
                checkSt.setString(2, attendance.getSubject());
                checkSt.setString(3, attendance.getDate());
                
                ResultSet rs = checkSt.executeQuery();
                
                if (rs.next()) {
                    PreparedStatement updateSt = conn.prepareStatement(updateQuery);
                    updateSt.setString(1, attendance.getStatus());
                    updateSt.setString(2, attendance.getTeacherId());
                    updateSt.setInt(3, attendance.getRollNo());
                    updateSt.setString(4, attendance.getSubject());
                    updateSt.setString(5, attendance.getDate());
                    
                    updateSt.executeUpdate();
                    System.out.println("Updated attendance for Roll No: " + attendance.getRollNo());
                } else {
                    PreparedStatement insertSt = conn.prepareStatement(insertQuery);
                    insertSt.setInt(1, attendance.getRollNo());
                    insertSt.setString(2, attendance.getStudentName());
                    insertSt.setString(3, attendance.getSubject());
                    insertSt.setString(4, attendance.getSemester());
                    insertSt.setString(5, attendance.getDate());
                    insertSt.setString(6, attendance.getStatus());
                    insertSt.setString(7, attendance.getTeacherId());
                    
                    insertSt.executeUpdate();
                    System.out.println("Inserted attendance for Roll No: " + attendance.getRollNo());
                }
                
                successCount++;
            }
            
            conn.commit();
            System.out.println("Successfully saved " + successCount + " attendance records");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error in saveAttendance: " + e.getMessage());
            e.printStackTrace();
            
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Get attendance for a specific date, semester, and subject
     */
    public List<TeacherAttendance> getAttendanceByDate(String date, String semester, String subject) {
        List<TeacherAttendance> attendanceList = new ArrayList<>();
        // CAST date string to DATE type
        String query = "SELECT * FROM teacher_attendance WHERE date = ?::date AND semester = ? AND subject = ? ORDER BY roll_no";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, date);
            st.setString(2, semester);
            st.setString(3, subject);
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                TeacherAttendance attendance = new TeacherAttendance();
                attendance.setId(rs.getInt("id"));
                attendance.setRollNo(rs.getInt("roll_no"));
                attendance.setStudentName(rs.getString("student_name"));
                attendance.setSubject(rs.getString("subject"));
                attendance.setSemester(rs.getString("semester"));
                attendance.setDate(rs.getString("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setTeacherId(rs.getString("teacher_id"));
                attendanceList.add(attendance);
            }
            
            System.out.println("Found " + attendanceList.size() + " attendance records for " + date);
            
        } catch (SQLException e) {
            System.err.println("Error in getAttendanceByDate: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return attendanceList;
    }

    /**
     * Get all unique semesters
     */
    public List<String> getAllSemesters() {
        List<String> semesters = new ArrayList<>();
        String query = "SELECT DISTINCT semester FROM students ORDER BY semester";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                semesters.add(rs.getString("semester"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error in getAllSemesters: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return semesters;
    }

    /**
     * Get all unique subjects
     */
    public List<String> getAllSubjects() {
        List<String> subjects = new ArrayList<>();
        String query = "SELECT DISTINCT subject_name FROM subjects1 ORDER BY subject_name";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                subjects.add(rs.getString("subject_name"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error in getAllSubjects: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return subjects;
    }

    /**
     * Close database connection
     */
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}