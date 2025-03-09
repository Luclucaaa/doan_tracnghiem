/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tracnghiem.DTO;

/**
 *
 * @author THELUC
 */
public class AnswerDTO {
    private int awID;
    private int qID;
    private String awContent;
    private String awPictures;
    private int isRight; // Thay boolean thành int
    private int awStatus;

    public AnswerDTO() {
    }

    public AnswerDTO(int qID, String awContent, String awPictures, int isRight, int awStatus) {
        this.qID = qID;
        this.awContent = awContent;
        this.awPictures = awPictures;
        this.isRight = isRight;
        this.awStatus = awStatus;
    }

    public AnswerDTO(int awID, int qID, String awContent, String awPictures, int isRight, int awStatus) {
        this.awID = awID;
        this.qID = qID;
        this.awContent = awContent;
        this.awPictures = awPictures;
        this.isRight = isRight;
        this.awStatus = awStatus;
    }

    public int getAwStatus() {
        return awStatus;
    }

    public void setAwStatus(int awStatus) {
        this.awStatus = awStatus;
    }

    public int getAwID() {
        return awID;
    }

    public void setAwID(int awID) {
        this.awID = awID;
    }

    public int getQID() {
        return qID;
    }

    public void setQID(int qID) {
        this.qID = qID;
    }

    public String getAwContent() {
        return awContent;
    }

    public void setAwContent(String awContent) {
        this.awContent = awContent;
    }

    public String getAwPictures() {
        return awPictures;
    }

    public void setAwPictures(String awPictures) {
        this.awPictures = awPictures;
    }

    public int getIsRight() { // Thay isIsRight() bằng getIsRight()
        return isRight;
    }

    public void setIsRight(int isRight) { // Thay setIsRight(boolean) bằng setIsRight(int)
        this.isRight = isRight;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" + "awID=" + awID + ", qID=" + qID + ", awContent=" + awContent + ", awPictures=" + awPictures + ", isRight=" + isRight + ", awStatus=" + awStatus + '}';
    }
}