package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.stereotype.Service;
import com.services.sms.model.AdminProfile;

@Service
public class AdminProfileService {

    // Fetch Profile
    public AdminProfile getProfile(String username) {
        AdminProfile p = null;
        String query = "SELECT * FROM admin_profile WHERE username = ?";
        Database d = new Database();
        
        try (Connection conn = d.db(); 
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                p = new AdminProfile();
                p.setUsername(rs.getString("username"));
                p.setFirstName(rs.getString("first_name"));
                p.setMiddleName(rs.getString("middle_name"));
                p.setLastName(rs.getString("last_name"));
                p.setGender(rs.getString("gender"));
                p.setDateOfBirth(rs.getDate("date_of_birth"));
                p.setBloodGroup(rs.getString("blood_group"));
                p.setEmployeeCode(rs.getString("employee_code"));
                p.setDepartment(rs.getString("department"));
                p.setDesignation(rs.getString("designation"));
                p.setJoiningDate(rs.getDate("joining_date"));
                p.setEmail(rs.getString("email"));
                p.setMobile(rs.getString("mobile"));
                p.setAddress(rs.getString("address"));
                p.setCity(rs.getString("city"));
                p.setState(rs.getString("state"));
                p.setPincode(rs.getString("pincode"));
                p.setBankName(rs.getString("bank_name"));
                p.setAccountNumber(rs.getString("account_number"));
                p.setIfscCode(rs.getString("ifsc_code"));
                p.setPanNumber(rs.getString("pan_number"));
                p.setAadharNumber(rs.getString("aadhar_number"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return p;
    }

    // Update Profile
    public boolean updateProfile(AdminProfile p) {
        String query = "UPDATE admin_profile SET first_name=?, middle_name=?, last_name=?, gender=?, " +
                       "date_of_birth=?, blood_group=?, department=?, designation=?, " +
                       "email=?, mobile=?, address=?, city=?, state=?, pincode=?, " +
                       "bank_name=?, account_number=?, ifsc_code=?, pan_number=?, aadhar_number=? " +
                       "WHERE username=?";
        
        Database d = new Database();
        try (Connection conn = d.db(); 
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, p.getFirstName());
            st.setString(2, p.getMiddleName());
            st.setString(3, p.getLastName());
            st.setString(4, p.getGender());
            st.setDate(5, p.getDateOfBirth());
            st.setString(6, p.getBloodGroup());
            st.setString(7, p.getDepartment());
            st.setString(8, p.getDesignation());
            st.setString(9, p.getEmail());
            st.setString(10, p.getMobile());
            st.setString(11, p.getAddress());
            st.setString(12, p.getCity());
            st.setString(13, p.getState());
            st.setString(14, p.getPincode());
            st.setString(15, p.getBankName());
            st.setString(16, p.getAccountNumber());
            st.setString(17, p.getIfscCode());
            st.setString(18, p.getPanNumber());
            st.setString(19, p.getAadharNumber());
            st.setString(20, p.getUsername());
            
            return st.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}