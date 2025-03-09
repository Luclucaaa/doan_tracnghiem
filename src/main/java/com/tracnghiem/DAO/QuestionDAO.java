/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DAO;

import com.tracnghiem.config.JDBCUtil;
import com.tracnghiem.DTO.QuestionDTO;
import com.tracnghiem.DTO.AnswerDTO;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author X
 */
public class QuestionDAO implements InterfaceDAO<QuestionDTO> {
    private Connection conn;

    public static QuestionDAO getInstance() {
        return new QuestionDAO();
    }

    public QuestionDAO() {
        this.conn = JDBCUtil.getConnection();
        if (this.conn == null) {
            System.err.println("Không thể kết nối tới database! Vui lòng kiểm tra cấu hình trong JDBCUtil.");
        }
    }

    @Override
    public boolean insert(QuestionDTO t) {
        if (conn == null) {
            System.err.println("Không thể thực hiện insert: Kết nối database là null!");
            return false;
        }
        String sql = "INSERT INTO questions (qContent, qPictures, qtopicID, qlevel, qStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getQContent());
            ps.setString(2, t.getQPicture());
            ps.setInt(3, t.getTopicID());
            ps.setString(4, t.getLevel());
            ps.setInt(5, t.getStatus()); // Sử dụng getStatus() từ QuestionDTO
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        t.setQID(generatedKeys.getInt(1));
                    }
                }
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(QuestionDTO t) {
        if (conn == null) {
            System.err.println("Không thể thực hiện update: Kết nối database là null!");
            return false;
        }
        String sql = "UPDATE questions SET qContent = ?, qPictures = ?, qtopicID = ?, qlevel = ?, qStatus = ? WHERE qID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getQContent());
            ps.setString(2, t.getQPicture());
            ps.setInt(3, t.getTopicID());
            ps.setString(4, t.getLevel());
            ps.setInt(5, t.getStatus()); // Sử dụng getStatus() từ QuestionDTO
            ps.setInt(6, t.getQID());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<QuestionDTO> selectAll() {
        if (conn == null) {
            System.err.println("Không thể thực hiện selectAll: Kết nối database là null!");
            return new ArrayList<>();
        }
        ArrayList<QuestionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE qStatus = 1"; // Sử dụng qStatus thay vì status
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                QuestionDTO question = new QuestionDTO(
                    rs.getInt("qID"),
                    rs.getString("qContent"),
                    rs.getString("qPictures"),
                    rs.getInt("qtopicID"),
                    rs.getString("qlevel"),
                    rs.getInt("qStatus") // Sử dụng qStatus từ ResultSet
                );
                list.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public QuestionDTO selectByID(String id) {
        if (conn == null) {
            System.err.println("Không thể thực hiện selectByID: Kết nối database là null!");
            return null;
        }
        String sql = "SELECT * FROM questions WHERE qID = ? AND qStatus = 1"; // Sử dụng qStatus
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new QuestionDTO(
                        rs.getInt("qID"),
                        rs.getString("qContent"),
                        rs.getString("qPictures"),
                        rs.getInt("qtopicID"),
                        rs.getString("qlevel"),
                        rs.getInt("qStatus") // Sử dụng qStatus từ ResultSet
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        if (conn == null) {
            System.err.println("Không thể thực hiện delete: Kết nối database là null!");
            return false;
        }
        String sql = "UPDATE questions SET qStatus = 0 WHERE qID = ?"; // Sử dụng qStatus
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertAnswer(AnswerDTO answer) {
        if (conn == null) {
            System.err.println("Không thể thực hiện insertAnswer: Kết nối database là null!");
            return false;
        }
        String sql = "INSERT INTO answers (qID, awContent, awPictures, isRight, awStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, answer.getQID());
            ps.setString(2, answer.getAwContent());
            ps.setString(3, answer.getAwPictures());
            ps.setInt(4, answer.getIsRight());
            ps.setInt(5, answer.getAwStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getLastInsertedID() {
        if (conn == null) {
            System.err.println("Không thể thực hiện getLastInsertedID: Kết nối database là null!");
            return -1;
        }
        String sql = "SELECT LAST_INSERT_ID()";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void closeConnection() {
        JDBCUtil.closeConnection(conn);
    }
}