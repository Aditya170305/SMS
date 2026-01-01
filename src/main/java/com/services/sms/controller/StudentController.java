
package com.services.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.sms.model.Assignment;
import com.services.sms.model.Attendance;
import com.services.sms.model.AttendancePercentage;
import com.services.sms.model.Fee;
import com.services.sms.model.FeeSummary;
import com.services.sms.model.StudentProfile; // Import StudentProfile
import com.services.sms.model.Subject;
import com.services.sms.service.AcademicsService;
import com.services.sms.service.AttendanceStudent;
import com.services.sms.service.FeeService;
import com.services.sms.service.StudentProfileService; // Import Service

@Controller
public class StudentController {

    @Autowired
    private AttendanceStudent attendanceService;
    
    @Autowired
    private AcademicsService academicsService;
    
    @Autowired
    private FeeService feeService;
    
    @Autowired
    private StudentProfileService studentProfileService; // Inject Service

    // ============================================
    // DASHBOARD
    // ============================================
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "StudentDashboard";
    }

    // ============================================
    // CLASS SCHEDULE
    // ============================================
    
    @GetMapping("/class-schedule")
    public String classSchedule() {
        return "ClassSchedule";
    }

    // ============================================
    // ATTENDANCE SECTION
    // ============================================
    
    @GetMapping("/attendance")
    public String attendance(Model m) {
        System.out.println("===== LOADING ATTENDANCE PAGE =====");
        List<Attendance> list = attendanceService.showAttendance();
        m.addAttribute("attendanceList", list);
        
        Map<String, AttendancePercentage> percentages = attendanceService.getAttendancePercentages();
        m.addAttribute("percentages", percentages);
        
        return "Attendance";
    }

    @GetMapping("/attendance/data")
    @ResponseBody
    public List<Attendance> getAttendanceData(@RequestParam("subject") String subject) {
        return attendanceService.getAttendanceBySubject(subject);
    }
    
    @GetMapping("/attendance/percentages")
    @ResponseBody
    public Map<String, AttendancePercentage> getAttendancePercentages() {
        return attendanceService.getAttendancePercentages();
    }

    // ============================================
    // ACADEMICS SECTION
    // ============================================
    
    @GetMapping("/academics")
    public String academics() {
        return "Academics";
    }
    
    @GetMapping("/academics/subjects")
    @ResponseBody
    public List<Subject> getAllSubjects() {
        return academicsService.getAllSubjects();
    }
    
    @GetMapping("/academics/assignments")
    @ResponseBody
    public List<Assignment> getAssignments(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        return academicsService.getAssignmentsBySubject(subjectCode, subjectType);
    }
    
    @GetMapping("/academics/syllabus")
    @ResponseBody
    public List<Map<String, Object>> getSyllabus(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        return academicsService.getSyllabusBySubject(subjectCode, subjectType);
    }
    
    @GetMapping("/academics/notes")
    @ResponseBody
    public List<Map<String, Object>> getNotes(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        return academicsService.getNotesBySubject(subjectCode, subjectType);
    }
    
    // ============================================
    // FEES SECTION
    // ============================================
    
    @GetMapping("/fees")
    public String fees(Model m) {
        List<String> courses = feeService.getAllCourses();
        m.addAttribute("courses", courses);
        
        if (!courses.isEmpty()) {
            String defaultCourse = courses.get(0);
            FeeSummary summary = feeService.getFeeSummaryByCourse(defaultCourse);
            m.addAttribute("summary", summary);
            
            List<Fee> fees = feeService.getFeesByCourse(defaultCourse);
            m.addAttribute("fees", fees);
        }
        return "Fees";
    }
    
    @GetMapping("/fees/bycourse")
    @ResponseBody
    public List<Fee> getFeesByCourse(@RequestParam("course") String course) {
        return feeService.getFeesByCourse(course);
    }
    
    @GetMapping("/fees/summary")
    @ResponseBody
    public FeeSummary getFeeSummary(@RequestParam("course") String course) {
        return feeService.getFeeSummaryByCourse(course);
    }
    
    @GetMapping("/fees/receipt")
    @ResponseBody
    public Fee getFeeReceipt(
            @RequestParam("semester") String semester,
            @RequestParam("course") String course) {
        return feeService.getFeeDetailsBySemester(semester, course);
    }
    
    // ============================================
    // PROFILE SECTION
    // ============================================
    
    @GetMapping("/profileStudent")
    public String profile(Model m) {
        System.out.println("===== LOADING STUDENT PROFILE =====");
        
        // In real app, get from session
        String studentId = "STUDENT001";
        
        StudentProfile profile = studentProfileService.getStudentProfile(studentId);
        
        if (profile != null) {
            m.addAttribute("profile", profile);
            System.out.println("Profile loaded: " + profile.getFullName());
        } else {
            System.out.println("Profile not found for: " + studentId);
        }
        
        return "StudentProfile"; // Corresponds to StudentProfile.jsp
    }

    @PostMapping("/student/profile/update")
    @ResponseBody
    public Map<String, Object> updateProfile(@ModelAttribute StudentProfile profile) {
        System.out.println("===== UPDATING STUDENT PROFILE =====");
        
        Map<String, Object> response = new HashMap<>();
        
        // In real app, get from session
        profile.setStudentId("STUDENT001");
        
        boolean success = studentProfileService.updateStudentProfile(profile);
        
        response.put("success", success);
        response.put("message", success ? "Profile updated successfully" : "Failed to update profile");
        
        System.out.println("Update result: " + success);
        
        return response;
    }

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }
}
