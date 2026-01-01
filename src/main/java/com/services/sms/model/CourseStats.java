package com.services.sms.model;

public class CourseStats {
    private int totalStudents;
    private int activeStudents;
    private int inactiveStudents;

    public CourseStats(int total, int active, int inactive) {
        this.totalStudents = total;
        this.activeStudents = active;
        this.inactiveStudents = inactive;
    }
    // Getters
    public int getTotalStudents() { return totalStudents; }
    public int getActiveStudents() { return activeStudents; }
    public int getInactiveStudents() { return inactiveStudents; }
}