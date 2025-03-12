/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.TopicDAO;
import com.tracnghiem.DTO.TopicDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TopicBUS {
    private TopicDAO topicDAO;

    public TopicBUS() {
        this.topicDAO = TopicDAO.getInstance();
    }

    public boolean addTopic(TopicDTO topic) {
    return topicDAO.insert(topic);
}

    public boolean updateTopic(TopicDTO topic) {
        return topicDAO.update(topic);
    }

    public boolean deleteTopic(String topicID) {
        return topicDAO.delete(topicID);
    }

    public ArrayList<TopicDTO> getAllActiveTopics() {
        return topicDAO.selectAll();
    }

    public TopicDTO getTopicByID(String topicID) {
        return topicDAO.selectByID(topicID);
    }
    public int getIDbyTopic(String topicName) {
        return topicDAO.getIDbyTopic(topicName);
    }
    public TopicDTO getTopicByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        ArrayList<TopicDTO> allTopics = topicDAO.selectAll();
        for (TopicDTO topic : allTopics) {
            if (topic.getTpTitle() != null && topic.getTpTitle().trim().equalsIgnoreCase(title.trim())) {
                return topic;
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }
}
