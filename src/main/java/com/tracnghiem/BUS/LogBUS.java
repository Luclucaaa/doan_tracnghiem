/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.LogDAO;
import com.tracnghiem.DTO.LogDTO;
import java.util.ArrayList;


/**
 *
 * @author Admin
 */
public class LogBUS {
    private LogDAO logDAO;

    public LogBUS() {
        this.logDAO = new LogDAO();
    }

    public boolean addLog(LogDTO log) {
        return logDAO.insert(log);
    }

    public boolean updateLog(LogDTO log) {
        return logDAO.update(log);
    }

    public boolean deleteLog(String logID) {
        return logDAO.delete(logID);
    }

    public LogDTO getLogByID(String logID) {
        return logDAO.selectByID(logID);
    }

    public ArrayList<LogDTO> getAllLog() {
        return logDAO.selectAll();
    }

    public ArrayList<LogDTO> getLogByUserID(int userID) {
        ArrayList<LogDTO> allLogs = logDAO.selectAll();
        ArrayList<LogDTO> userLogs = new ArrayList<>();
        for (LogDTO log : allLogs) {
            if (log.getLogUserID() == userID) {
                userLogs.add(log);
            }
        }
        return userLogs;
    }
}
