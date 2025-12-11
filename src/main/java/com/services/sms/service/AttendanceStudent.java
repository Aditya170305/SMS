package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.services.sms.model.Attendance;
import com.services.sms.model.AttendancePercentage;

@Service
public class AttendanceStudent {

    public List<Attendance> showAttendance() {
        List<Attendance> list = new ArrayList<>();
        String query = "SELECT \"date\", \"status\", subject FROM attendance";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setDate(rs.getString("date"));
                a.setStatus(rs.getString("status"));
                a.setSubject(rs.getString("subject"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error in showAttendance: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    // Fetch attendance by subject
    public List<Attendance> getAttendanceBySubject(String subject) {
        List<Attendance> list = new ArrayList<>();
        String query = "SELECT \"date\", \"status\", subject FROM attendance WHERE subject = ? ORDER BY \"date\" ASC";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subject);
            
            System.out.println("Executing query: " + query);
            System.out.println("With parameter: " + subject);
            
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Attendance a = new Attendance();
                a.setDate(rs.getString("date"));
                a.setStatus(rs.getString("status"));
                a.setSubject(rs.getString("subject"));
                list.add(a);
                
                System.out.println("Found record: " + a.getDate() + " - " + a.getStatus());
            }
            
            System.out.println("Total records retrieved: " + list.size());
            
        } catch (SQLException e) {
            System.err.println("Error in getAttendanceBySubject: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
    
    // NEW METHOD: Calculate attendance percentage for all subjects
    public Map<String, AttendancePercentage> getAttendancePercentages() {
        Map<String, AttendancePercentage> percentageMap = new HashMap<>();
        
        // SQL query to calculate percentages using GROUP BY and aggregation
        String query = "SELECT subject, " +
                       "COUNT(*) as total, " +
                       "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) as present, " +
                       "SUM(CASE WHEN status = 'Absent' THEN 1 ELSE 0 END) as absent " +
                       "FROM attendance " +
                       "GROUP BY subject";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            System.out.println("===== CALCULATING ATTENDANCE PERCENTAGES =====");
            
            while (rs.next()) {
                String subject = rs.getString("subject");
                int total = rs.getInt("total");
                int present = rs.getInt("present");
                int absent = rs.getInt("absent");
                
                // Calculate percentage
                double percentage = (total > 0) ? ((double) present / total) * 100 : 0;
                percentage = Math.round(percentage * 100.0) / 100.0; // Round to 2 decimal places
                
                // Create AttendancePercentage object
                AttendancePercentage ap = new AttendancePercentage(subject, total, present, absent, percentage);
                percentageMap.put(subject, ap);
                
                System.out.println("Subject: " + subject);
                System.out.println("  Total: " + total + " | Present: " + present + " | Absent: " + absent);
                System.out.println("  Percentage: " + percentage + "%");
            }
            
            System.out.println("==============================================");
            
        } catch (SQLException e) {
            System.err.println("Error in getAttendancePercentages: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return percentageMap;
    }
}