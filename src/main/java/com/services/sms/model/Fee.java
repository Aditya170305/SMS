package com.services.sms.model;

public class Fee {
    private int id;
    private String semester;
    private String course;
    private double totalFees;
    private double paidFees;
    private double balanceFees;
    private String status;
    private String paymentDate;
    
    // Constructors
    public Fee() {
    }
    
    public Fee(int id, String semester, String course, double totalFees, 
               double paidFees, double balanceFees, String status, String paymentDate) {
        this.id = id;
        this.semester = semester;
        this.course = course;
        this.totalFees = totalFees;
        this.paidFees = paidFees;
        this.balanceFees = balanceFees;
        this.status = status;
        this.paymentDate = paymentDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public double getTotalFees() {
        return totalFees;
    }
    
    public void setTotalFees(double totalFees) {
        this.totalFees = totalFees;
    }
    
    public double getPaidFees() {
        return paidFees;
    }
    
    public void setPaidFees(double paidFees) {
        this.paidFees = paidFees;
    }
    
    public double getBalanceFees() {
        return balanceFees;
    }
    
    public void setBalanceFees(double balanceFees) {
        this.balanceFees = balanceFees;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}