/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.tracnghiem.GUI.Dialog;

import com.formdev.flatlaf.FlatClientProperties;
import com.toedter.calendar.JDateChooser;
import com.tracnghiem.BUS.TestBUS;
import com.tracnghiem.BUS.TopicBUS;
import com.tracnghiem.DTO.TestDTO;
import com.tracnghiem.DTO.TopicDTO;
import com.toedter.calendar.JDateChooser;
import com.tracnghiem.BUS.ExamBUS;
import com.tracnghiem.BUS.QuestionBUS;
import com.tracnghiem.DTO.ExamDTO;
import com.tracnghiem.DTO.QuestionDTO;
import com.tracnghiem.GUI.Main.Main;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author THELUC
 */
public class TestDialog extends javax.swing.JDialog {
    private TestBUS testBUS;
    private TopicBUS topicBUS;
    private QuestionBUS questionBUS;
    /**
     * Creates new form TestDialog
     */
    public TestDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        testBUS = new TestBUS();
        topicBUS = new TopicBUS();
        questionBUS = new QuestionBUS();
        loadTopics();
        loadQuestions(); // Tải danh sách câu hỏi
        jLabel18.setText("0");
        jLabel20.setText("0");
        jLabel19.setText("0");
        jLabel23.setText("0");// Tải danh sách chủ đề vào jComboBox1
        setLocationRelativeTo(null); // Căn giữa dialog
    }
    
    public TestDialog(java.awt.Frame parent, boolean modal, TestDTO testToEdit, Main main) {
    super(parent, modal);
    initComponents();
    testBUS = new TestBUS();
    topicBUS = new TopicBUS();
    questionBUS = new QuestionBUS();
    loadTopics();
    loadQuestions(); // Tải danh sách câu hỏi
    jLabel18.setText("0");
    jLabel20.setText("0");
    jLabel19.setText("0");
    jLabel23.setText("0");

    // Hiển thị dữ liệu bài thi cần sửa
    displayTestData(testToEdit, topicBUS);
    configureUpdateButton(testToEdit, testBUS, main); // Cấu hình nút "Cập nhật"
    loadSelectedQuestions(testToEdit); // Tải câu hỏi đã chọn (nếu cần)
    setLocationRelativeTo(null);
}
    
    public void setAddButtonListener(ActionListener listener) {
        // Xóa các ActionListener cũ (nếu có)
        for (ActionListener al : jButton3.getActionListeners()) {
            jButton3.removeActionListener(al);
        }
        // Gán ActionListener mới
        jButton3.addActionListener(listener);
    }
    
    public void setTestCode(String testCode) {
        jTextField4.setText(testCode);
    }

    public void setTestTitle(String testTitle) {
        jTextField2.setText(testTitle);
    }

    public void setTopic(String topic) {
        jComboBox1.setSelectedItem(topic);
    }

    public void setTestTime(String testTime) {
        jTextField3.setText(testTime);
    }

    public void setTestLimit(String testLimit) {
        jTextField5.setText(testLimit);
    }

    public void setTestDate(Date testDate) {
        jDateChooser1.setDate(testDate);
    }

    public void setNumEasy(String numEasy) {
        jLabel18.setText(numEasy);
    }

    public void setNumMedium(String numMedium) {
        jLabel20.setText(numMedium);
    }

    public void setNumDiff(String numDiff) {
        jLabel19.setText(numDiff);
    }

    public void setNumExams(String numExams) {
        jTextField1.setText(numExams);
    }
    
    public void displayTestData(TestDTO test, TopicBUS topicBUS) {
        setTestCode(test.getTestCode());
        setTestTitle(test.getTestTitle());
        TopicDTO topic = topicBUS.getTopicByID(String.valueOf(test.getTpID()));
        String topicString = (topic != null) ? topic.getTpTitle() + " (" + test.getTpID() + ")" : "Không xác định";
        setTopic(topicString);
        setTestTime(String.valueOf(test.getTestTime()));
        setTestLimit(String.valueOf(test.getTestLimit()));
        setTestDate(test.getTestDate());
        setNumEasy(String.valueOf(test.getNum_easy()));
        setNumMedium(String.valueOf(test.getNum_medium()));
        setNumDiff(String.valueOf(test.getNum_diff()));
        setNumExams("1");
    }
    
    private void loadSelectedQuestions(TestDTO test) {
    ExamBUS examBUS = new ExamBUS();
    ArrayList<ExamDTO> exams = examBUS.getExamsByTestCode(test.getTestCode());
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    model.setRowCount(0); // Xóa dữ liệu cũ
    if (exams != null && !exams.isEmpty()) {
        String quesIDs = exams.get(0).getExQuesIDs();
        if (quesIDs != null && !quesIDs.isEmpty()) {
            String[] questionIDs = quesIDs.split(",");
            for (String qID : questionIDs) {
                QuestionDTO question = questionBUS.getQuestionByID(Integer.parseInt(qID.trim()));
                if (question != null) {
                    TopicDTO topic = topicBUS.getTopicByID(String.valueOf(question.getTopicID()));
                    model.addRow(new Object[]{
                        question.getQID(),
                        question.getQContent(),
                        topic != null ? topic.getTpTitle() : "Không xác định",
                        question.getLevel()
                    });
                }
            }
        }
    }
    updateQuestionCount(); // Cập nhật lại số lượng câu hỏi trên giao diện
}
    
    private void updateQuestionCount() {
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    int easy = 0, medium = 0, diff = 0;
    for (int i = 0; i < model.getRowCount(); i++) {
        String level = (String) model.getValueAt(i, 3);
        switch (level) {
            case "easy": easy++; break;
            case "medium": medium++; break;
            case "diff": diff++; break;
        }
    }
    jLabel18.setText(String.valueOf(easy));
    jLabel20.setText(String.valueOf(medium));
        jLabel19.setText(String.valueOf(diff));
        jLabel23.setText(String.valueOf(easy + medium + diff));
}

    public void configureUpdateButton(TestDTO test, TestBUS testBUS, Main main) {
    jLabel1.setText("Sửa bài thi");
    jButton3.setText("Cập nhật");
    for (ActionListener al : jButton3.getActionListeners()) {
        jButton3.removeActionListener(al);
    }
    jButton3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Cập nhật thông tin bài thi
                test.setTestTitle(getTestTitle());
                    test.setTpID(getTpID());
                test.setTestTime(getTestTime());
                test.setTestLimit(getTestLimit());
                    String testDateStr = getTestDate();
                    if (testDateStr == null || testDateStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày tạo!");
                    return;
                }
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date testDate = sdf.parse(testDateStr);
                test.setTestDate(new java.sql.Date(testDate.getTime()));

                // Lấy số lượng câu hỏi từ giao diện
                int numEasy = getNumEasy();
                int numMedium = getNumMedium();
                int numDiff = getNumDiff();
                int totalQuestions = numEasy + numMedium + numDiff;

                // Kiểm tra số lượng câu hỏi
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                int tableQuestionCount = model.getRowCount();
                if (totalQuestions != tableQuestionCount) {
                    JOptionPane.showMessageDialog(null, "Số lượng câu hỏi (" + totalQuestions + 
                        ") không khớp với số câu trong bảng (" + tableQuestionCount + ")!");
                    return;
                }

                // Cập nhật số lượng câu hỏi
                test.setNum_easy(numEasy);
                test.setNum_medium(numMedium);
                test.setNum_diff(numDiff);

                // Kiểm tra dữ liệu đầu vào
                if (test.getTestTitle().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tên bài thi không được để trống!");
                    return;
                }
                if (test.getTpID() == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn chủ đề!");
                    return;
                }
                if (test.getTestTime() <= 0) {
                    JOptionPane.showMessageDialog(null, "Thời gian thi phải lớn hơn 0!");
                    return;
                }
                if (test.getTestLimit() < 0) {
                    JOptionPane.showMessageDialog(null, "Số lượt làm bài không hợp lệ!");
                    return;
                }
                if (totalQuestions <= 0) {
                    JOptionPane.showMessageDialog(null, "Tổng số câu hỏi phải lớn hơn 0!");
                    return;
                }

                // Lấy số lượng đề thi mới
                int numExams = getNumExams();
                if (numExams <= 0 || numExams > 26) {
                    JOptionPane.showMessageDialog(null, "Số lượng đề thi phải từ 1 đến 26!");
                    return;
                }

                // Lấy danh sách câu hỏi từ jTable2
                String quesIDs = "";
                if (tableQuestionCount > 0) {
                    quesIDs = IntStream.range(0, tableQuestionCount)
                        .mapToObj(i -> model.getValueAt(i, 0).toString())
                        .collect(Collectors.joining(","));
                }
                if (quesIDs.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một câu hỏi!");
                    return;
                }

                ExamBUS examBUS = new ExamBUS();
                ArrayList<ExamDTO> currentExams = examBUS.getExamsByTestCode(test.getTestCode());
                int currentNumExams = currentExams.size();

                // Điều chỉnh số lượng đề thi
                if (currentNumExams != numExams) {
                    if (numExams < currentNumExams) {
                        int examsToRemove = currentNumExams - numExams;
                        if (currentNumExams - examsToRemove >= 1) {
                            for (int i = 0; i < examsToRemove; i++) {
                                int lastIndex = currentExams.size() - 1;
                                String exCode = currentExams.get(lastIndex).getExCode();
                                if (!examBUS.deleteExam(exCode)) {
                                    JOptionPane.showMessageDialog(null, "Xóa đề thi " + exCode + " thất bại!");
                                    return;
                                }
                                currentExams.remove(lastIndex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Không thể xóa hết các đề thi do ràng buộc khóa ngoại!");
                            return;
                        }
                    } else {
                        ArrayList<Integer> allQuestions = Arrays.stream(quesIDs.split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toCollection(ArrayList::new));
                        for (int i = currentNumExams; i < numExams; i++) {
                            Collections.shuffle(allQuestions);
                            String newQuesIDs = allQuestions.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","));
                            ExamDTO exam = new ExamDTO();
                            exam.setTestCode(test.getTestCode());
                            exam.setExOrder(String.valueOf((char) ('A' + i)));
                            exam.setExCode(test.getTestCode() + exam.getExOrder());
                            exam.setExQuesIDs(newQuesIDs);
                            if (!examBUS.addExam(exam)) {
                                JOptionPane.showMessageDialog(null, "Thêm đề thi " + exam.getExCode() + " thất bại!");
                                return;
                            }
                        }
                    }
                }

                // Cập nhật quesIDs cho tất cả các đề thi hiện có
                ArrayList<ExamDTO> updatedExams = examBUS.getExamsByTestCode(test.getTestCode());
                for (ExamDTO exam : updatedExams) {
                    exam.setExQuesIDs(quesIDs);
                    if (!examBUS.updateExam(exam)) {
                        JOptionPane.showMessageDialog(null, "Cập nhật đề thi " + exam.getExCode() + " thất bại!");
                        return;
                    }
                }

                // Cập nhật bài thi trong database
                if (testBUS.updateTest(test)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật bài thi thành công!");
                    main.loadTests();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật bài thi thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    });
}
    
    public int getNumExams() {
        try {
            return Integer.parseInt(jTextField1.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    // Tải danh sách chủ đề vào jComboBox1
    private void loadTopics() {
        ArrayList<TopicDTO> topics = topicBUS.getAllActiveTopics();
        jComboBox1.removeAllItems();
        for (TopicDTO topic : topics) {
            jComboBox1.addItem(topic.getTpTitle() + " (" + topic.getTpID() + ")");
        }
    }
    
    // Getter cho các trường dữ liệu
    public String getTestCode() {
        return jTextField4.getText().trim(); // Mã bài thi, có thể để tự động sinh sau
    }

    public String getTestTitle() {
        return jTextField2.getText().trim();
    }

    public int getTpID() {
        String selectedTopic = (String) jComboBox1.getSelectedItem();
        if (selectedTopic != null) {
            String[] parts = selectedTopic.split("\\(");
            String idStr = parts[1].replace(")", "");
            return Integer.parseInt(idStr);
        }
        return -1;
    }

    public int getTestTime() {
        try {
            return Integer.parseInt(jTextField3.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getTestLimit() {
        try {
            return Integer.parseInt(jTextField5.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getTestDate() {
        Date date = jDateChooser1.getDate();
        if (date == null) {
            return "";
    }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public int getNumEasy() {
        try {
            return Integer.parseInt(jLabel18.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getNumMedium() {
        try {
            return Integer.parseInt(jLabel20.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getNumDiff() {
        try {
            return Integer.parseInt(jLabel19.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    

    public int getTestStatus() {
        // Giả định trạng thái mặc định là 1 (hoạt động)
        return 1;
    }

    private void loadQuestions() {
        ArrayList<QuestionDTO> questions = questionBUS.getAllQuestions();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setColumnIdentifiers(new String[]{"Mã câu hỏi", "Nội dung", "Chủ đề", "Mức độ"});
        model.setRowCount(0);

        TopicBUS topicBUS = new TopicBUS();
        String selectedLevel = (String) jComboBox3.getSelectedItem();
        int selectedTopicID = getTpID(); // Lấy tpID từ jComboBox1

        for (QuestionDTO q : questions) {
            TopicDTO topic = topicBUS.getTopicByID(String.valueOf(q.getTopicID()));
            String level = q.getLevel();
            int questionTopicID = q.getTopicID();

            boolean matchTopic = (selectedTopicID == -1 || questionTopicID == selectedTopicID);
            boolean matchLevel = "Tất cả".equals(selectedLevel) || level.equalsIgnoreCase(selectedLevel) ||
                                (selectedLevel.equals("Dễ") && level.equalsIgnoreCase("easy")) ||
                                (selectedLevel.equals("Trung bình") && level.equalsIgnoreCase("medium")) ||
                                (selectedLevel.equals("Khó") && level.equalsIgnoreCase("diff"));

            if (matchTopic && matchLevel) {
                model.addRow(new Object[]{
                    q.getQID(),
                    q.getQContent(),
                    topic != null ? topic.getTpTitle() : "Không xác định",
                    level
                });
            }
        }
    }
    
    private void updateQuestionCount(String level, int delta) {
        int easy = Integer.parseInt(jLabel18.getText().isEmpty() ? "0" : jLabel18.getText());
        int medium = Integer.parseInt(jLabel20.getText().isEmpty() ? "0" : jLabel20.getText());
        int diff = Integer.parseInt(jLabel19.getText().isEmpty() ? "0" : jLabel19.getText());

        if (level.equalsIgnoreCase("easy")) easy += delta;
        else if (level.equalsIgnoreCase("medium")) medium += delta;
        else if (level.equalsIgnoreCase("diff")) diff += delta;

        jLabel18.setText(String.valueOf(easy));
        jLabel20.setText(String.valueOf(medium));
        jLabel19.setText(String.valueOf(diff));
        jLabel23.setText(String.valueOf(easy + medium + diff));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextField2 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 255, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Thêm bài thi mới");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(399, 399, 399)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Mã bài thi");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Chủ đề");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Thời gian thi");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Số lượt làm bài");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Phút");

        jButton3.setBackground(new java.awt.Color(0, 255, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton3.setText("Thêm mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton4.setText("Hủy bỏ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Tên bàn thi");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Chọn câu hỏi");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("Độ khó");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Dễ", "Trung bình", "Khó" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Các câu đã chọn");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Số câu dễ");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Số câu khó");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Số câu trung bình");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setText("Tổng số câu");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton1.setText("Thêm câu hỏi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton2.setText("Xóa câu hỏi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addComponent(jLabel14))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(270, 270, 270)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13))
                            .addComponent(jLabel10))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Ngày tạo");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextField2);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("Số lượng đề thi");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 14, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        loadQuestions();// TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
    if (selectedRow >= 0) {
        DefaultTableModel sourceModel = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel targetModel = (DefaultTableModel) jTable2.getModel();
        if (targetModel.getColumnCount() == 0) {
            targetModel.setColumnIdentifiers(new String[]{"Mã câu hỏi", "Nội dung", "Chủ đề", "Mức độ"});
        }

        Object[] rowData = new Object[sourceModel.getColumnCount()];
        for (int i = 0; i < sourceModel.getColumnCount(); i++) {
            rowData[i] = sourceModel.getValueAt(selectedRow, i);
        }
        targetModel.addRow(rowData);

        // Cập nhật số lượng câu hỏi theo mức độ
        String level = (String) sourceModel.getValueAt(selectedRow, 3);
        updateQuestionCount(level, 1);
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để thêm!");
    }// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int selectedRow = jTable2.getSelectedRow();
    if (selectedRow >= 0) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        String level = (String) model.getValueAt(selectedRow, 3);
        model.removeRow(selectedRow);

        // Cập nhật số lượng câu hỏi theo mức độ
        updateQuestionCount(level, -1);
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một câu hỏi để xóa!");
    }// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
        String testCode = getTestCode();
        String testTitle = getTestTitle();
            int topicID = getTpID();
        int testTime = getTestTime();
        int testLimit = getTestLimit();
            String testDateStr = getTestDate();
        int numEasy = getNumEasy();
        int numMedium = getNumMedium();
        int numDiff = getNumDiff();
        int numExams = getNumExams();

        // Kiểm tra dữ liệu đầu vào
        if (testCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã bài thi không được để trống!");
            return;
        }
        if (testTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên bài thi không được để trống!");
            return;
        }
        if (topicID == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chủ đề!");
            return;
        }
        if (testTime <= 0) {
            JOptionPane.showMessageDialog(this, "Thời gian thi phải lớn hơn 0!");
            return;
        }
        if (testLimit < 0) {
            JOptionPane.showMessageDialog(this, "Số lượt làm bài không hợp lệ!");
            return;
        }
            if (testDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày tạo!");
            return;
        }
        if (numEasy + numMedium + numDiff <= 0) {
            JOptionPane.showMessageDialog(this, "Tổng số câu hỏi phải lớn hơn 0!");
            return;
        }
        if (numExams <= 0 || numExams > 26) {
            JOptionPane.showMessageDialog(this, "Số lượng đề thi phải từ 1 đến 26!");
            return;
        }

        // Lấy danh sách câu hỏi từ bảng questions
        QuestionBUS questionBUS = new QuestionBUS();
        ArrayList<Integer> easyQuestions = questionBUS.getQuestionsByDifficulty(topicID, "easy", numEasy);
        ArrayList<Integer> mediumQuestions = questionBUS.getQuestionsByDifficulty(topicID, "medium", numMedium);
            ArrayList<Integer> diffQuestions = questionBUS.getQuestionsByDifficulty(topicID, "diff", numDiff);

        // Kết hợp danh sách câu hỏi
        ArrayList<Integer> allQuestions = new ArrayList<>();
        allQuestions.addAll(easyQuestions);
        allQuestions.addAll(mediumQuestions);
        allQuestions.addAll(diffQuestions);

        if (allQuestions.size() < (numEasy + numMedium + numDiff)) {
            JOptionPane.showMessageDialog(this, "Không đủ câu hỏi trong cơ sở dữ liệu!");
            return;
        }

        // Tạo danh sách câu hỏi cho từng đề thi
        ExamBUS examBUS = new ExamBUS();
        for (int i = 0; i < numExams; i++) {
            // Xáo trộn danh sách câu hỏi để tạo đề khác nhau
            Collections.shuffle(allQuestions);
            String quesIDs = allQuestions.stream()
                .limit(numEasy + numMedium + numDiff)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

            ExamDTO exam = new ExamDTO();
            exam.setTestCode(testCode);
            exam.setExOrder(String.valueOf((char) ('A' + i)));
            exam.setExCode(testCode + exam.getExOrder());
            exam.setExQuesIDs(quesIDs); // Gán danh sách ID câu hỏi
            if (!examBUS.addExam(exam)) {
                JOptionPane.showMessageDialog(this, "Thêm đề thi " + exam.getExCode() + " thất bại!");
                return;
            }
        }

        // Tạo TestDTO và chèn vào bảng test
        TestDTO test = new TestDTO();
        test.setTestCode(testCode);
        test.setTestTitle(testTitle);
        test.setTpID(topicID);
        test.setTestTime(testTime);
        test.setTestLimit(testLimit);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date testDate = sdf.parse(testDateStr);
        test.setTestDate(new java.sql.Date(testDate.getTime()));
        test.setNum_easy(numEasy);
        test.setNum_medium(numMedium);
        test.setNum_diff(numDiff);
        test.setTestStatus(1);

        if (testBUS.addTest(test)) {
            JOptionPane.showMessageDialog(this, "Thêm bài thi và đề thi thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm bài thi thất bại!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        e.printStackTrace();
    }// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        loadQuestions();// TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TestDialog dialog = new TestDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
