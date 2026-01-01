package com.services.sms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.services.sms.model.RequestDTO;

@Service
public class AdminRequestsService {

    // Fetch Requests (Joined with Student Profile to get Names)
    public List<RequestDTO> getAllRequests(String statusFilter) {
        List<RequestDTO> list = new ArrayList<>();
        
        // Base Query
        String query = "SELECT r.request_id, r.enrollment_no, s.first_name, r.request_type, r.subject, r.description, r.status, r.request_date " +
                       "FROM request_master r " +
                       "JOIN student_profile s ON r.enrollment_no = s.enrollment_no ";
        
        // Dynamic Filtering
        if (!statusFilter.equals("All")) {
            query += "WHERE r.status = ? ";
        }
        query += "ORDER BY r.request_date DESC";

        Database d = new Database();
        try (Connection conn = d.db(); PreparedStatement st = conn.prepareStatement(query)) {
            
            if (!statusFilter.equals("All")) {
                st.setString(1, statusFilter);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new RequestDTO(
                    rs.getInt("request_id"),
                    rs.getString("enrollment_no"),
                    rs.getString("first_name"),
                    rs.getString("request_type"),
                    rs.getString("subject"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getDate("request_date")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Update Status (Approve/Reject)
    public boolean updateRequestStatus(int requestId, String newStatus) {
        String query = "UPDATE request_master SET status = ? WHERE request_id = ?";
        Database d = new Database();
        try (Connection conn = d.db(); PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, newStatus);
            st.setInt(2, requestId);
            return st.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}