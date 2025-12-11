package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.services.sms.model.TeacherProfile;

@Service
public class TeacherProfileService {
    
    // Get teacher profile by teacher ID
    public TeacherProfile getTeacherProfile(String teacherId) {
        TeacherProfile profile = null;
        String query = "SELECT * FROM teacher_profile WHERE teacher_id = ?";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, teacherId);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                profile = new TeacherProfile();
                profile.setTeacherId(rs.getString("teacher_id"));
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
                profile.setMaritalStatus(rs.getString("marital_status"));
                profile.setFatherName(rs.getString("father_name"));
                profile.setMotherName(rs.getString("mother_name"));
                profile.setQualification(rs.getString("qualification"));
                profile.setSpecialization(rs.getString("specialization"));
                profile.setExperienceYears(rs.getInt("experience_years"));
                profile.setDepartment(rs.getString("department"));
                profile.setDesignation(rs.getString("designation"));
                profile.setEmployeeCode(rs.getString("employee_code"));
                profile.setJoiningDate(rs.getDate("joining_date"));
                profile.setEmploymentType(rs.getString("employment_type"));
                profile.setAddressLine1(rs.getString("address_line1"));
                profile.setAddressLine2(rs.getString("address_line2"));
                profile.setCity(rs.getString("city"));
                profile.setState(rs.getString("state"));
                profile.setCountry(rs.getString("country"));
                profile.setPincode(rs.getString("pincode"));
                profile.setEmergencyContactName(rs.getString("emergency_contact_name"));
                profile.setEmergencyContactNumber(rs.getString("emergency_contact_number"));
                profile.setEmergencyContactRelation(rs.getString("emergency_contact_relation"));
                profile.setBankName(rs.getString("bank_name"));
                profile.setAccountNumber(rs.getString("account_number"));
                profile.setIfscCode(rs.getString("ifsc_code"));
                profile.setPanNumber(rs.getString("pan_number"));
                profile.setAadharNumber(rs.getString("aadhar_number"));
                profile.setProfilePhoto(rs.getString("profile_photo"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching teacher profile: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return profile;
    }
    
    // Update teacher profile
    public boolean updateTeacherProfile(TeacherProfile profile) {
        String query = "UPDATE teacher_profile SET title=?, first_name=?, middle_name=?, last_name=?, " +
                      "gender=?, email=?, mobile=?, phone=?, date_of_birth=?, place_of_birth=?, " +
                      "blood_group=?, marital_status=?, father_name=?, mother_name=?, qualification=?, " +
                      "specialization=?, experience_years=?, department=?, designation=?, " +
                      "address_line1=?, address_line2=?, city=?, state=?, country=?, pincode=?, " +
                      "emergency_contact_name=?, emergency_contact_number=?, emergency_contact_relation=?, " +
                      "updated_at=CURRENT_TIMESTAMP WHERE teacher_id=?";
        
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
            st.setString(12, profile.getMaritalStatus());
            st.setString(13, profile.getFatherName());
            st.setString(14, profile.getMotherName());
            st.setString(15, profile.getQualification());
            st.setString(16, profile.getSpecialization());
            st.setInt(17, profile.getExperienceYears());
            st.setString(18, profile.getDepartment());
            st.setString(19, profile.getDesignation());
            st.setString(20, profile.getAddressLine1());
            st.setString(21, profile.getAddressLine2());
            st.setString(22, profile.getCity());
            st.setString(23, profile.getState());
            st.setString(24, profile.getCountry());
            st.setString(25, profile.getPincode());
            st.setString(26, profile.getEmergencyContactName());
            st.setString(27, profile.getEmergencyContactNumber());
            st.setString(28, profile.getEmergencyContactRelation());
            st.setString(29, profile.getTeacherId());
            
            int rowsAffected = st.executeUpdate();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating teacher profile: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}