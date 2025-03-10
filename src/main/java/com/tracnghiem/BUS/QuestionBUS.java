/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.QuestionDAO;
import com.tracnghiem.DAO.AnswerDAO;
import com.tracnghiem.DTO.AnswerDTO;
import com.tracnghiem.DTO.QuestionDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class QuestionBUS {
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;

    public QuestionBUS() {
        this.questionDAO = QuestionDAO.getInstance();
        this.answerDAO = AnswerDAO.getInstance();
    }

    public boolean addQuestion(QuestionDTO question) {
        try {
            return questionDAO.insert(question);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addAnswer(AnswerDTO answer) {
        try {
            return questionDAO.insertAnswer(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getLastInsertedID() {
        try {
            return questionDAO.getLastInsertedID();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean updateQuestion(QuestionDTO question) {
        return questionDAO.update(question);
    }

    public boolean deleteQuestion(int questionID) {
        deleteAnswersByQuestionID(questionID);
        return questionDAO.delete(String.valueOf(questionID));
    }

    public QuestionDTO getQuestionByID(int questionID) {
        return questionDAO.selectByID(String.valueOf(questionID));
    }

    public ArrayList<QuestionDTO> getAllQuestions() {
    ArrayList<QuestionDTO> allQuestions = questionDAO.selectAll();
    ArrayList<QuestionDTO> activeQuestions = new ArrayList<>();
    for (QuestionDTO question : allQuestions) {
        if (question.getStatus() == 1) { // Sử dụng getStatus() từ QuestionDTO
            activeQuestions.add(question);
        }
    }
    return activeQuestions;
}

    public ArrayList<QuestionDTO> getQuestionsByTopic(int topicID) {
        ArrayList<QuestionDTO> allQuestions = getAllQuestions();
        ArrayList<QuestionDTO> topicQuestions = new ArrayList<>();
        for (QuestionDTO question : allQuestions) {
            if (question.getTopicID() == topicID) {
                topicQuestions.add(question);
            }
        }
        return topicQuestions;
    }

    public ArrayList<QuestionDTO> getQuestionsByLevel(String level) {
        ArrayList<QuestionDTO> allQuestions = getAllQuestions();
        ArrayList<QuestionDTO> levelQuestions = new ArrayList<>();
        for (QuestionDTO question : allQuestions) {
            if (question.getLevel().equalsIgnoreCase(level)) {
                levelQuestions.add(question);
            }
        }
        return levelQuestions;
    }

    public ArrayList<AnswerDTO> getAllAnswers() {
        ArrayList<AnswerDTO> allAnswers = answerDAO.selectAll();
        ArrayList<AnswerDTO> activeAnswers = new ArrayList<>();
        for (AnswerDTO answer : allAnswers) {
            if (answer.getAwStatus() == 1) {
                activeAnswers.add(answer);
            }
        }
        return activeAnswers;
    }

    public List<AnswerDTO> getAnswersByQuestionID(int qID) {
        ArrayList<AnswerDTO> allAnswers = getAllAnswers();
        List<AnswerDTO> questionAnswers = new ArrayList<>();
        for (AnswerDTO answer : allAnswers) {
            if (answer.getQID() == qID) {
                questionAnswers.add(answer);
            }
        }
        return questionAnswers;
    }
    
    public ArrayList<Integer> getQuestionsByDifficulty(int topicID, String difficulty, int numQuestions) {
        return questionDAO.getQuestionsByDifficulty(topicID, difficulty, numQuestions);
    }   

    public boolean deleteAnswersByQuestionID(int qID) {
        List<AnswerDTO> answers = getAnswersByQuestionID(qID);
        boolean success = true;
        for (AnswerDTO answer : answers) {
            if (!answerDAO.delete(String.valueOf(answer.getAwID()))) {
                success = false;
            }
        }
        return success;
    }

    public void closeConnection() {
        questionDAO.closeConnection();
    }
}