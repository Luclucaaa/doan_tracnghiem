/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.QuestionDAO;
import com.tracnghiem.DTO.QuestionDTO;
import java.util.ArrayList;


/**
 *
 * @author Admin
 */
public class QuestionBUS {
    private QuestionDAO questionDAO;

    public QuestionBUS() {
        this.questionDAO = new QuestionDAO();
    }

    public boolean addQuestion(QuestionDTO question) {
        return questionDAO.insert(question);
    }
    public boolean updateQuestion(QuestionDTO question) {
        return questionDAO.update(question);
    }
    public boolean deleteQuestion(String questionID) {
        return questionDAO.delete(questionID);
    }

    public QuestionDTO getQuestionByID(String questionID) {
        return questionDAO.selectByID(questionID);
    }

    public ArrayList<QuestionDTO> getAllQuestions() {
        return questionDAO.selectAll();
    }

    // Lấy danh sách câu hỏi theo chủ đề 
    public ArrayList<QuestionDTO> getQuestionsByTopic(int topicID) {
        ArrayList<QuestionDTO> allQuestions = questionDAO.selectAll();
        ArrayList<QuestionDTO> topicQuestions = new ArrayList<>();
        for (QuestionDTO question : allQuestions) {
            if (question.getQTopic() == topicID) {
                topicQuestions.add(question);
            }
        }
        return topicQuestions;
    }

    // Lấy danh sách câu hỏi theo mức độ 
    public ArrayList<QuestionDTO> getQuestionsByLevel(String level) {
        ArrayList<QuestionDTO> allQuestions = questionDAO.selectAll();
        ArrayList<QuestionDTO> levelQuestions = new ArrayList<>();
        for (QuestionDTO question : allQuestions) {
            if (question.getQLevel().equals(level)) {
                levelQuestions.add(question);
            }
        }
        return levelQuestions;
    }
}
