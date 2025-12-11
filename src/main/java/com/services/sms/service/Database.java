package com.services.sms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

//@Component
public class Database {
	Connection conn = null;
	public Connection db() {
		String url = "jdbc:postgresql://localhost:5432/sms";
		String username = "postgres";
		String password = "17032005";
		try {
			conn = DriverManager.getConnection(url , username , password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}
}
