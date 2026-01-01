package com.services.sms.model;

import java.sql.Date;

public class RequestDTO {
    private int requestId;
    private String enrollmentNo;
    private String studentName; // Fetched from student_profile
    private String requestType;
    private String subject;
    private String description;
    private String status;
    private Date requestDate;

    public RequestDTO(int requestId, String enrollmentNo, String studentName, String requestType, String subject, String description, String status, Date requestDate) {
        this.requestId = requestId;
        this.enrollmentNo = enrollmentNo;
        this.studentName = studentName;
        this.requestType = requestType;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.requestDate = requestDate;
    }

    // Getters
    public int getRequestId() { return requestId; }
    public String getEnrollmentNo() { return enrollmentNo; }
    public String getStudentName() { return studentName; }
    public String getRequestType() { return requestType; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Date getRequestDate() { return requestDate; }
}