package com.services.sms.model;

/**
 * Model class to represent aggregated fee summary for a course
 * Used to display total academic fees, paid fees, and balance
 */
public class FeeSummary {
    private String course;
    private double totalAcademic;
    private double totalPaid;
    private double totalBalance;
    
    // Default Constructor
    public FeeSummary() {
    }
    
    // Parameterized Constructor
    public FeeSummary(String course, double totalAcademic, double totalPaid, double totalBalance) {
        this.course = course;
        this.totalAcademic = totalAcademic;
        this.totalPaid = totalPaid;
        this.totalBalance = totalBalance;
    }
    
    // Getters and Setters
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public double getTotalAcademic() {
        return totalAcademic;
    }
    
    public void setTotalAcademic(double totalAcademic) {
        this.totalAcademic = totalAcademic;
    }
    
    public double getTotalPaid() {
        return totalPaid;
    }
    
    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }
    
    public double getTotalBalance() {
        return totalBalance;
    }
    
    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }
    
    // toString method for debugging
    @Override
    public String toString() {
        return "FeeSummary{" +
                "course='" + course + '\'' +
                ", totalAcademic=" + totalAcademic +
                ", totalPaid=" + totalPaid +
                ", totalBalance=" + totalBalance +
                '}';
    }
}