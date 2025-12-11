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
import com.services.sms.model.AssignmentQuestion;
import com.services.sms.model.Subject;

@Service
public class AcademicsService {
    
    // Get all subjects
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String query = "SELECT id, subject_code, subject_name, subject_type FROM subjects ORDER BY subject_code, subject_type";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt("id"));
                s.setSubjectCode(rs.getString("subject_code"));
                s.setSubjectName(rs.getString("subject_name"));
                s.setSubjectType(rs.getString("subject_type"));
                list.add(s);
            }
            
            System.out.println("Retrieved " + list.size() + " subjects from database");
            
        } catch (SQLException e) {
            System.err.println("Error in getAllSubjects: " + e.getMessage());
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
    
    // Get assignments by subject code and type
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
            
            System.out.println("Fetching assignments for: " + subjectCode + " (" + subjectType + ")");
            
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
            
            System.out.println("Retrieved " + list.size() + " assignments");
            
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
    
    // Get questions by assignment ID (helper method)
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
    
    // Get syllabus by subject code and type
    public List<Map<String, Object>> getSyllabusBySubject(String subjectCode, String subjectType) {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "SELECT module_number, module_title, module_content " +
                      "FROM syllabus WHERE subject_code = ? AND subject_type = ? ORDER BY module_number";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subjectCode);
            st.setString(2, subjectType);
            
            System.out.println("Fetching syllabus for: " + subjectCode + " (" + subjectType + ")");
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> module = new HashMap<>();
                module.put("moduleNumber", rs.getInt("module_number"));
                module.put("moduleTitle", rs.getString("module_title"));
                module.put("moduleContent", rs.getString("module_content"));
                list.add(module);
            }
            
            System.out.println("Retrieved " + list.size() + " syllabus modules");
            
        } catch (SQLException e) {
            System.err.println("Error in getSyllabusBySubject: " + e.getMessage());
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
    
    // Get notes by subject code and type
    public List<Map<String, Object>> getNotesBySubject(String subjectCode, String subjectType) {
        List<Map<String, Object>> list = new ArrayList<>();
        String query = "SELECT note_title, note_content, note_url, uploaded_date " +
                      "FROM notes WHERE subject_code = ? AND subject_type = ? ORDER BY uploaded_date DESC";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, subjectCode);
            st.setString(2, subjectType);
            
            System.out.println("Fetching notes for: " + subjectCode + " (" + subjectType + ")");
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> note = new HashMap<>();
                note.put("noteTitle", rs.getString("note_title"));
                note.put("noteContent", rs.getString("note_content"));
                note.put("noteUrl", rs.getString("note_url"));
                note.put("uploadedDate", rs.getDate("uploaded_date"));
                list.add(note);
            }
            
            System.out.println("Retrieved " + list.size() + " notes");
            
        } catch (SQLException e) {
            System.err.println("Error in getNotesBySubject: " + e.getMessage());
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
}