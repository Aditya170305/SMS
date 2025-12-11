package com.services.sms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.services.sms.model.Assignment;
import com.services.sms.model.Student;
import com.services.sms.model.Subject;
import com.services.sms.model.TeacherAttendance;
import com.services.sms.model.TeacherProfile;
import com.services.sms.service.TeacherDashboardService;
import com.services.sms.service.TeacherProfileService;
import com.services.sms.service.TeacherAcademicsService;
import com.services.sms.service.TeacherAttendanceService;

@Controller
public class EmployeeController {

    @Autowired
    private TeacherDashboardService teacherService;
    
    @Autowired
    private TeacherAcademicsService teacherAcademicsService;
    
    @Autowired
    private TeacherAttendanceService teacherAttendanceService;

    @GetMapping("/dashboardEmployee")
    public String dashboard() {
        return "EmployeeDashboard";
    }

    // AJAX endpoint to fetch teacher's assignments
    @GetMapping("/teacher/assignments")
    @ResponseBody
    public List<Map<String, Object>> getTeacherAssignments() {
        System.out.println("===== TEACHER ASSIGNMENTS REQUEST =====");

        // In a real app, you'd get the teacher ID from session
        String teacherId = "TEACHER001"; // Placeholder

        List<Map<String, Object>> assignments = teacherService.getTeacherAssignmentsWithSubmissions(teacherId);

        System.out.println("Returning " + assignments.size() + " assignments");
        System.out.println("======================================");

        return assignments;
    }

    // AJAX endpoint to fetch assignment details by ID
    @GetMapping("/teacher/assignment/details")
    @ResponseBody
    public Assignment getAssignmentDetails(@RequestParam("id") int assignmentId) {
        System.out.println("===== ASSIGNMENT DETAILS REQUEST =====");
        System.out.println("Assignment ID: " + assignmentId);

        Assignment assignment = teacherService.getAssignmentDetailsById(assignmentId);

        if (assignment != null) {
            System.out.println("Assignment found: " + assignment.getAssignmentName());
        } else {
            System.out.println("Assignment not found");
        }
        System.out.println("======================================");

        return assignment;
    }

    // AJAX endpoint to fetch dashboard statistics
    @GetMapping("/teacher/stats")
    @ResponseBody
    public Map<String, Object> getTeacherStats() {
        String teacherId = "TEACHER001"; // Placeholder
        return teacherService.getTeacherDashboardStats(teacherId);
    }

    // ============================================================
    // COURSES MODULE ENDPOINTS
    // ============================================================
    
    @GetMapping("/courses")
    public String courses() {
        return "Courses";
    }
    
    // Fetch all subjects for teacher
    @GetMapping("/teacher/courses/subjects")
    @ResponseBody
    public List<Subject> getAllSubjects() {
        System.out.println("===== FETCHING SUBJECTS FOR TEACHER =====");
        List<Subject> subjects = teacherAcademicsService.getAllSubjects();
        System.out.println("Returning " + subjects.size() + " subjects");
        return subjects;
    }
    
    // Fetch assignments for a subject with upload status
    @GetMapping("/teacher/courses/assignments")
    @ResponseBody
    public List<Assignment> getCourseAssignments(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        System.out.println("===== FETCHING ASSIGNMENTS FOR TEACHER =====");
        System.out.println("Subject Code: " + subjectCode);
        System.out.println("Subject Type: " + subjectType);
        
        List<Assignment> assignments = teacherAcademicsService.getAssignmentsBySubject(subjectCode, subjectType);
        System.out.println("Returning " + assignments.size() + " assignments");
        
        return assignments;
    }
    
    // Mark assignment as uploaded
    @PostMapping("/teacher/courses/upload-assignment")
    @ResponseBody
    public Map<String, Object> uploadAssignment(
            @RequestParam("assignmentId") int assignmentId,
            @RequestParam("teacherId") String teacherId) {
        System.out.println("===== UPLOADING ASSIGNMENT =====");
        System.out.println("Assignment ID: " + assignmentId);
        System.out.println("Teacher ID: " + teacherId);
        
        Map<String, Object> response = new HashMap<>();
        
        boolean success = teacherAcademicsService.markAssignmentAsUploaded(assignmentId, teacherId);
        
        response.put("success", success);
        response.put("message", success ? "Assignment uploaded successfully" : "Failed to upload assignment");
        
        System.out.println("Upload result: " + success);
        return response;
    }
    
    // Check syllabus upload status
    @GetMapping("/teacher/courses/check-syllabus")
    @ResponseBody
    public Map<String, Boolean> checkSyllabusUploaded(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        
        boolean uploaded = teacherAcademicsService.isSyllabusUploaded(subjectCode, subjectType);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("uploaded", uploaded);
        
        return response;
    }
    
    // Mark syllabus as uploaded
    @PostMapping("/teacher/courses/upload-syllabus")
    @ResponseBody
    public Map<String, Object> uploadSyllabus(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType,
            @RequestParam("fileName") String fileName,
            @RequestParam("teacherId") String teacherId) {
        
        System.out.println("===== UPLOADING SYLLABUS =====");
        System.out.println("Subject: " + subjectCode + " (" + subjectType + ")");
        System.out.println("File: " + fileName);
        
        Map<String, Object> response = new HashMap<>();
        
        boolean success = teacherAcademicsService.markSyllabusAsUploaded(subjectCode, subjectType, fileName, teacherId);
        
        response.put("success", success);
        response.put("message", success ? "Syllabus uploaded successfully" : "Failed to upload syllabus");
        
        return response;
    }
    
    // Check notes upload status
    @GetMapping("/teacher/courses/check-notes")
    @ResponseBody
    public Map<String, Boolean> checkNotesUploaded(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        
        boolean uploaded = teacherAcademicsService.isNotesUploaded(subjectCode, subjectType);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("uploaded", uploaded);
        
        return response;
    }
    
    // Mark notes as uploaded
    @PostMapping("/teacher/courses/upload-notes")
    @ResponseBody
    public Map<String, Object> uploadNotes(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType,
            @RequestParam("fileName") String fileName,
            @RequestParam("teacherId") String teacherId) {
        
        System.out.println("===== UPLOADING NOTES =====");
        System.out.println("Subject: " + subjectCode + " (" + subjectType + ")");
        System.out.println("File: " + fileName);
        
        Map<String, Object> response = new HashMap<>();
        
        boolean success = teacherAcademicsService.markNotesAsUploaded(subjectCode, subjectType, fileName, teacherId);
        
        response.put("success", success);
        response.put("message", success ? "Notes uploaded successfully" : "Failed to upload notes");
        
        return response;
    }
    
    // Fetch syllabus for viewing template
    @GetMapping("/teacher/courses/syllabus")
    @ResponseBody
    public List<Map<String, Object>> getSyllabus(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        return teacherAcademicsService.getSyllabusBySubject(subjectCode, subjectType);
    }
    
    // Fetch notes for viewing template
    @GetMapping("/teacher/courses/notes")
    @ResponseBody
    public List<Map<String, Object>> getNotes(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("subjectType") String subjectType) {
        return teacherAcademicsService.getNotesBySubject(subjectCode, subjectType);
    }
    
    // ============================================================
    // ATTENDANCE MODULE ENDPOINTS - NEW SECTION
    // ============================================================
    
    @GetMapping("/attendance1")
    public String attendanceEmployee(Model m) {
        System.out.println("===== LOADING TEACHER ATTENDANCE PAGE =====");
        
        // Get all semesters and subjects for dropdowns
        List<String> semesters = teacherAttendanceService.getAllSemesters();
        List<String> subjects = teacherAttendanceService.getAllSubjects();
        
        m.addAttribute("semesters", semesters);
        m.addAttribute("subjects", subjects);
        
        System.out.println("Semesters: " + semesters.size());
        System.out.println("Subjects: " + subjects.size());
        System.out.println("==========================================");
        
        return "AttendanceEmployee";
    }
    
    // AJAX endpoint to get students by semester
    @GetMapping("/teacher/attendance/students")
    @ResponseBody
    public List<Student> getStudentsBySemester(
            @RequestParam("semester") String semester,
            @RequestParam("subject") String subject) {
        System.out.println("===== FETCHING STUDENTS =====");
        System.out.println("Semester: " + semester);
        System.out.println("Subject: " + subject);
        
        List<Student> students = teacherAttendanceService.getStudentsBySemester(semester, subject);
        
        System.out.println("Students found: " + students.size());
        System.out.println("=============================");
        
        return students;
    }
    
    // AJAX endpoint to save attendance
    @PostMapping("/teacher/attendance/save")
    @ResponseBody
    public Map<String, Object> saveAttendance(@RequestBody Map<String, Object> payload) {
        System.out.println("===== SAVING ATTENDANCE =====");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Extract data from payload
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> attendanceData = (List<Map<String, Object>>) payload.get("attendance");
            String teacherId = (String) payload.get("teacherId");
            String date = (String) payload.get("date");
            String semester = (String) payload.get("semester");
            String subject = (String) payload.get("subject");
            
            // Convert to TeacherAttendance objects
            List<TeacherAttendance> attendanceList = new ArrayList<>();
            
            for (Map<String, Object> data : attendanceData) {
                TeacherAttendance attendance = new TeacherAttendance();
                attendance.setRollNo(Integer.parseInt(data.get("rollNo").toString()));
                attendance.setStudentName((String) data.get("name"));
                attendance.setSubject(subject);
                attendance.setSemester(semester);
                attendance.setDate(date);
                attendance.setStatus((String) data.get("status"));
                attendance.setTeacherId(teacherId);
                
                attendanceList.add(attendance);
            }
            
            // Save to database
            boolean success = teacherAttendanceService.saveAttendance(attendanceList);
            
            response.put("success", success);
            response.put("message", success ? 
                "Attendance saved successfully for " + attendanceList.size() + " students" : 
                "Failed to save attendance");
            
            System.out.println("Save result: " + success);
            
        } catch (Exception e) {
            System.err.println("Error saving attendance: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
        }
        
        System.out.println("============================");
        return response;
    }
    
    // AJAX endpoint to get attendance for a specific date
    @GetMapping("/teacher/attendance/getbydate")
    @ResponseBody
    public List<TeacherAttendance> getAttendanceByDate(
            @RequestParam("date") String date,
            @RequestParam("semester") String semester,
            @RequestParam("subject") String subject) {
        System.out.println("===== FETCHING ATTENDANCE BY DATE =====");
        System.out.println("Date: " + date);
        System.out.println("Semester: " + semester);
        System.out.println("Subject: " + subject);
        
        List<TeacherAttendance> attendance = teacherAttendanceService.getAttendanceByDate(date, semester, subject);
        
        System.out.println("Records found: " + attendance.size());
        System.out.println("======================================");
        
        return attendance;
    }
    
    @Autowired
    private TeacherProfileService teacherProfileService;

    @GetMapping("/profile")
    public String profile(Model m) {
        System.out.println("===== LOADING TEACHER PROFILE =====");
        
        // In real app, get from session
        String teacherId = "TEACHER001";
        
        TeacherProfile profile = teacherProfileService.getTeacherProfile(teacherId);
        
        if (profile != null) {
            m.addAttribute("profile", profile);
            System.out.println("Profile loaded: " + profile.getFullName());
        } else {
            System.out.println("Profile not found for: " + teacherId);
        }
        
        return "EmployeeProfile";
    }

    @PostMapping("/teacher/profile/update")
    @ResponseBody
    public Map<String, Object> updateProfile(@ModelAttribute TeacherProfile profile) {
        System.out.println("===== UPDATING TEACHER PROFILE =====");
        
        Map<String, Object> response = new HashMap<>();
        
        // In real app, get from session
        profile.setTeacherId("TEACHER001");
        
        boolean success = teacherProfileService.updateTeacherProfile(profile);
        
        response.put("success", success);
        response.put("message", success ? "Profile updated successfully" : "Failed to update profile");
        
        System.out.println("Update result: " + success);
        
        return response;
    }
    
    @GetMapping("/students")
    public String students() {
    	return "Student";
    }
    @GetMapping("/logoutEmployee")
    public String logout() {
    	return "index";
    }
}