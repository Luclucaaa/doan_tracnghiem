/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.TestDAO;
import com.tracnghiem.DTO.TestDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TestBUS {
    private TestDAO testDAO;

    public TestBUS() {
        testDAO = new TestDAO();
    }

    public ArrayList<TestDTO> getAllTests() {
        return testDAO.selectAll();
    }

    public TestDTO getTestByCode(String testCode) {
        return testDAO.selectByID(testCode);
    }

    public boolean addTest(TestDTO test) {
        return testDAO.insert(test);
    }

    public boolean updateTest(TestDTO test) {
        return testDAO.update(test);
    }

    public boolean deleteTest(String testCode) {
        return testDAO.delete(testCode);
    }
    public ArrayList<TestDTO> getTestsByTopicID(int tpID) {
    ArrayList<TestDTO> allTests = testDAO.selectAll();
    ArrayList<TestDTO> result = new ArrayList<>();
    for (TestDTO test : allTests) {
        if (test.getTpID() == tpID) {
            result.add(test);
}
    }
    return result;
}
    // Thêm phương thức getTestByTitle
    public TestDTO getTestByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        ArrayList<TestDTO> allTests = testDAO.selectAll();
        for (TestDTO test : allTests) {
            if (test.getTestTitle() != null && test.getTestTitle().trim().equalsIgnoreCase(title.trim())) {
                return test;
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }
    // Thêm phương thức lấy số lượt làm bài còn lại của người dùng cho bài thi
    public int getUserRemainingAttempts(int userID, String testCode) {
        int remainingAttempts = testDAO.getUserRemainingAttempts(userID, testCode);
        if (remainingAttempts == -1) { // Chưa có bản ghi trong user_test_limits
            TestDTO test = getTestByCode(testCode);
            if (test != null) {
                remainingAttempts = test.getTestLimit(); // Lấy từ testLimit ban đầu
                testDAO.insertUserTestLimit(userID, testCode, remainingAttempts); // Thêm bản ghi mới
            } else {
                return 0; // Bài thi không tồn tại
            }
        }
        return remainingAttempts;
    }

    // Thêm phương thức giảm số lượt làm bài còn lại
    public boolean decreaseUserRemainingAttempts(int userID, String testCode) {
        int currentAttempts = getUserRemainingAttempts(userID, testCode);
        if (currentAttempts > 0) {
            return testDAO.updateUserRemainingAttempts(userID, testCode, currentAttempts - 1);
        }
        return false; // Không còn lượt làm bài
    }
}