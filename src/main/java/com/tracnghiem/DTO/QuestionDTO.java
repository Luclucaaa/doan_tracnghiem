/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DTO;

/**
 *
 * @author THELUC
 */
public class QuestionDTO {
    private int qID;
    private String qContent;
    private String qPictures;
    private int topicID;
    private String level;
    private int status;

    // Constructor đầy đủ
    public QuestionDTO(int qID, String qContent, String qPicture, int topicID, String level, int status) {
        this.qID = qID;
        this.qContent = qContent;
        this.qPictures = qPicture;
        this.topicID = topicID;
        this.level = level;
        this.status = status;
    }

    // Constructor dùng khi thêm mới (không cần qID vì sẽ tự động sinh)
    public QuestionDTO(String qContent, String qPicture, int topicID, String level, int status) {
        this.qContent = qContent;
        this.qPictures = qPicture;
        this.topicID = topicID;
        this.level = level;
        this.status = status;
    }

    // Getter và Setter cho qID
    public int getQID() {
        return qID;
    }

    public void setQID(int qID) {
        this.qID = qID;
    }

    // Getter và Setter cho qContent
    public String getQContent() {
        return qContent;
    }

    public void setQContent(String qContent) {
        this.qContent = qContent;
    }

    // Getter và Setter cho qPicture
    public String getQPicture() {
        return qPictures;
    }

    public void setQPicture(String qPicture) {
        this.qPictures = qPicture;
    }

    // Getter và Setter cho topicID
    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    // Getter và Setter cho level
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // Getter và Setter cho status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
