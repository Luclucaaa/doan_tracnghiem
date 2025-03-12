/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.AnswerDAO;
import com.tracnghiem.DTO.AnswerDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AnswerBUS {
        private AnswerDAO answerDAO;

    public AnswerBUS() {
        this.answerDAO = AnswerDAO.getInstance();
    }

    public boolean addAnswer(AnswerDTO answer) {
        return answerDAO.insert(answer);
    }

    public boolean updateAnswer(AnswerDTO answer) {
        return answerDAO.update(answer);
    }

    public boolean deleteAnswer(String answerID) {
        return answerDAO.delete(answerID);
    }

    public ArrayList<AnswerDTO> getAllActiveAnswers() {
        return answerDAO.selectAll();
    }

    public AnswerDTO getAnswerByID(String answerID) {
        return answerDAO.selectByID(answerID);
    }   
    
    public List<AnswerDTO> getAnswersByQuestionID(int qID) {
        try {
            List<AnswerDTO> answers = answerDAO.selectByQuestionID(qID);
            System.out.println("Answers retrieved for qID " + qID + ": " + (answers != null ? answers.size() : "null"));
            return answers;
        } catch (Exception e) {
            System.err.println("Error retrieving answers for qID " + qID + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
