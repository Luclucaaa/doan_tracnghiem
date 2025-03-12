/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DAO;

import com.tracnghiem.config.JDBCUtil;
import com.tracnghiem.DTO.ResultDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author X
 */
public class ResultDAO implements InterfaceDAO<ResultDTO>{
    public static QuestionDAO getInstance() {
        return new QuestionDAO();
    }
     @Override
    public boolean insert(ResultDTO result) {
        String sql = "INSERT INTO result(userID, exCode, rs_answers, rs_mark, rs_date) VALUES(?,?,?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, result.getUserID());
            ps.setString(2, result.getExCode());
            ps.setString(3, result.getRs_answers());
            ps.setDouble(4, result.getRs_mark());
            ps.setTimestamp(5, Timestamp.valueOf(result.getRs_date()));
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(ResultDTO result) {
        String sql = "UPDATE result SET userID=?, exCode=?, rs_answers=?, rs_mark=?, rs_date=? WHERE rs_num=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, result.getUserID());
            ps.setString(2, result.getExCode());
            ps.setString(3, result.getRs_answers());
            ps.setDouble(4, result.getRs_mark());
            ps.setTimestamp(5, Timestamp.valueOf(result.getRs_date()));
            ps.setInt(6, result.getUserID());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<ResultDTO> selectAll() {
        ArrayList<ResultDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM result";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new ResultDTO(
                    rs.getInt("re_num"),
                    rs.getInt("userID"),
                    rs.getString("exCode"),
                    rs.getString("rs_answers"),
                    rs.getDouble("rs_mark"),
                    rs.getTimestamp("rs_date").toLocalDateTime()
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public ResultDTO selectByID(String id) {
        ResultDTO result = null;
        String sql = "SELECT * FROM result WHERE re_num=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = new ResultDTO(
                        rs.getInt("rs_num"),
                        rs.getInt("userID"),
                        rs.getString("exCode"),
                        rs.getString("rs_answers"),
                        rs.getDouble("rs_mark"),
                        rs.getTimestamp("rs_date").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public ArrayList<ResultDTO> selectByUserID(int userID) {
    ArrayList<ResultDTO> result = new ArrayList<>();
    String sql = "SELECT * FROM result WHERE userID = ?";
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userID);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new ResultDTO(
                    rs.getInt("rs_num"),
                    rs.getInt("userID"),
                    rs.getString("exCode"),
                    rs.getString("rs_answers"),
                    rs.getDouble("rs_mark"),
                    rs.getTimestamp("rs_date").toLocalDateTime()
                ));
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return result;
}

// Lấy kết quả theo exCode
public ArrayList<ResultDTO> selectByExCode(String exCode) {
    ArrayList<ResultDTO> result = new ArrayList<>();
    String sql = "SELECT * FROM result WHERE exCode LIKE ?";
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, exCode + "%");
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new ResultDTO(
                    rs.getInt("rs_num"),
                    rs.getInt("userID"),
                    rs.getString("exCode"),
                    rs.getString("rs_answers"),
                    rs.getDouble("rs_mark"),
                    rs.getTimestamp("rs_date").toLocalDateTime()
                ));
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return result;
}

// Lấy thống kê theo exCode (cho jTable6)
public Map<String, int[]> getStatisticsByExCode(String exCodeFilter) {
    Map<String, int[]> statistics = new HashMap<>();
    String sql = "SELECT exCode, " +
                 "COUNT(*) as total, " +
                 "SUM(CASE WHEN rs_mark >= 50 THEN 1 ELSE 0 END) as passed, " +
                 "SUM(CASE WHEN rs_mark < 50 THEN 1 ELSE 0 END) as failed " +
                 "FROM result " +
                 (exCodeFilter.isEmpty() ? "" : "WHERE exCode LIKE ?") +
                 " GROUP BY exCode";
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        if (!exCodeFilter.isEmpty()) {
            ps.setString(1, exCodeFilter + "%");
        }
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String exCode = rs.getString("exCode");
                int total = rs.getInt("total");
                int passed = rs.getInt("passed");
                int failed = rs.getInt("failed");
                statistics.put(exCode, new int[]{total, passed, failed});
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return statistics;
}

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM result WHERE rs_num=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

