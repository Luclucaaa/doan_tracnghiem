/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DTO;

import java.time.LocalDateTime;

/**
 *
 * @author THELUC
 */
public class ResultDTO {
    private int rs_num;
    private int userID;
    private String exCode;
    private String rs_answers;
    private double rs_mark;
    private LocalDateTime rs_date;

    public ResultDTO(int userID, String exCode, String rs_answers, double rs_mark, LocalDateTime rs_date) {
        this.userID = userID;
        this.exCode = exCode;
        this.rs_answers = rs_answers;
        this.rs_mark = rs_mark;
        this.rs_date = rs_date;
    }

    public ResultDTO(int rs_num, int userID, String exCode, String rs_answers, double rs_mark, LocalDateTime rs_date) {
        this.rs_num = rs_num;
        this.userID = userID;
        this.exCode = exCode;
        this.rs_answers = rs_answers;
        this.rs_mark = rs_mark;
        this.rs_date = rs_date;
    }

    public ResultDTO() {
    }
    
    public int getRs_num() {
        return rs_num;
    }

    public void setRs_num(int rs_num) {
        this.rs_num = rs_num;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getRs_answers() {
        return rs_answers;
    }

    public void setRs_answers(String rs_answers) {
        this.rs_answers = rs_answers;
    }

    public double getRs_mark() {
        return rs_mark;
    }

    public void setRs_mark(double rs_mark) {
        this.rs_mark = rs_mark;
    }

    public LocalDateTime getRs_date() {
        return rs_date;
    }

    public void setRs_date(LocalDateTime rs_date) {
        this.rs_date = rs_date;
    }

    @Override
    public String toString() {
        return "ResultDTO{" + "rs_num=" + rs_num + ", userID=" + userID + ", exCode=" + exCode + ", rs_answers=" + rs_answers + ", rs_mark=" + rs_mark + ", rs_date=" + rs_date + '}';
    }

    
}
