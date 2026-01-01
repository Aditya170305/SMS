package com.services.sms.model;

import java.util.List;

public class AdminDashboardData {
    private int totalUsers;
    private List<BranchStat> branchStats; // For the Table (Branch Name + Count)
    private List<Integer> semesterCounts; // For Bar Chart (Students per Semester)
    private List<Integer> enrollmentTrends; // For Line Chart (Quarterly Trends)

    // Inner class to hold Table Row data
    public static class BranchStat {
        private String branch;
        private int count;
        
        public BranchStat(String branch, int count) {
            this.branch = branch;
            this.count = count;
        }
        // Getters
        public String getBranch() { return branch; }
        public int getCount() { return count; }
    }

    // Getters and Setters for Main Class
    public int getTotalUsers() { return totalUsers; }
    public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }
    
    public List<BranchStat> getBranchStats() { return branchStats; }
    public void setBranchStats(List<BranchStat> branchStats) { this.branchStats = branchStats; }
    
    public List<Integer> getSemesterCounts() { return semesterCounts; }
    public void setSemesterCounts(List<Integer> semesterCounts) { this.semesterCounts = semesterCounts; }
    
    public List<Integer> getEnrollmentTrends() { return enrollmentTrends; }
    public void setEnrollmentTrends(List<Integer> enrollmentTrends) { this.enrollmentTrends = enrollmentTrends; }
}