/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.UserDAO;
import com.tracnghiem.DTO.UserDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class UserBUS {
    private UserDAO userDAO;

    public UserBUS() {
        this.userDAO = new UserDAO();
    }

    public boolean addUser(UserDTO user) {
        return userDAO.insert(user);
    }

    public boolean updateUser(UserDTO user) {
        return userDAO.update(user);
    }

    public boolean deleteUser(String userID) {
        return userDAO.delete(userID);
    }

    public UserDTO getUserByID(String userID) {
        return userDAO.selectByID(userID);
    }

    public ArrayList<UserDTO> getAllUsers() {
        return userDAO.selectAll();
    }

    public UserDTO login(String username, String password) {
        ArrayList<UserDTO> users = userDAO.selectAll();
        for (UserDTO user : users) {
            if (user.getUserName().equals(username) && user.getUserPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUsernameExists(String username) {
        ArrayList<UserDTO> users = userDAO.selectAll();
        for (UserDTO user : users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        ArrayList<UserDTO> users = userDAO.selectAll();
        for (UserDTO user : users) {
            if (user.getUserEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
