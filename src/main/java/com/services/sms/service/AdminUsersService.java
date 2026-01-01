package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.services.sms.model.StudentUserDTO;

@Service
public class AdminUsersService {

    public List<StudentUserDTO> getStudentsByFilters(String degree, String branch, String semester) {
        List<StudentUserDTO> students = new ArrayList<>();
        
        // Note: 'Year' is usually derived from Semester, so we filter mainly by Semester
        // However, if your DB strictly separates them, we can add logic.
        // For now, filtering by Degree, Branch, and Semester is sufficient.

        String query = "SELECT first_name, enrollment_no, email, status FROM student_profile " +
                       "WHERE course = ? AND branch = ? AND semester = ?";
        
        Database d = new Database();
        try (Connection conn = d.db(); 
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, degree);
            st.setString(2, branch);
            st.setString(3, semester); // Ensure semester matches DB format (e.g., "3")
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                String name = rs.getString("first_name");
                String roll = rs.getString("enrollment_no");
                String email = rs.getString("email");
                String status = rs.getString("status");
                
                // Handle null emails gracefully
                if(email == null) email = "N/A";
                
                students.add(new StudentUserDTO(name, roll, email, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
    
 // ... existing getStudentsByFilters method ...

    // --- NEW METHOD: Insert Student into Database ---
    public boolean addStudent(String name, String degree, String branch, String semester) {
        // 1. Generate a semi-realistic Enrollment No (Prefix + random string)
        // e.g., CSE_a1b2c3d4
        String prefix = branch.toUpperCase().substring(0, Math.min(branch.length(), 3));
        String uniqueId = prefix + "_" + UUID.randomUUID().toString().substring(0, 6);
        
        // 2. Generate a dummy email
        // e.g., john.cse@college.edu
        String firstNameStr = name.split(" ")[0].toLowerCase();
        String email = firstNameStr + "." + prefix.toLowerCase() + "@college.edu";

        String query = "INSERT INTO student_profile (enrollment_no, first_name, course, branch, semester, email, status, admission_date) " +
                       "VALUES (?, ?, ?, ?, ?, ?, 'Active', CURRENT_DATE)";
        
        Database d = new Database();
        try (Connection conn = d.db(); 
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, uniqueId);
            st.setString(2, name);
            st.setString(3, degree);
            st.setString(4, branch);
            st.setString(5, semester);
            st.setString(6, email);
            
            int rows = st.executeUpdate();
            return rows > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}