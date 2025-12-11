package com.services.sms.model;

import java.sql.Date;

class Notes {
    private int id;
    private String subjectCode;
    private String subjectType;
    private String noteTitle;
    private String noteContent;
    private String noteUrl;
    private Date uploadedDate;
    
    public Notes() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public String getSubjectType() { return subjectType; }
    public void setSubjectType(String subjectType) { this.subjectType = subjectType; }
    
    public String getNoteTitle() { return noteTitle; }
    public void setNoteTitle(String noteTitle) { this.noteTitle = noteTitle; }
    
    public String getNoteContent() { return noteContent; }
    public void setNoteContent(String noteContent) { this.noteContent = noteContent; }
    
    public String getNoteUrl() { return noteUrl; }
    public void setNoteUrl(String noteUrl) { this.noteUrl = noteUrl; }
    
    public Date getUploadedDate() { return uploadedDate; }
    public void setUploadedDate(Date uploadedDate) { this.uploadedDate = uploadedDate; }
}