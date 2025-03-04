/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.AnswerDAO;
import com.tracnghiem.DTO.AnswerDTO;
import java.util.ArrayList;

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

    public ArrayList<AnswerDTO> getAnswersByQuestionID(int questionID) {
        ArrayList<AnswerDTO> allAnswers = answerDAO.selectAll();
        ArrayList<AnswerDTO> result = new ArrayList<>();

        for (AnswerDTO answer : allAnswers) {
            if (answer.getQID() == questionID) {
                result.add(answer);
            }
        }
        return result;
    }
}
