package com.services.sms.model;

public class StudentUserDTO {
    private String name;
    private String rollNo;
    private String email;
    private String status;

    public StudentUserDTO(String name, String rollNo, String email, String status) {
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.status = status;
    }

    // Getters
    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }
}