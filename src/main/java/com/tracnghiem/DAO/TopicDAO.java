/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DAO;

import com.tracnghiem.config.JDBCUtil;
import com.tracnghiem.DTO.TopicDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author X
 */
public class TopicDAO implements InterfaceDAO<TopicDTO>{

    public static TopicDAO getInstance() {
        return new TopicDAO();
    }
    
    @Override
public boolean insert(TopicDTO t) {
    boolean rs = false;
    String sql = "INSERT INTO topics(tpTitle, tpParent, tpStatus) VALUES(?,?,?)";
    try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
        ps.setString(1, t.getTpTitle());
        ps.setInt(2, t.getTpParent());
        ps.setInt(3, t.getTpStatus());
        
        rs = ps.executeUpdate() > 0; // Nếu có ít nhất 1 dòng bị ảnh hưởng, insert thành công
    } catch (SQLException ex) {
        ex.printStackTrace();
    } 
    return rs;
}

    @Override
    public boolean update(TopicDTO t) {
        boolean rs = false;
        String sql = "UPDATE topics SET tpTitle=?, tpParent=?, tpStatus=? WHERE tpID=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getTpTitle());
            ps.setInt(2, t.getTpParent());
            ps.setInt(3, t.getTpStatus());
            ps.setInt(4, t.getTpID());
            rs = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs; 
    }

    @Override
public ArrayList<TopicDTO> selectAll() {
    ArrayList<TopicDTO> list = new ArrayList<>();
    String sql = "SELECT * FROM topics WHERE tpStatus=1 ORDER BY tpID";
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            int tpID = rs.getInt("tpID");
            String tpTitle = rs.getString("tpTitle");
            int tpParent = rs.getInt("tpParent");
            int tpStatus = rs.getInt("tpStatus");
            System.out.println("Retrieved from DB - tpID: " + tpID + ", Title: " + tpTitle + ", Parent: " + tpParent + ", Status: " + tpStatus);
            list.add(new TopicDTO(tpTitle, tpID, tpParent));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return list;
}

    @Override
public TopicDTO selectByID(String id) {
    String sql = "SELECT * FROM topics WHERE tpID = ?"; // Xóa AND tpStatus=1
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new TopicDTO(
                                                rs.getString("tpTitle"), rs.getInt("tpID"),
                        rs.getInt("tpParent"));
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
}

    @Override
    public boolean delete(String id) {
        boolean rs = false;
        String deleteSql = "UPDATE topics SET tpStatus=0 WHERE tpID = ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setString(1, id);
                rs = deleteStmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }
        public int getIDbyTopic(String topicName) {
        int topicID = -1; 
        String sql = "SELECT tpID FROM topics WHERE tpTitle = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, topicName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    topicID = rs.getInt("tpID");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return topicID;
    }

}