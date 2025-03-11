/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.ResultDAO;
import com.tracnghiem.DTO.ResultDTO;
import java.util.ArrayList;
import java.util.Map;

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
        return resultDAO.selectByUserID(userID);
    }

    public ArrayList<ResultDTO> getResultsByExCode(String exCode) {
        return resultDAO.selectByExCode(exCode);
    }

    public Map<String, int[]> getStatisticsByExCode(String exCodeFilter) {
        return resultDAO.getStatisticsByExCode(exCodeFilter);
    }
}
