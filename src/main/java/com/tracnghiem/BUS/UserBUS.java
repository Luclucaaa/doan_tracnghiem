/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.UserDAO;
import com.tracnghiem.DTO.UserDTO;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.math.BigInteger;

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
    String hashedPassword = md5(password); // Mã hóa mật khẩu đầu vào
    for (UserDTO user : users) {
        if (user.getUserName().equals(username) && user.getUserPassword().equals(hashedPassword)) {
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
    public UserDTO getUserByUsername(String username) {
    return userDAO.getUserByUsername(username);
}
    public boolean updateUserInfo(UserDTO user) {
    return userDAO.updateUserInfo(user);
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
    private String md5(String input) {
    try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        java.math.BigInteger no = new java.math.BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }  
}
    
}
