    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DAO;

import com.tracnghiem.config.JDBCUtil;
import com.tracnghiem.DTO.AnswerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author X
 */
public class AnswerDAO implements InterfaceDAO<AnswerDTO> {

    public static AnswerDAO getInstance() {
        return new AnswerDAO();
    }

    @Override
    public boolean insert(AnswerDTO t) {
        boolean rs = false;
        String sql = "INSERT INTO answers(qID, awContent, awPictures, isRight, awStatus) VALUES(?,?,?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getQID());
            ps.setString(2, t.getAwContent());
            ps.setString(3, t.getAwPictures());
            ps.setInt(4, t.getIsRight());
            ps.setInt(5, t.getAwStatus());
            rs = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    @Override
    public boolean update(AnswerDTO t) {
        boolean rs = false;
        String sql = "UPDATE answers SET qID=?, awContent=?, awPictures=?, isRight=?, awStatus=? WHERE awID=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getQID());
            ps.setString(2, t.getAwContent());
            ps.setString(3, t.getAwPictures());
            ps.setInt(4, t.getIsRight());
            ps.setInt(5, t.getAwStatus());
            ps.setInt(6, t.getAwID());
            rs = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    @Override
    public ArrayList<AnswerDTO> selectAll() {
        ArrayList<AnswerDTO> rs = new ArrayList<>();
        String sql = "SELECT * FROM answers WHERE awStatus=1";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rsSet = ps.executeQuery()) {
            while (rsSet.next()) {
                rs.add(new AnswerDTO(rsSet.getInt("awID"),
                                     rsSet.getInt("qID"),
                                     rsSet.getString("awContent"),
                                     rsSet.getString("awPictures"),
                                     rsSet.getInt("isRight"),
                                     rsSet.getInt("awStatus")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    @Override
    public AnswerDTO selectByID(String id) {
        AnswerDTO rs = null;
        String sql = "SELECT * FROM answers WHERE awID=? AND awStatus=1";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rsSet = ps.executeQuery()) {
                if (rsSet.next()) {
                    rs = new AnswerDTO(rsSet.getInt("awID"),
                                       rsSet.getInt("qID"),
                                       rsSet.getString("awContent"),
                                       rsSet.getString("awPictures"),
                                       rsSet.getInt("isRight"),
                                       rsSet.getInt("awStatus"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    @Override
    public boolean delete(String id) {
        boolean rs = false;
        String sql = "UPDATE answers SET awStatus=0 WHERE awID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            rs = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    // Thêm phương thức để lấy đáp án theo qID
    public ArrayList<AnswerDTO> selectByQuestionID(int qID) {
        ArrayList<AnswerDTO> rs = new ArrayList<>();
        String sql = "SELECT * FROM answers WHERE qID = ? AND awStatus = 1";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qID);
            try (ResultSet rsSet = ps.executeQuery()) {
                while (rsSet.next()) {
                    rs.add(new AnswerDTO(rsSet.getInt("awID"),
                                         rsSet.getInt("qID"),
                                         rsSet.getString("awContent"),
                                         rsSet.getString("awPictures"),
                                         rsSet.getInt("isRight"),
                                         rsSet.getInt("awStatus")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }
}