/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DAO;

import com.tracnghiem.config.JDBCUtil;
import com.tracnghiem.DTO.ResultDTO;
import java.sql.*;
import java.time.LocalDateTime;
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
    // Lấy giá trị rs_num lớn nhất hiện tại
    int nextRsNum = 1;
    String getMaxSql = "SELECT MAX(rs_num) FROM result";
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(getMaxSql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            nextRsNum = rs.getInt(1) + 1;
        }
    } catch (SQLException ex) {
        System.err.println("Error getting max rs_num: " + ex.getMessage());
        ex.printStackTrace();
        return false;
    }

    // Sử dụng nextRsNum trong câu lệnh INSERT
    String sql = "INSERT INTO result(rs_num, userID, exCode, rs_answers, rs_mark, rs_date) VALUES(?,?,?,?,?,?)";
    try (Connection conn = JDBCUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, nextRsNum);
        ps.setInt(2, result.getUserID());
        ps.setString(3, result.getExCode());
        ps.setString(4, result.getRs_answers());
        ps.setDouble(5, result.getRs_mark());
        ps.setTimestamp(6, Timestamp.valueOf(result.getRs_date()));
        int rowsAffected = ps.executeUpdate();
        System.out.println("Rows affected by insert: " + rowsAffected);
        if (rowsAffected == 0) {
            System.out.println("Insert failed: No rows were affected. Check constraints or database configuration.");
        }
        return rowsAffected > 0;
    } catch (SQLException ex) {
        System.err.println("SQLException in ResultDAO.insert: " + ex.getMessage());
        ex.printStackTrace();
        return false;
    }
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
                    rs.getInt("rs_num"),
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
        String sql = "SELECT * FROM result WHERE rs_num=?";
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
    public int getLastInsertedID(int userID, String exCode, LocalDateTime rsDate) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int lastID = -1;
    try {
        conn = JDBCUtil.getConnection();
        String sql = "SELECT rs_num FROM result WHERE userID = ? AND exCode = ? AND rs_date = ? ORDER BY rs_num DESC LIMIT 1";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userID);
        pstmt.setString(2, exCode);
        pstmt.setTimestamp(3, Timestamp.valueOf(rsDate));
        rs = pstmt.executeQuery();
        if (rs.next()) {
            lastID = rs.getInt("rs_num");
        } else {
            System.err.println("No record found for userID=" + userID + ", exCode=" + exCode + ", rs_date=" + rsDate);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        if (pstmt != null) {
            try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        JDBCUtil.closeConnection(conn);
    }
    return lastID;
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

