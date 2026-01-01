package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.services.sms.model.CourseStats;

@Service
public class AdminCoursesService {

    public CourseStats getStudentStats(String degree, String semester) {
        int total = 0;
        int active = 0;
        int inactive = 0;
        
        // Extract numeric semester from string "Semester 1" -> "1"
        String semNumber = semester.replace("Semester ", "").trim();

        String query = "SELECT status, COUNT(*) as cnt FROM student_profile WHERE course = ? AND semester = ? GROUP BY status";
        
        Database d = new Database();
        try (Connection conn = d.db(); 
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, degree);
            st.setString(2, semNumber);
            
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("cnt");
                
                if ("Active".equalsIgnoreCase(status)) {
                    active = count;
                } else {
                    inactive += count; // Count anything else as inactive
                }
            }
            total = active + inactive;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new CourseStats(total, active, inactive);
    }
    
 // ... (Keep your existing getStudentStats method) ...

    // --- NEW METHOD: Insert a new student to add/increment course ---
    public boolean addCourseEntry(String degree, String semester) {
        // Generate a unique dummy enrollment ID
        String uniqueId = "NEW_" + UUID.randomUUID().toString().substring(0, 8);
        
        // Extract numeric semester (e.g., "Semester 1" -> "1")
        String semNumber = semester.replace("Semester ", "").trim();

        String query = "INSERT INTO student_profile (enrollment_no, first_name, course, semester, admission_date, status) " +
                       "VALUES (?, 'New Student', ?, ?, CURRENT_DATE, 'Active')";
        
        Database d = new Database();
        try (Connection conn = d.db(); 
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, uniqueId);
            st.setString(2, degree);
            st.setString(3, semNumber);
            
            int rows = st.executeUpdate();
            return rows > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
 // --- UPDATED METHOD: Insert with Student Name ---
    public boolean addCourseEntry(String degree, String semester, String studentName) {
        // Generate unique ID
        String uniqueId = "NEW_" + UUID.randomUUID().toString().substring(0, 8);
        
        // Extract numeric semester
        String semNumber = semester.replace("Semester ", "").trim();

        // Query uses 'studentName' now
        String query = "INSERT INTO student_profile (enrollment_no, first_name, course, semester, admission_date, status) " +
                       "VALUES (?, ?, ?, ?, CURRENT_DATE, 'Active')";
        
        Database d = new Database();
        try (Connection conn = d.db(); 
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, uniqueId);
            st.setString(2, studentName); // <--- Insert the real name here
            st.setString(3, degree);
            st.setString(4, semNumber);
            
            int rows = st.executeUpdate();
            return rows > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}