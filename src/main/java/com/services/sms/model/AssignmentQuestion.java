package com.services.sms.model;

public class AssignmentQuestion {
    private int id;
    private int assignmentId;
    private int questionNumber;
    private String questionText;
    private int marks;
    
    // Default constructor
    public AssignmentQuestion() {
    }
    
    // Parameterized constructor
    public AssignmentQuestion(int id, int assignmentId, int questionNumber, String questionText, int marks) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.marks = marks;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getAssignmentId() {
        return assignmentId;
    }
    
    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }
    
    public int getQuestionNumber() {
        return questionNumber;
    }
    
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public int getMarks() {
        return marks;
    }
    
    public void setMarks(int marks) {
        this.marks = marks;
    }
    
    @Override
    public String toString() {
        return "AssignmentQuestion [id=" + id + ", assignmentId=" + assignmentId + ", questionNumber=" 
                + questionNumber + ", questionText=" + questionText + ", marks=" + marks + "]";
    }
}