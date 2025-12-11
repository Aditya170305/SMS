package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.services.sms.model.Assignment;
import com.services.sms.model.AssignmentQuestion;

@Service
public class TeacherAcademicsService extends AcademicsService {
    
    // Mark assignment as uploaded by teacher
    public boolean markAssignmentAsUploaded(int assignmentId, String teacherId) {
        String query = "UPDATE assignments SET teacher_uploaded = TRUE, upload_date = CURRENT_TIMESTAMP, uploaded_by = ? WHERE id = ?";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, teacherId);
            st.setInt(2, assignmentId);
            
            int rowsAffected = st.executeUpdate();
            System.out.println("Marked assignment " + assignmentId + " as uploaded by " + teacherId);
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error marking assignment as uploaded: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Check if syllabus is uploaded for a subject
    public boolean isSyllabusUploaded(String subjectCode, String subjectType) {
        String query = "SELECT COUNT(*) as count FROM syllabus_uploads WHERE subject_code = ? AND subject_type = ? AND is_active = TRUE";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subjectCode);
            st.setString(2, subjectType);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking syllabus upload: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return false;
    }
    
    // Mark syllabus as uploaded
    public boolean markSyllabusAsUploaded(String subjectCode, String subjectType, String fileName, String teacherId) {
        String query = "INSERT INTO syllabus_uploads (subject_code, subject_type, file_name, uploaded_by) VALUES (?, ?, ?, ?)";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subjectCode);
            st.setString(2, subjectType);
            st.setString(3, fileName);
            st.setString(4, teacherId);
            
            int rowsAffected = st.executeUpdate();
            System.out.println("Marked syllabus as uploaded for " + subjectCode + " (" + subjectType + ")");
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error marking syllabus as uploaded: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Check if notes are uploaded for a subject
    public boolean isNotesUploaded(String subjectCode, String subjectType) {
        String query = "SELECT COUNT(*) as count FROM notes_uploads WHERE subject_code = ? AND subject_type = ? AND is_active = TRUE";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subjectCode);
            st.setString(2, subjectType);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking notes upload: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return false;
    }
    
    // Mark notes as uploaded
    public boolean markNotesAsUploaded(String subjectCode, String subjectType, String fileName, String teacherId) {
        String query = "INSERT INTO notes_uploads (subject_code, subject_type, file_name, uploaded_by) VALUES (?, ?, ?, ?)";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subjectCode);
            st.setString(2, subjectType);
            st.setString(3, fileName);
            st.setString(4, teacherId);
            
            int rowsAffected = st.executeUpdate();
            System.out.println("Marked notes as uploaded for " + subjectCode + " (" + subjectType + ")");
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error marking notes as uploaded: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Override to include teacher_uploaded flag
    @Override
    public List<Assignment> getAssignmentsBySubject(String subjectCode, String subjectType) {
        List<Assignment> list = new ArrayList<>();
        String query = "SELECT id, subject_code, subject_type, assignment_name, status, due_date, submission_date, " +
                      "teacher_uploaded, upload_date, uploaded_by " +
                      "FROM assignments WHERE subject_code = ? AND subject_type = ? ORDER BY id";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subjectCode);
            st.setString(2, subjectType);
            
            System.out.println("Fetching assignments for teacher: " + subjectCode + " (" + subjectType + ")");
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Assignment a = new Assignment();
                a.setId(rs.getInt("id"));
                a.setSubjectCode(rs.getString("subject_code"));
                a.setSubjectType(rs.getString("subject_type"));
                a.setAssignmentName(rs.getString("assignment_name"));
                a.setStatus(rs.getString("status"));
                a.setDueDate(rs.getDate("due_date"));
                a.setSubmissionDate(rs.getDate("submission_date"));
                a.setTeacherUploaded(rs.getBoolean("teacher_uploaded"));
                a.setUploadDate(rs.getTimestamp("upload_date"));
                a.setUploadedBy(rs.getString("uploaded_by"));
                
                // Fetch questions for this assignment
                a.setQuestions(getQuestionsByAssignmentId(a.getId(), conn));
                
                list.add(a);
            }
            
            System.out.println("Retrieved " + list.size() + " assignments for teacher");
            
        } catch (SQLException e) {
            System.err.println("Error in getAssignmentsBySubject: " + e.getMessage());
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
    
    // Helper method (protected in parent class)
    private List<AssignmentQuestion> getQuestionsByAssignmentId(int assignmentId, Connection conn) {
        List<AssignmentQuestion> questions = new ArrayList<>();
        String query = "SELECT id, assignment_id, question_number, question_text, marks " +
                      "FROM assignment_questions WHERE assignment_id = ? ORDER BY question_number";
        
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, assignmentId);
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                AssignmentQuestion q = new AssignmentQuestion();
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
}