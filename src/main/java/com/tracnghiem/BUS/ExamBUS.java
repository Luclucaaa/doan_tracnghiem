/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.ExamDAO;
import com.tracnghiem.DTO.ExamDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ExamBUS {
    private ExamDAO examDAO;

    public ExamBUS() {
        this.examDAO = ExamDAO.getInstance();
    }
    
    public boolean deleteExamsByTestCode(String testCode) {
        return examDAO.deleteByTestCode(testCode);
    }

    public boolean addExam(ExamDTO exam) {
        return examDAO.insert(exam);
    }

    public boolean updateExam(ExamDTO exam) {
        return examDAO.update(exam);
    }

    public boolean deleteExam(String exCode) {
        return examDAO.delete(exCode);
    }

    public ArrayList<ExamDTO> getAllExams() {
        return examDAO.selectAll();
    }

    public ExamDTO getExamByCode(String exCode) {
        return examDAO.selectByID(exCode);
    }

    public ArrayList<ExamDTO> getExamsByTestCode(String testCode) {
        ArrayList<ExamDTO> allExams = examDAO.selectAll();
        ArrayList<ExamDTO> result = new ArrayList<>();

        for (ExamDTO exam : allExams) {
            if (exam.getTestCode().equals(testCode)) {
                result.add(exam);
            }
        }

        return result;
    }

}
