/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.BUS;

import com.tracnghiem.DAO.ResultDAO;
import com.tracnghiem.DTO.ResultDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    
    public int getLastInsertedID(int userID, String exCode, LocalDateTime rsDate) {
    return resultDAO.getLastInsertedID(userID, exCode, rsDate);
}
    public Map<String, int[]> getStatisticsByExCode(String exCodeFilter) {
        ArrayList<ResultDTO> results = getAllResults();
        Map<String, int[]> stats = new HashMap<>();

        for (ResultDTO result : results) {
            String exCode = result.getExCode();
            if (!exCodeFilter.isEmpty() && !exCode.startsWith(exCodeFilter)) {
                continue; // Lọc theo exCode nếu có
            }

            stats.putIfAbsent(exCode, new int[]{0, 0, 0}); // [số lần thi, đạt, không đạt]
            int[] stat = stats.get(exCode);
            stat[0]++; // Tăng số lần thi
            if (result.getRs_mark() >= 5) {
                stat[1]++; // Tăng số người đạt
            } else {
                stat[2]++; // Tăng số người không đạt
            }
        }
        return stats;
    }
}
