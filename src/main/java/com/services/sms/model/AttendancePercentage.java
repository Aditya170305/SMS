package com.services.sms.model;

	public class AttendancePercentage {
	    private String subject;
	    private int totalClasses;
	    private int presentCount;
	    private int absentCount;
	    private double percentage;
	    
	    public AttendancePercentage() {
	    }
	    
	    public AttendancePercentage(String subject, int totalClasses, int presentCount, int absentCount, double percentage) {
	        this.subject = subject;
	        this.totalClasses = totalClasses;
	        this.presentCount = presentCount;
	        this.absentCount = absentCount;
	        this.percentage = percentage;
	    }
	    
	    public String getSubject() {
	        return subject;
	    }
	    
	    public void setSubject(String subject) {
	        this.subject = subject;
	    }
	    
	    public int getTotalClasses() {
	        return totalClasses;
	    }
	    
	    public void setTotalClasses(int totalClasses) {
	        this.totalClasses = totalClasses;
	    }
	    
	    public int getPresentCount() {
	        return presentCount;
	    }
	    
	    public void setPresentCount(int presentCount) {
	        this.presentCount = presentCount;
	    }
	    
	    public int getAbsentCount() {
	        return absentCount;
	    }
	    
	    public void setAbsentCount(int absentCount) {
	        this.absentCount = absentCount;
	    }
	    
	    public double getPercentage() {
	        return percentage;
	    }
	    
	    public void setPercentage(double percentage) {
	        this.percentage = percentage;
	    }
	    
	    @Override
	    public String toString() {
	        return "AttendancePercentage{" +
	                "subject='" + subject + '\'' +
	                ", totalClasses=" + totalClasses +
	                ", presentCount=" + presentCount +
	                ", absentCount=" + absentCount +
	                ", percentage=" + percentage +
	                '}';
	    }
	}
