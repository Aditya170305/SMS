package com.services.sms.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Assignment {
    private int id;
    private String subjectCode;
    private String subjectType;
    private String assignmentName;
    private String status;
    private Date dueDate;
    private Date submissionDate;
    private List<AssignmentQuestion> questions;
    private boolean teacherUploaded;
    private Timestamp uploadDate;
    private String uploadedBy;
    
    // Default constructor
    public Assignment() {
    }
    
    // Parameterized constructor
    public Assignment(int id, String subjectCode, String subjectType, String assignmentName, 
                     String status, Date dueDate, Date submissionDate) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectType = subjectType;
        this.assignmentName = assignmentName;
        this.status = status;
        this.dueDate = dueDate;
        this.submissionDate = submissionDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSubjectCode() {
        return subjectCode;
    }
    
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    
    public String getSubjectType() {
        return subjectType;
    }
    
    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }
    
    public String getAssignmentName() {
        return assignmentName;
    }
    
    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
    
    public List<AssignmentQuestion> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<AssignmentQuestion> questions) {
        this.questions = questions;
    }
    
    public boolean isTeacherUploaded() {
        return teacherUploaded;
    }
    
    public void setTeacherUploaded(boolean teacherUploaded) {
        this.teacherUploaded = teacherUploaded;
    }
    
    public Timestamp getUploadDate() {
        return uploadDate;
    }
    
    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }
    
    public String getUploadedBy() {
        return uploadedBy;
    }
    
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
    
    @Override
    public String toString() {
        return "Assignment [id=" + id + ", subjectCode=" + subjectCode + ", subjectType=" + subjectType
                + ", assignmentName=" + assignmentName + ", status=" + status + ", dueDate=" + dueDate
                + ", submissionDate=" + submissionDate + ", teacherUploaded=" + teacherUploaded + "]";
    }
}