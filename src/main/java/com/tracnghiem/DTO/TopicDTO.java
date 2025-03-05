/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DTO;

/**
 *
 * @author THELUC
 */
public class TopicDTO {
    private int tpID;
    private String tpTitle;
    private int tpParent;
    private int tpStatus;

    public TopicDTO(String tpTitle, int tpID, int tpParent) {
        this.tpID = tpID != 0 ? tpID : -1; // Đảm bảo tpID không phải 0 (giá trị mặc định sai)
        this.tpTitle = (tpTitle != null && !tpTitle.trim().isEmpty()) ? tpTitle.trim() : "Unknown";
        this.tpParent = tpParent;
        this.tpStatus = tpStatus != 0 ? tpStatus : 1; // Đặt mặc định tpStatus = 1 nếu là 0
    }

    // Constructor mặc định với giá trị mặc định hợp lý
    public TopicDTO() {
        this.tpID = -1; // Giá trị mặc định không hợp lệ, cần được set sau
        this.tpTitle = "Unknown";
        this.tpParent = 0; // Mặc định không có chủ đề cha
        this.tpStatus = 1; // Mặc định là Active
    }

    public int getTpID() {
        return tpID;
    }

    public void setTpID(int tpID) {
        this.tpID = tpID != 0 ? tpID : -1; // Đảm bảo tpID không phải 0
    }

    public String getTpTitle() {
        return tpTitle;
    }

    public void setTpTitle(String tpTitle) {
        this.tpTitle = (tpTitle != null && !tpTitle.trim().isEmpty()) ? tpTitle.trim() : "Unknown";
    }

    public int getTpParent() {
        return tpParent;
    }

    public void setTpParent(int tpParent) {
        this.tpParent = tpParent;
    }

    public int getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(int tpStatus) {
        this.tpStatus = tpStatus != 0 ? tpStatus : 1; // Đặt mặc định tpStatus = 1 nếu là 0
    }

    @Override
    public String toString() {
        return "TopicDTO{" + "tpID=" + tpID + ", tpTitle=" + tpTitle + ", tpParent=" + tpParent + ", tpStatus=" + tpStatus + '}';
    }
}
