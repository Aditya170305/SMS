package com.services.sms.model;

public class ClassSchedule {
    private String day;
    private String period;
    private String time;
    private String subject;
    private String faculty;
    private boolean isLab;

    public ClassSchedule() {}

    // Getters and Setters
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public boolean isLab() { return isLab; }
    public void setLab(boolean isLab) { this.isLab = isLab; }
}