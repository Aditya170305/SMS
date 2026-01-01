package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.services.sms.model.AdminDashboardData;

@Service
public class AdminDashboardService {

    public AdminDashboardData getCourseData(String degree) {
        AdminDashboardData data = new AdminDashboardData();
        Database d = new Database();
        
        // --- 1. Get Branch Statistics (For the Table) ---
        // Counts students grouped by Branch for the selected Degree
        String branchQuery = "SELECT branch, COUNT(*) as cnt FROM student_profile WHERE course = ? GROUP BY branch";
        List<AdminDashboardData.BranchStat> branchStats = new ArrayList<>();
        int totalUsers = 0;

        try {
            Connection conn = d.db(); 
            PreparedStatement st = conn.prepareStatement(branchQuery);
            st.setString(1, degree);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                String branch = rs.getString("branch");
                int count = rs.getInt("cnt");
                // Add to list and update total
                branchStats.add(new AdminDashboardData.BranchStat(branch, count));
                totalUsers += count;
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        
        data.setBranchStats(branchStats);
        data.setTotalUsers(totalUsers);

        // --- 2. Get Semester Distribution (For Bar Chart) ---
        // Creates an array of 8 zeros (for 8 semesters)
        Integer[] semCounts = new Integer[8]; 
        Arrays.fill(semCounts, 0);
        
        String semQuery = "SELECT semester, COUNT(*) as cnt FROM student_profile WHERE course = ? GROUP BY semester";
        try {
            Connection conn = d.db(); 
            PreparedStatement st = conn.prepareStatement(semQuery);
            st.setString(1, degree);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                try {
                    int sem = Integer.parseInt(rs.getString("semester"));
                    // Only map if semester is between 1 and 8
                    if (sem >= 1 && sem <= 8) {
                        semCounts[sem - 1] = rs.getInt("cnt");
                    }
                } catch (NumberFormatException e) { 
                    // Ignore invalid semester data
                }
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        data.setSemesterCounts(Arrays.asList(semCounts));

        // --- 3. Get Enrollment Trends (For Line Chart) ---
        // Group students into 4 Quarters based on Admission Month
        Integer[] trends = {0, 0, 0, 0}; 
        
        String trendQuery = "SELECT EXTRACT(MONTH FROM admission_date) as mth, COUNT(*) as cnt " +
                            "FROM student_profile WHERE course = ? GROUP BY mth";
        try {
            Connection conn = d.db(); 
            PreparedStatement st = conn.prepareStatement(trendQuery);
            st.setString(1, degree);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                int month = rs.getInt("mth");
                int count = rs.getInt("cnt");
                
                if (month >= 1 && month <= 3) trends[0] += count;      // Q1 (Jan-Mar)
                else if (month >= 4 && month <= 6) trends[1] += count; // Q2 (Apr-Jun)
                else if (month >= 7 && month <= 9) trends[2] += count; // Q3 (Jul-Sep)
                else if (month >= 10 && month <= 12) trends[3] += count; // Q4 (Oct-Dec)
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        data.setEnrollmentTrends(Arrays.asList(trends));

        return data;
    }
}