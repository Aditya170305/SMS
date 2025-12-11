package com.services.sms.model;

/**
 * Model class for Teacher Attendance Record
 */
public class TeacherAttendance {
    private int id;
    private int rollNo;
    private String studentName;
    private String subject;
    private String semester;
    private String date;
    private String status; // "Present" or "Absent"
    private String teacherId;
    
    // Constructors
    public TeacherAttendance() {
    }
    
    public TeacherAttendance(int rollNo, String studentName, String subject, 
                            String semester, String date, String status, String teacherId) {
        this.rollNo = rollNo;
        this.studentName = studentName;
        this.subject = subject;
        this.semester = semester;
        this.date = date;
        this.status = status;
        this.teacherId = teacherId;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getRollNo() {
        return rollNo;
    }
    
    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
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
    
    public String getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    
    @Override
    public String toString() {
        return "TeacherAttendance{" +
                "id=" + id +
                ", rollNo=" + rollNo +
                ", studentName='" + studentName + '\'' +
                ", subject='" + subject + '\'' +
                ", semester='" + semester + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}