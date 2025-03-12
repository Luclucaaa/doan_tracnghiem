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
import java.util.List;

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

    public List<AnswerDTO> selectByQuestionID(int qID) throws Exception {
    List<AnswerDTO> answers = new ArrayList<>();
    Connection conn = JDBCUtil.getConnection();
    if (conn == null) {
        System.err.println("Failed to get database connection!");
        return new ArrayList<>();
    }

    String sql = "SELECT * FROM answers WHERE qID = ? AND awStatus = 1";
    System.out.println("Executing query: " + sql + " with qID = " + qID);
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, qID);
        ResultSet rs = pstmt.executeQuery();
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            AnswerDTO answer = new AnswerDTO();
            answer.setAwID(rs.getInt("awID"));
            answer.setQID(rs.getInt("qID"));
            answer.setAwContent(rs.getString("awContent"));
            answer.setAwPictures(rs.getString("awPictures"));
            answer.setIsRight(rs.getInt("isRight"));
            answer.setAwStatus(rs.getInt("awStatus"));
            answers.add(answer);
            System.out.println("Found answer: awID = " + answer.getAwID() + ", content = " + answer.getAwContent());
        }
        System.out.println("Total rows retrieved for qID " + qID + ": " + rowCount);
        rs.close();
    } catch (Exception e) {
        System.err.println("SQL Error: " + e.getMessage());
        throw e;
    } finally {
        JDBCUtil.closeConnection(conn);
    }
    System.out.println("Answers retrieved for qID " + qID + ": " + answers.size());
    return answers;
}
}