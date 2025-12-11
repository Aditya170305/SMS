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

import com.services.sms.model.Assignment;

@Service
public class TeacherDashboardService {
    
	// Get teacher's published assignments with submission count
	public List<Map<String, Object>> getTeacherAssignmentsWithSubmissions(String teacherId) {
	    List<Map<String, Object>> assignmentsList = new ArrayList<>();
	    
	    // ✅ UPDATED QUERY: Only fetch assignments for subjects this teacher teaches
	    String query = "SELECT " +
	                  "a.id, " +
	                  "a.assignment_name, " +
	                  "a.subject_code, " +
	                  "a.subject_type, " +
	                  "a.status, " +
	                  "a.due_date, " +
	                  "s.subject_name, " +
	                  "(SELECT COUNT(*) FROM assignment_submissions WHERE assignment_id = a.id) as submission_count, " +
	                  "(SELECT COUNT(*) FROM student_courses WHERE subject_code = a.subject_code AND subject_type = a.subject_type) as total_students " +
	                  "FROM assignments a " +
	                  "LEFT JOIN subjects s ON a.subject_code = s.subject_code AND a.subject_type = s.subject_type " +
	                  "INNER JOIN teacher_subjects ts ON a.subject_code = ts.subject_code AND a.subject_type = ts.subject_type " +
	                  "WHERE a.subject_type = 'Theory' AND ts.teacher_id = ? " +
	                  "ORDER BY a.id DESC";
	    
	    Database d = new Database();
	    Connection conn = null;
	    
	    try {
	        conn = d.db();
	        PreparedStatement st = conn.prepareStatement(query);
	        st.setString(1, teacherId); // ✅ Filter by teacher ID
	        
	        ResultSet rs = st.executeQuery();
	        
	        System.out.println("===== FETCHING TEACHER ASSIGNMENTS =====");
	        System.out.println("Teacher ID: " + teacherId);
	        
	        while (rs.next()) {
	            Map<String, Object> assignment = new HashMap<>();
	            assignment.put("id", rs.getInt("id"));
	            assignment.put("assignmentName", rs.getString("assignment_name"));
	            assignment.put("subjectCode", rs.getString("subject_code"));
	            assignment.put("subjectType", rs.getString("subject_type"));
	            assignment.put("subjectName", rs.getString("subject_name"));
	            assignment.put("status", rs.getString("status"));
	            assignment.put("dueDate", rs.getDate("due_date"));
	            assignment.put("submissionCount", rs.getInt("submission_count"));
	            assignment.put("totalStudents", rs.getInt("total_students"));
	            assignment.put("submissions", rs.getInt("submission_count") + " / " + rs.getInt("total_students"));
	            
	            assignmentsList.add(assignment);
	            
	            System.out.println("  - " + rs.getString("assignment_name") + 
	                             " (" + rs.getString("subject_name") + "): " + 
	                             rs.getInt("submission_count") + " / " + rs.getInt("total_students"));
	        }
	        
	        System.out.println("Total assignments found: " + assignmentsList.size());
	        System.out.println("========================================");
	        
	    } catch (SQLException e) {
	        System.err.println("Error in getTeacherAssignmentsWithSubmissions: " + e.getMessage());
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
	    
	    return assignmentsList;
	}
    
    // Get assignment details by ID (for viewing)
    public Assignment getAssignmentDetailsById(int assignmentId) {
        Assignment assignment = null;
        
        String query = "SELECT id, subject_code, subject_type, assignment_name, status, due_date, submission_date " +
                      "FROM assignments WHERE id = ?";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, assignmentId);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                assignment = new Assignment();
                assignment.setId(rs.getInt("id"));
                assignment.setSubjectCode(rs.getString("subject_code"));
                assignment.setSubjectType(rs.getString("subject_type"));
                assignment.setAssignmentName(rs.getString("assignment_name"));
                assignment.setStatus(rs.getString("status"));
                assignment.setDueDate(rs.getDate("due_date"));
                assignment.setSubmissionDate(rs.getDate("submission_date"));
                
                // Fetch questions for this assignment
                assignment.setQuestions(getQuestionsByAssignmentId(assignmentId, conn));
            }
            
        } catch (SQLException e) {
            System.err.println("Error in getAssignmentDetailsById: " + e.getMessage());
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
        
        return assignment;
    }
    
    // Helper method to get questions
    private List<com.services.sms.model.AssignmentQuestion> getQuestionsByAssignmentId(int assignmentId, Connection conn) {
        List<com.services.sms.model.AssignmentQuestion> questions = new ArrayList<>();
        String query = "SELECT id, assignment_id, question_number, question_text, marks " +
                      "FROM assignment_questions WHERE assignment_id = ? ORDER BY question_number";
        
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, assignmentId);
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                com.services.sms.model.AssignmentQuestion q = new com.services.sms.model.AssignmentQuestion();
                q.setId(rs.getInt("id"));
                q.setAssignmentId(rs.getInt("assignment_id"));
                q.setQuestionNumber(rs.getInt("question_number"));
                q.setQuestionText(rs.getString("question_text"));
                q.setMarks(rs.getInt("marks"));
                questions.add(q);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching questions for assignment " + assignmentId + ": " + e.getMessage());
        }
        
        return questions;
    }
    
 // Get dashboard statistics
    public Map<String, Object> getTeacherDashboardStats(String teacherId) {
        Map<String, Object> stats = new HashMap<>();
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            
            // ✅ Total courses taught by THIS teacher
            String coursesQuery = "SELECT COUNT(DISTINCT subject_code) as total " +
                                 "FROM teacher_subjects " +
                                 "WHERE teacher_id = ? AND subject_type = 'Theory'";
            PreparedStatement st1 = conn.prepareStatement(coursesQuery);
            st1.setString(1, teacherId);
            ResultSet rs1 = st1.executeQuery();
            if (rs1.next()) {
                stats.put("totalCourses", rs1.getInt("total"));
            }
            
            // ✅ Total students enrolled in THIS teacher's courses
            String studentsQuery = "SELECT COUNT(DISTINCT sc.student_id) as total " +
                                  "FROM student_courses sc " +
                                  "INNER JOIN teacher_subjects ts ON sc.subject_code = ts.subject_code " +
                                  "AND sc.subject_type = ts.subject_type " +
                                  "WHERE ts.teacher_id = ?";
            PreparedStatement st2 = conn.prepareStatement(studentsQuery);
            st2.setString(1, teacherId);
            ResultSet rs2 = st2.executeQuery();
            if (rs2.next()) {
                stats.put("totalStudents", rs2.getInt("total"));
            }
            
            // Average grade (placeholder - adjust based on your grading system)
            stats.put("averageGrade", 79);
            
        } catch (SQLException e) {
            System.err.println("Error in getTeacherDashboardStats: " + e.getMessage());
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
        
        return stats;
    }
}