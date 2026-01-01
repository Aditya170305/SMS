package com.services.sms.controller;

import java.util.List;

//import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.sms.model.AdminDashboardData;
import com.services.sms.model.AdminProfile;
import com.services.sms.model.CourseStats;
import com.services.sms.model.RequestDTO;
import com.services.sms.model.StudentUserDTO;
import com.services.sms.service.AdminCoursesService;
import com.services.sms.service.AdminDashboardService;
import com.services.sms.service.AdminProfileService;
import com.services.sms.service.AdminRequestsService;
import com.services.sms.service.AdminUsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminDashboardService adminService;
    
    // ... existing autowired services ...
    
    @Autowired
    private AdminUsersService usersService; // Inject the new service

    @Autowired
    private AdminCoursesService coursesService; // Inject the Courses Service
    
    @Autowired
    private AdminProfileService adminProfileService;
    
    @Autowired
    private AdminRequestsService requestsService;
    
    // Load the Dashboard View
    @GetMapping("/adminDashboard")
    public String adminDashboard(HttpSession session) {
        // Optional: Add security check here
        // if (session.getAttribute("currentUser") == null) return "redirect:/";
        return "AdminDashboard";
    }

    // AJAX Endpoint: Returns JSON data for charts
    @GetMapping("/admin/dashboard/data")
    @ResponseBody
    public AdminDashboardData getDashboardData(@RequestParam("degree") String degree) {
        System.out.println("Fetching admin data for degree: " + degree);
        return adminService.getCourseData(degree);
    }
    
    @GetMapping("/adminCourses")
    public String Courses() {
    	return "Admin_Courses";
    }
    
 // AJAX: Get Stats for Courses Page (Total/Active/Inactive Students)
    @GetMapping("/admin/courses/stats")
    @ResponseBody
    public CourseStats getCourseStats(
            @RequestParam("degree") String degree,
            @RequestParam("semester") String semester) {
        
        System.out.println("Fetching course stats for: " + degree + ", " + semester);
        return coursesService.getStudentStats(degree, semester);
    }
    
 // ... (Keep existing methods) ...

 // ... inside AdminController class ...

 // --- UPDATED API: Add Course with Student Name ---
    @PostMapping("/admin/courses/add")
    @ResponseBody
    public String addCourse(
            @RequestParam("degree") String degree,
            @RequestParam("year") String year,
            @RequestParam("semester") String semester,
            @RequestParam("studentName") String studentName) { // <--- Added Parameter
        
        System.out.println("Enrolling: " + studentName + " in " + degree + " " + semester);
        
        // Pass name to service
        boolean success = coursesService.addCourseEntry(degree, semester, studentName);
        
        return success ? "success" : "error";
    }
    
 // 1. View Page
    @GetMapping("/requests")
    public String requestsPage() {
        return "AdminRequests"; // Maps to Requests.jsp
    }

    // 2. API: Get Requests List
    @GetMapping("/admin/requests/list")
    @ResponseBody
    public List<RequestDTO> getRequests(@RequestParam("status") String status) {
        return requestsService.getAllRequests(status);
    }

    // 3. API: Update Status
    @PostMapping("/admin/requests/update")
    @ResponseBody
    public String updateRequestStatus(@RequestParam("id") int id, @RequestParam("status") String status) {
        boolean success = requestsService.updateRequestStatus(id, status);
        return success ? "success" : "error";
    }
    
    @GetMapping("/users")
    public String users() {
    	return "AdminUsers";
    }
    
 // --- NEW API: Fetch Students for User Table ---
    @GetMapping("/admin/users/list")
    @ResponseBody
    public List<StudentUserDTO> getUsersList(
            @RequestParam("degree") String degree,
            @RequestParam("branch") String branch,
            @RequestParam("semester") String semester) {
        
        // Note: 'Year' is sent from frontend but Semester is the unique identifier in DB
        System.out.println("Fetching users for: " + degree + " | " + branch + " | Sem: " + semester);
        
        return usersService.getStudentsByFilters(degree, branch, semester);
    }
    
 // --- NEW API: Add Student ---
    @PostMapping("/admin/users/add")
    @ResponseBody
    public String addStudent(
            @RequestParam("name") String name,
            @RequestParam("degree") String degree,
            @RequestParam("branch") String branch,
            @RequestParam("year") String year,
            @RequestParam("semester") String semester) {
        
        System.out.println("Adding Student: " + name + " | " + degree + " | " + branch);
        
        // Call service to insert data
        boolean success = usersService.addStudent(name, degree, branch, semester);
        
        return success ? "success" : "error";
    }
    
 // 1. Show Profile Page
    @GetMapping("/adminProfile")
    public String adminProfile(HttpSession session, Model model) {
        // In a real app, get username from session. For now, we default to 'admin'
        // String username = (String) session.getAttribute("username"); 
        String username = "admin"; // Defaulting for testing
        
        AdminProfile profile = adminProfileService.getProfile(username);
        model.addAttribute("profile", profile);
        return "AdminProfile";
    }

    // 2. Update Profile (AJAX)
    @PostMapping("/admin/profile/update")
    @ResponseBody
    public String updateAdminProfile(@ModelAttribute AdminProfile profile) {
        // Ensure username is set (security measure in real apps)
        profile.setUsername("admin"); 
        
        boolean success = adminProfileService.updateProfile(profile);
        return success ? "{\"success\": true}" : "{\"success\": false, \"message\": \"Database error\"}";
    }
    
    @GetMapping("/logoutAdmin")
    public String logout() {
    	return "index";
    }
    
}