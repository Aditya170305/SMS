package com.services.sms.model;

public class Subject {
    private int id;
    private String subjectCode;
    private String subjectName;
    private String subjectType;
    
    public Subject() {}
    
    public Subject(int id, String subjectCode, String subjectName, String subjectType) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
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
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public String getSubjectType() {
        return subjectType;
    }
    
    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }
    
    @Override
    public String toString() {
        return "Subject [id=" + id + ", subjectCode=" + subjectCode + ", subjectName=" + subjectName
                + ", subjectType=" + subjectType + "]";
    }
}