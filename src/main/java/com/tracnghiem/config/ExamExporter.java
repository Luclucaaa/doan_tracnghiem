package com.tracnghiem.config;
import com.tracnghiem.DTO.ExamDTO;
import com.tracnghiem.DTO.QuestionDTO;
import com.tracnghiem.DTO.AnswerDTO;
import com.tracnghiem.BUS.QuestionBUS;
import com.tracnghiem.BUS.AnswerBUS;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExamExporter {

    private static QuestionBUS questionBUS = new QuestionBUS();
    private static AnswerBUS answerBUS = new AnswerBUS();
    public static void exportExamToDocx(ExamDTO exam, String outputFilePath) {
        try (XWPFDocument document = new XWPFDocument()) {
            addTitle(document, "ĐỀ THI TRẮC NGHIỆM");

            // Thêm thông tin bài thi
            addExamInfo(document, exam);

            // Thêm danh sách câu hỏi
            String[] questionIDs = exam.getExQuesIDs().split(","); 
            for (int i = 0; i < questionIDs.length; i++) {
                int questionID = Integer.parseInt(questionIDs[i].trim());
                String questionText = "Câu " + (i + 1) + ": " + getQuestionText(questionID);
                List<String> choices = getQuestionChoices(questionID);
                String questionImage = getQuestionImage(questionID); // Lấy hình ảnh câu hỏi (nếu có)

                addQuestion(document, questionText, choices, questionImage);
            }

            // Ghi file
            try (FileOutputStream out = new FileOutputStream(outputFilePath)) {
                document.write(out);
            }

            System.out.println("Bài thi đã được xuất thành công: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void addTitle(XWPFDocument document, String title) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER); 

        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(18);
        run.setColor("0000FF"); 
        run.setText(title);
        run.addBreak();
    }

   
    private static void addExamInfo(XWPFDocument document, ExamDTO exam) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Mã bài thi: " + exam.getTestCode());
        run.addBreak();
        run.setText("Thứ tự đề: " + exam.getExOrder());
        run.addBreak();
        run.setText("Mã đề thi: " + exam.getExCode());
        run.addBreak();
        run.addBreak();
    }

    private static void addQuestion(XWPFDocument document, String questionText, List<String> choices, String questionImage) {
        XWPFParagraph questionParagraph = document.createParagraph();
        XWPFRun questionRun = questionParagraph.createRun();
        questionRun.setBold(true);
        questionRun.setText(questionText);
        questionRun.addBreak();

        // Thêm hình ảnh câu hỏi (nếu có)
        if (questionImage != null && !questionImage.isEmpty()) {
            try (InputStream imageStream = new URL(questionImage).openStream()) {
                questionRun.addBreak();
                questionRun.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, questionImage, Units.toEMU(300), Units.toEMU(200)); // Kích thước hình ảnh
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        }

        XWPFParagraph choicesParagraph = document.createParagraph();
        choicesParagraph.setIndentationLeft(400); 

        for (int i = 0; i < choices.size(); i++) {
            XWPFRun choiceRun = choicesParagraph.createRun();
            choiceRun.setText((char) ('A' + i) + ". " + choices.get(i));
            choiceRun.addBreak();
        }
        choicesParagraph.createRun().addBreak();
    }

    private static String getQuestionText(int questionID) {
        ArrayList<QuestionDTO> questions = questionBUS.getAllQuestions();
        for (QuestionDTO question : questions) {
            if (question.getQID() == questionID) {
                return question.getQContent(); 
            }
        }
        return "Nội dung câu hỏi không tồn tại"; 
    }

    private static List<String> getQuestionChoices(int questionID) {
        ArrayList<AnswerDTO> answers = answerBUS.getAllActiveAnswers();
        List<String> choices = new ArrayList<>();

        List<AnswerDTO> questionAnswers = answers.stream()
                .filter(a -> a.getQID() == questionID)
                .collect(Collectors.toList());

        for (AnswerDTO answer : questionAnswers) {
            choices.add(answer.getAwContent());
        }
        return choices;
    }

    private static String getQuestionImage(int questionID) {
        ArrayList<QuestionDTO> questions = questionBUS.getAllQuestions();
        for (QuestionDTO question : questions) {
            if (question.getQID() == questionID) {
                return question.getQPicture(); 
            }
        }
        return null; 
    }
}