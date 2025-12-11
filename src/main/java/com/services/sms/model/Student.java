package com.services.sms.model;

/**
 * Model class for Student information
 */
public class Student {
    private int rollNo;
    private String name;
    private String course;
    private String semester;
    private String email;
    private String phone;
    
    // Constructors
    public Student() {
    }
    
    public Student(int rollNo, String name, String course, String semester) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        this.semester = semester;
    }
    
    // Getters and Setters
    public int getRollNo() {
        return rollNo;
    }
    
    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}