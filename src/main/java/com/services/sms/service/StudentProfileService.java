package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Service;
import com.services.sms.model.StudentProfile;

@Service
public class StudentProfileService {
    
    // Get student profile by student ID
    public StudentProfile getStudentProfile(String studentId) {
        StudentProfile profile = null;
        String query = "SELECT * FROM student_profile WHERE student_id = ?";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, studentId);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                profile = new StudentProfile();
                profile.setStudentId(rs.getString("student_id"));
                profile.setEnrollmentNo(rs.getString("enrollment_no"));
                profile.setTitle(rs.getString("title"));
                profile.setFirstName(rs.getString("first_name"));
                profile.setMiddleName(rs.getString("middle_name"));
                profile.setLastName(rs.getString("last_name"));
                profile.setGender(rs.getString("gender"));
                profile.setEmail(rs.getString("email"));
                profile.setMobile(rs.getString("mobile"));
                profile.setPhone(rs.getString("phone"));
                profile.setDateOfBirth(rs.getDate("date_of_birth"));
                profile.setPlaceOfBirth(rs.getString("place_of_birth"));
                profile.setBloodGroup(rs.getString("blood_group"));
                profile.setFatherName(rs.getString("father_name"));
                profile.setMotherName(rs.getString("mother_name"));
                
                // Academics
                profile.setCourse(rs.getString("course"));
                profile.setBranch(rs.getString("branch"));
                profile.setSemester(rs.getString("semester"));
                profile.setSection(rs.getString("section"));
                profile.setAdmissionDate(rs.getDate("admission_date"));
                
                // Address
                profile.setAddressLine1(rs.getString("address_line1"));
                profile.setAddressLine2(rs.getString("address_line2"));
                profile.setCity(rs.getString("city"));
                profile.setState(rs.getString("state"));
                profile.setCountry(rs.getString("country"));
                profile.setPincode(rs.getString("pincode"));
                
                // Bank & Docs
                profile.setBankName(rs.getString("bank_name"));
                profile.setAccountNumber(rs.getString("account_number"));
                profile.setIfscCode(rs.getString("ifsc_code"));
                profile.setPanNumber(rs.getString("pan_number"));
                profile.setAadharNumber(rs.getString("aadhar_number"));
                profile.setProfilePhoto(rs.getString("profile_photo"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching student profile: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        
        return profile;
    }
    
    // Update student profile
    public boolean updateStudentProfile(StudentProfile profile) {
        String query = "UPDATE student_profile SET title=?, first_name=?, middle_name=?, last_name=?, " +
                      "gender=?, email=?, mobile=?, phone=?, date_of_birth=?, place_of_birth=?, " +
                      "blood_group=?, father_name=?, mother_name=?, " +
                      "address_line1=?, address_line2=?, city=?, state=?, country=?, pincode=?, " +
                      "bank_name=?, account_number=?, ifsc_code=?, pan_number=?, aadhar_number=?, " +
                      "updated_at=CURRENT_TIMESTAMP WHERE student_id=?";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            
            st.setString(1, profile.getTitle());
            st.setString(2, profile.getFirstName());
            st.setString(3, profile.getMiddleName());
            st.setString(4, profile.getLastName());
            st.setString(5, profile.getGender());
            st.setString(6, profile.getEmail());
            st.setString(7, profile.getMobile());
            st.setString(8, profile.getPhone());
            st.setDate(9, profile.getDateOfBirth());
            st.setString(10, profile.getPlaceOfBirth());
            st.setString(11, profile.getBloodGroup());
            st.setString(12, profile.getFatherName());
            st.setString(13, profile.getMotherName());
            st.setString(14, profile.getAddressLine1());
            st.setString(15, profile.getAddressLine2());
            st.setString(16, profile.getCity());
            st.setString(17, profile.getState());
            st.setString(18, profile.getCountry());
            st.setString(19, profile.getPincode());
            st.setString(20, profile.getBankName());
            st.setString(21, profile.getAccountNumber());
            st.setString(22, profile.getIfscCode());
            st.setString(23, profile.getPanNumber());
            st.setString(24, profile.getAadharNumber());
            st.setString(25, profile.getStudentId());
            
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating student profile: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}