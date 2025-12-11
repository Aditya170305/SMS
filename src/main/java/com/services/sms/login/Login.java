package com.services.sms.login;

import java.sql.Connection;
import java.sql.SQLException;

import com.services.sms.service.DBConnectionAdmin;
import com.services.sms.service.DBConnectionEmployee;
import com.services.sms.service.DBConnectionStudent;
import com.services.sms.service.Database;

public class Login {
	Database d = new Database();
	Connection conn = d.db();
	public boolean verify(String username , String password , String role) throws SQLException {
		if(role.equals("admin")) {
			DBConnectionAdmin admin_user = new DBConnectionAdmin();
			return admin_user.check(username, password);
		}
		else if(role.equals("teacher")) {
			DBConnectionEmployee employee_user = new DBConnectionEmployee();
			return employee_user.check(username, password);
		}
		else if(role.equals("student")) {
			DBConnectionStudent student_user = new DBConnectionStudent();
			return student_user.check(username , password);
		}
		return false;
	}
}
