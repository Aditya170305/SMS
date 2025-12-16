package com.services.sms.model;

public class StudentPerformanceDTO {
    private int rollNo;
    private String name;
    private String department; // course
    private String semester;
    private int testScore; // Placeholder if no DB table exists
    private double attendancePercentage;
    private String status;

    public StudentPerformanceDTO() {}

    // Getters and Setters
    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public int getTestScore() { return testScore; }
    public void setTestScore(int testScore) { this.testScore = testScore; }

    public double getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(double attendancePercentage) { this.attendancePercentage = attendancePercentage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}