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
        this.testDAO = TestDAO.getInstance();
    }

    public boolean addTest(TestDTO test) {
        return testDAO.insert(test);
    }

    public boolean updateTest(TestDTO test) {
        return testDAO.update(test);
    }

    public boolean deleteTest(String testID) {
        return testDAO.delete(testID);
    }

    public ArrayList<TestDTO> getAllActiveTests() {
        return testDAO.selectAll();
    }

    public TestDTO getTestByID(String testID) {
        return testDAO.selectByID(testID);
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
}
