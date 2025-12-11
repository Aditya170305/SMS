package com.services.sms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class DBConnectionAdmin {
	Connection conn = null;
	public DBConnectionAdmin() {
		String url = "jdbc:postgresql://localhost:5432/sms";
		String username = "postgres";
		String password = "17032005";
		try {
			conn = DriverManager.getConnection(url , username , password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean check(String uname , String pass) throws SQLException {
		String query = "select * from adminlogin where username = ? and password = ?";
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, uname);
		st.setString(2, pass);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
	}
}
