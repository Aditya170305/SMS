package com.services.sms.model;

public class Attendance {
    private String date;
    private String status;
    private String subject;  // Changed from 'sem' to 'subject'
    
    public Attendance() {
    }
    
    public Attendance(String date, String status, String subject) {
        this.date = date;
        this.status = status;
        this.subject = subject;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    @Override
    public String toString() {
        return "Attendance{" +
                "date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}