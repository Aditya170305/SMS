package com.services.sms.model;

import java.sql.Date;

// Syllabus Model
class Syllabus {
    private int id;
    private String subjectCode;
    private String subjectType;
    private int moduleNumber;
    private String moduleTitle;
    private String moduleContent;
    
    public Syllabus() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public String getSubjectType() { return subjectType; }
    public void setSubjectType(String subjectType) { this.subjectType = subjectType; }
    
    public int getModuleNumber() { return moduleNumber; }
    public void setModuleNumber(int moduleNumber) { this.moduleNumber = moduleNumber; }
    
    public String getModuleTitle() { return moduleTitle; }
    public void setModuleTitle(String moduleTitle) { this.moduleTitle = moduleTitle; }
    
    public String getModuleContent() { return moduleContent; }
    public void setModuleContent(String moduleContent) { this.moduleContent = moduleContent; }
}