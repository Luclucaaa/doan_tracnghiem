/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.ResultDAO;
import com.tracnghiem.DTO.ResultDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ResultBUS {
    private ResultDAO resultDAO;

    public ResultBUS() {
        this.resultDAO = new ResultDAO();
    }

    public boolean addResult(ResultDTO result) {
        return resultDAO.insert(result);
    }

    public boolean updateResult(ResultDTO result) {
        return resultDAO.update(result);
    }

    public boolean deleteResult(String resultID) {
        return resultDAO.delete(resultID);
    }

    public ResultDTO getResultByID(String resultID) {
        return resultDAO.selectByID(resultID);
    }

    public ArrayList<ResultDTO> getAllResults() {
        return resultDAO.selectAll();
    }

    public ArrayList<ResultDTO> getResultsByUserID(int userID) {
        ArrayList<ResultDTO> allResults = resultDAO.selectAll();
        ArrayList<ResultDTO> userResults = new ArrayList<>();
        for (ResultDTO result : allResults) {
            if (result.getUserID() == userID) {
                userResults.add(result);
            }
        }
        return userResults;
    }

    public ArrayList<ResultDTO> getResultsByExCode(String exCode) {
        ArrayList<ResultDTO> allResults = resultDAO.selectAll();
        ArrayList<ResultDTO> exCodeResults = new ArrayList<>();
        for (ResultDTO result : allResults) {
            if (result.getExCode().equals(exCode)) {
                exCodeResults.add(result);
            }
        }
        return exCodeResults;
    }

}
