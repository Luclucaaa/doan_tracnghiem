package com.tracnghiem.config;

import com.tracnghiem.DAO.QuestionDAO;
import com.tracnghiem.DTO.AnswerDTO;
import com.tracnghiem.DTO.QuestionDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelImporter {
    public static boolean importQuestionsWithAnswersFromExcel(String filePath) {
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); 

            List<QuestionDTO> questions = new ArrayList<>();
            List<AnswerDTO> answers = new ArrayList<>();

            QuestionDAO questionDAO = new QuestionDAO();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue; 
                }

                String qContent = getStringValue(row.getCell(0)); // qContent
                String qPictures = getStringValue(row.getCell(1)); // qPictures
                int qTopic = getNumericValue(row.getCell(2)); // qTopic
                String qLevel = getStringValue(row.getCell(3)); // qLevel
                int qStatus = getNumericValue(row.getCell(4)); // qStatus

                // Kiểm tra xem qTopic có tồn tại trong bảng topics không
                if (!questionDAO.isTopicExists(qTopic)) {
                    System.err.println("Topic không tồn tại: " + qTopic + " (Câu hỏi: " + qContent + ")");
                    continue; 
                }

                QuestionDTO question = new QuestionDTO(qContent, qPictures, qTopic, qLevel, qStatus);

                boolean isQuestionAdded = questionDAO.insert(question);
                if (!isQuestionAdded) {
                    System.err.println("Không thể thêm câu hỏi: " + qContent);
                    continue;
                }

                // Lấy qID của câu hỏi vừa được thêm
                int qID = questionDAO.getLastInsertedID();
                if (qID == -1) {
                    System.err.println("Không thể lấy qID của câu hỏi: " + qContent);
                    continue;
                }

                // Đọc thông tin đáp án (nếu có)
                for (int j = 5; j < row.getLastCellNum(); j += 4) {
                    String awContent = getStringValue(row.getCell(j)); // awContent
                    String awPictures = getStringValue(row.getCell(j + 1)); // awPictures
                    int isRight = getNumericValue(row.getCell(j + 2)); // isRight
                    int awStatus = getNumericValue(row.getCell(j + 3)); // awStatus

                    // Tạo đối tượng AnswerDTO
                    AnswerDTO answer = new AnswerDTO(qID, awContent, awPictures, isRight, awStatus);
                    answers.add(answer);
                }
            }

            for (AnswerDTO answer : answers) {
                questionDAO.insertAnswer(answer);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Import thất bại
        }
    }

    
    /**
     * Đọc giá trị số từ ô Excel. Nếu ô chứa kiểu chuỗi, chuyển đổi sang số.
     *
     * @param cell Ô cần đọc.
     * @return Giá trị số.
     */
   
    private static int getNumericValue(Cell cell) {
        if (cell == null) {
            return 0; 
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0; 
                }
            default:
                return 0; 
        }
    }

    /**
     * Đọc giá trị chuỗi từ ô Excel. Nếu ô trống, trả về chuỗi rỗng.
     *
     * @param cell Ô cần đọc.
     * @return Giá trị chuỗi.
     */
    
    private static String getStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return ""; 
        }
    }
}
