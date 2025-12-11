package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.services.sms.model.Fee;
import com.services.sms.model.FeeSummary;

/**
 * Service class for managing fee-related database operations
 * Similar to AttendanceStudent service
 */
@Service
public class FeeService {

    /**
     * Get all fees records from database
     * @return List of all Fee objects
     */
    public List<Fee> getAllFees() {
        List<Fee> list = new ArrayList<>();
        String query = "SELECT id, semester, course, total_fees, paid_fees, balance_fees, status, payment_date " +
                      "FROM fees ORDER BY semester DESC";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Fee fee = new Fee();
                fee.setId(rs.getInt("id"));
                fee.setSemester(rs.getString("semester"));
                fee.setCourse(rs.getString("course"));
                fee.setTotalFees(rs.getDouble("total_fees"));
                fee.setPaidFees(rs.getDouble("paid_fees"));
                fee.setBalanceFees(rs.getDouble("balance_fees"));
                fee.setStatus(rs.getString("status"));
                fee.setPaymentDate(rs.getString("payment_date"));
                list.add(fee);
            }
            
            System.out.println("Total fee records retrieved: " + list.size());
            
        } catch (SQLException e) {
            System.err.println("Error in getAllFees: " + e.getMessage());
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

    /**
     * Get fees by specific course
     * @param course The course name (e.g., "CSE 5 SEM 3 YR")
     * @return List of Fee objects for that course
     */
    public List<Fee> getFeesByCourse(String course) {
        List<Fee> list = new ArrayList<>();
        String query = "SELECT id, semester, course, total_fees, paid_fees, balance_fees, status, payment_date " +
                      "FROM fees WHERE course = ? ORDER BY semester DESC";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, course);
            
            System.out.println("Fetching fees for course: " + course);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Fee fee = new Fee();
                fee.setId(rs.getInt("id"));
                fee.setSemester(rs.getString("semester"));
                fee.setCourse(rs.getString("course"));
                fee.setTotalFees(rs.getDouble("total_fees"));
                fee.setPaidFees(rs.getDouble("paid_fees"));
                fee.setBalanceFees(rs.getDouble("balance_fees"));
                fee.setStatus(rs.getString("status"));
                fee.setPaymentDate(rs.getString("payment_date"));
                list.add(fee);
            }
            
            System.out.println("Fee records found: " + list.size());
            
        } catch (SQLException e) {
            System.err.println("Error in getFeesByCourse: " + e.getMessage());
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

    /**
     * Get aggregated fee summary for a course (by year)
     * Calculates total academic, paid, and balance fees for the entire year
     * @param course The course name
     * @return FeeSummary object with totals
     */
    public FeeSummary getFeeSummaryByCourse(String course) {
        FeeSummary summary = new FeeSummary();
        summary.setCourse(course);
        
        // Extract year from course name (e.g., "CSE 5 SEM 3 YR" -> "3 YR")
        String year = extractYear(course);
        
        // Query to sum fees for all courses in the same year
        String query = "SELECT " +
                      "SUM(total_fees) as total_academic, " +
                      "SUM(paid_fees) as total_paid, " +
                      "SUM(balance_fees) as total_balance " +
                      "FROM fees WHERE course LIKE ?";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, "%" + year); // Match all courses ending with same year
            
            System.out.println("===== CALCULATING FEE SUMMARY FOR YEAR: " + year + " =====");
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                summary.setTotalAcademic(rs.getDouble("total_academic"));
                summary.setTotalPaid(rs.getDouble("total_paid"));
                summary.setTotalBalance(rs.getDouble("total_balance"));
                
                System.out.println("Total Academic (Year): ₹ " + summary.getTotalAcademic());
                System.out.println("Total Paid (Year): ₹ " + summary.getTotalPaid());
                System.out.println("Total Balance (Year): ₹ " + summary.getTotalBalance());
            }
            
            System.out.println("=============================================");
            
        } catch (SQLException e) {
            System.err.println("Error in getFeeSummaryByCourse: " + e.getMessage());
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

        return summary;
    }
    
    /**
     * Extract year from course name
     * @param course Course name (e.g., "CSE 5 SEM 3 YR")
     * @return Year string (e.g., "3 YR")
     */
    private String extractYear(String course) {
        // Extract year pattern like "1 YR", "2 YR", "3 YR"
        if (course.contains("1 YR")) {
            return "1 YR";
        } else if (course.contains("2 YR")) {
            return "2 YR";
        } else if (course.contains("3 YR")) {
            return "3 YR";
        } else if (course.contains("4 YR")) {
            return "4 YR";
        }
        return "1 YR"; // Default
    }

    /**
     * Get fee details for a specific semester and course
     * Used for PDF receipt generation
     * @param semester The semester (e.g., "SEM 1")
     * @param course The course name
     * @return Fee object with details, or null if not found
     */
    public Fee getFeeDetailsBySemester(String semester, String course) {
        Fee fee = null;
        String query = "SELECT id, semester, course, total_fees, paid_fees, balance_fees, status, payment_date " +
                      "FROM fees WHERE semester = ? AND course = ?";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, semester);
            st.setString(2, course);
            
            System.out.println("Fetching fee details for: " + semester + " - " + course);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                fee = new Fee();
                fee.setId(rs.getInt("id"));
                fee.setSemester(rs.getString("semester"));
                fee.setCourse(rs.getString("course"));
                fee.setTotalFees(rs.getDouble("total_fees"));
                fee.setPaidFees(rs.getDouble("paid_fees"));
                fee.setBalanceFees(rs.getDouble("balance_fees"));
                fee.setStatus(rs.getString("status"));
                fee.setPaymentDate(rs.getString("payment_date"));
                
                System.out.println("Fee record found for receipt generation");
            } else {
                System.out.println("No fee record found for: " + semester + " - " + course);
            }
            
        } catch (SQLException e) {
            System.err.println("Error in getFeeDetailsBySemester: " + e.getMessage());
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

        return fee;
    }
    
    /**
     * Get all unique courses from fees table
     * Used to populate course dropdown
     * @return List of course names
     */
    public List<String> getAllCourses() {
        List<String> courses = new ArrayList<>();
        String query = "SELECT DISTINCT course FROM fees ORDER BY course";
        
        Database d = new Database();
        Connection conn = null;

        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                courses.add(rs.getString("course"));
            }
            
            System.out.println("Total courses found: " + courses.size());
            
        } catch (SQLException e) {
            System.err.println("Error in getAllCourses: " + e.getMessage());
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

        return courses;
    }
}