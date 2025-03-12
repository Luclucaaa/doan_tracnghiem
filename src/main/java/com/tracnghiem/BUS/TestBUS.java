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
}