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
}