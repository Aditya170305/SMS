package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.services.sms.model.ClassSchedule;

@Service
public class ClassScheduleService {

    public List<ClassSchedule> getAllClassSchedules() {
        List<ClassSchedule> scheduleList = new ArrayList<>();
        // Order by day and then by the sort order (L1, L2...)
        String query = "SELECT * FROM class_schedule ORDER BY sort_order";
        
        Database d = new Database();
        Connection conn = null;
        
        try {
            conn = d.db();
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                ClassSchedule cs = new ClassSchedule();
                cs.setDay(rs.getString("day_of_week"));
                cs.setPeriod(rs.getString("period_code"));
                cs.setTime(rs.getString("time_slot"));
                cs.setSubject(rs.getString("subject_name"));
                cs.setFaculty(rs.getString("faculty_name"));
                cs.setLab(rs.getBoolean("is_lab"));
                
                scheduleList.add(cs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try { conn.close(); } catch(SQLException e) { e.printStackTrace(); }
            }
        }
        
        return scheduleList;
    }
}