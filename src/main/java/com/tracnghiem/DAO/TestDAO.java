/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DAO;


import com.tracnghiem.DTO.TestDTO;
import java.sql.*;
import java.util.ArrayList;
import com.tracnghiem.config.JDBCUtil; // Import JDBCUtil
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TestDAO implements InterfaceDAO<TestDTO> {
    private Connection conn;

    public TestDAO() {
        // Lấy kết nối từ JDBCUtil
        conn = JDBCUtil.getConnection();
        if (conn == null) {
            System.err.println("Không thể khởi tạo kết nối trong TestDAO!");
        }
    }
    
    @Override
    public boolean insert(TestDTO test) {
    String sql = "INSERT INTO test (testCode, testTitle, testTime, tpID, num_easy, num_medium, num_diff, testLimit, testDate, testStatus) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, test.getTestCode());
        ps.setString(2, test.getTestTitle());
        ps.setInt(3, test.getTestTime());
        ps.setInt(4, test.getTpID());
        ps.setInt(5, test.getNum_easy());
        ps.setInt(6, test.getNum_medium());
        ps.setInt(7, test.getNum_diff());
        ps.setInt(8, test.getTestLimit());
        ps.setDate(9, test.getTestDate());
        ps.setInt(10, test.getTestStatus());
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                test.setTestID(rs.getInt(1)); // Lấy testID tự động sinh
            }
            rs.close();
            return true;
        }
        return false;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    @Override
    public boolean update(TestDTO t) {
        String sql = "UPDATE test SET testTitle=?, testTime=?, tpID=?, num_easy=?, num_medium=?, num_diff=?, testLimit=?, testDate=?, testStatus=? " +
                     "WHERE testCode=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, t.getTestTitle());
            pstmt.setInt(2, t.getTestTime());
            pstmt.setInt(3, t.getTpID());
            pstmt.setInt(4, t.getNum_easy());
            pstmt.setInt(5, t.getNum_medium());
            pstmt.setInt(6, t.getNum_diff());
            pstmt.setInt(7, t.getTestLimit());
            pstmt.setDate(8, t.getTestDate());
            pstmt.setInt(9, t.getTestStatus());
            pstmt.setString(10, t.getTestCode());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật bài thi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<TestDTO> selectAll() {
        ArrayList<TestDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM test";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TestDTO test = new TestDTO();
                test.setTestID(rs.getInt("testID"));
                test.setTestCode(rs.getString("testCode"));
                test.setTestTitle(rs.getString("testTitle"));
                test.setTestTime(rs.getInt("testTime"));
                test.setTpID(rs.getInt("tpID"));
                test.setNum_easy(rs.getInt("num_easy"));
                test.setNum_medium(rs.getInt("num_medium"));
                test.setNum_diff(rs.getInt("num_diff"));
                test.setTestLimit(rs.getInt("testLimit"));
                test.setTestDate(rs.getDate("testDate"));
                test.setTestStatus(rs.getInt("testStatus"));
                list.add(test);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách bài thi: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
public TestDTO selectByID(String id) {
    String sql = "SELECT * FROM test WHERE testCode=?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery(); // Sửa ở đây
        if (rs.next()) {
            TestDTO test = new TestDTO();
            test.setTestID(rs.getInt("testID"));
            test.setTestCode(rs.getString("testCode"));
            test.setTestTitle(rs.getString("testTitle"));
            test.setTestTime(rs.getInt("testTime"));
            test.setTpID(rs.getInt("tpID"));
            test.setNum_easy(rs.getInt("num_easy"));
            test.setNum_medium(rs.getInt("num_medium"));
            test.setNum_diff(rs.getInt("num_diff"));
            test.setTestLimit(rs.getInt("testLimit"));
            test.setTestDate(rs.getDate("testDate"));
            test.setTestStatus(rs.getInt("testStatus"));
            return test;
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi lấy bài thi theo ID: " + e.getMessage());
        e.printStackTrace();
    }
    return null;
}

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM test WHERE testCode=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa bài thi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Thêm phương thức lấy số lượt làm bài còn lại từ user_test_limits
    public int getUserRemainingAttempts(int userID, String testCode) {
        String sql = "SELECT remainingAttempts FROM user_test_limits WHERE userID = ? AND testCode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setString(2, testCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("remainingAttempts");
            }
            return -1; // Chưa có bản ghi
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy số lượt làm bài còn lại: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // Thêm phương thức chèn bản ghi mới vào user_test_limits
    public void insertUserTestLimit(int userID, String testCode, int remainingAttempts) {
        String sql = "INSERT INTO user_test_limits (userID, testCode, remainingAttempts) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setString(2, testCode);
            pstmt.setInt(3, remainingAttempts);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi chèn bản ghi vào user_test_limits: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Thêm phương thức cập nhật số lượt làm bài còn lại
    public boolean updateUserRemainingAttempts(int userID, String testCode, int newAttempts) {
        String sql = "UPDATE user_test_limits SET remainingAttempts = ? WHERE userID = ? AND testCode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newAttempts);
            pstmt.setInt(2, userID);
            pstmt.setString(3, testCode);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật số lượt làm bài: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Đóng kết nối khi không cần thiết (tùy chọn)
    public void closeConnection() {
        JDBCUtil.closeConnection(conn);
    }
}
