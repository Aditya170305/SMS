package com.services.sms.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.services.sms.login.Login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
	
	@GetMapping("/")
	public String getMethodName() {
		return "index";
	}
	@PostMapping("Login")
	public String login(@RequestParam("username") String username , @RequestParam("password") String password , @RequestParam("user") String role ,  HttpSession session) {
		System.out.println("Username : " + username);
		System.out.println("Password : " + password);
		System.out.println("Role : " + role);
		
		Login login = new Login();
		try {
			boolean result = login.verify(username, password, role);
			System.out.println("Result : " + result);
			if(result) {
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				if(role.equals("teacher")) {
					return "EmployeeDashboard";
				}
				String redirect = role.substring(0,1).toUpperCase() + role.substring(1) + "Dashboard";
				return redirect;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	
}
