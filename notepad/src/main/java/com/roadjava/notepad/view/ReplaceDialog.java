//package com.roadjava.notepad.view;
//
//import com.sun.tools.javac.util.StringUtils;
//
//import javax.swing.*;
//import javax.swing.JPanel;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//
////替换对话框
//
//public class ReplaceDialog extends JDialog implements ActionListener {
//    private MainPage mainPage;
//    private JTextField searchField = new JTextField();
//    private JTextField replaceField = new JTextField();
//    private JCheckBox caseSensitiveBox = new JCheckBox("是否区分大小写");
//    private JCheckBox loopBox = new JCheckBox("循环查找");
//    private JButton searchNextBtn = new JButton("查找下一个");
//    private JButton replaceBtn = new JButton("替换");
//    private JButton replaceAllBtn = new JButton("替换全部");
//    private JButton canceBtn = new JButton("取消");
//    public ReplaceDialog(MainPage mainPage) {
//        super(mainPage, "替换",true);
//        this.mainPage = mainPage;
//        //默认流式布局，传入null，表示绝对布局
//        JPanel searchPanel = new JPanel(null);
//        JLabel searchLabel = new JLabel("查找内容：");
//
//        jPanel.add(searchLabel);
//        jPanel.add(searchField);
//        jPanel.add(searchNextBtn);
//        jPanel.add(caseSensitiveBox);
//        jPanel.add(loopBox);
//        jPanel.add(canceBtn);
//
//
//        JLabel replaceLabel = new JLabel("替换内容：");
//        jPanel.add(replaceLabel);
//        jPanel.add(replaceField);
//        jPanel.add(replaceBtn);
//        jPanel.add(replaceAllBtn);
//
//
//
//
//
//        //对组件布局,x,y:相对左上角的顶点而言
//        searchLabel.setBounds(30,30,100,40);
//
//        searchField.setBounds(150,30,100,40);
//
//        searchNextBtn.addActionListener(this);
//        searchNextBtn.setBounds(320,30,150,40);
//
//        canceBtn.addActionListener(this);
//        canceBtn.setBounds(320,210,150,40);
//
//        caseSensitiveBox.addActionListener(this);
//        caseSensitiveBox.setBounds(30,160,150,40);
//        loopBox.addActionListener(this);
//        loopBox.setBounds(30,220,150,40);
//
//        replaceLabel.setBounds(30,90,100,40);
//        replaceField.setBounds(150,90,150,40);
//        replaceBtn.addActionListener(this);
//        replaceBtn.setBounds(320,90,150,40);
//        replaceAllBtn.addActionListener(this);
//        replaceAllBtn.setBounds(320,150,150,40);
//
//
//
//
//        this.getContentPane().add(jPanel);
//
//        setSize(500,300);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setResizable(false);
//        setVisible(true);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        //除了用actioncommand,也可以用getSource来半段谁被点击了
//        if(e.getSource() == canceBtn) {
//            this.dispose();
//        }else if(e.getSource() == searchNextBtn) {
//            //处理查找下一个按钮
//            String searchText = searchField.getText();
//            if (StringUtils.isBlank(searchText)) {
//                return;
//            }
//            int carePosition = mainPage.getContArea().getCaretPosition();
//            doSearch(searchText, carePosition, false);
//        }else if(e.getSource() == replaceBtn) {
//            //替换点击按钮处理
//            String selectedText =  mainPage.getContArea().getSelectedText();
//            if(StringUtils.isBlank(selectedText)){
//                JOptionPane.showMessageDialog(this,"没有要被替换的内容");
//                return;
//            }
//            //获取选中内容的起始下标
//            int selectionStart =  mainPage.getContArea().getSelectionStart();
//            int selectionEnd =  mainPage.getContArea().getSelectionEnd();
//            mainPage.getContArea().replaceRange(replaceField.getText(),selectionStart,selectionEnd);
//        }else if(e.getSource() == replaceAllBtn) {
//            //全部替换按钮点击处理
//            String content = mainPage.getContArea().getText();
//            //获取替换后的字符串
//            content = content.replaceAll(searchField.getText(),replaceField.getText());
//            mainPage.getContArea().setText(content);
//        }
//    }
//
//
//    //search要搜索的内容，fromIndex从哪里开始查找的下标，从0开始
//    private void doSearch(String searchText, int fromIndex,boolean isFromLoop) {
//        String content = mainPage.getContArea().getText();
//        if(StringUtils.isBlank(content)){
//            return;
//        }
//        //从fromIndex所在的位置往后查找
//        int index;
//        if(caseSensitiveBox.isSelected()){
//            //要区分大小写
//            index = content.indexOf(searchText,fromIndex);
//        }else{
//            //不区分大小写
//            index = StringUtils.indexOfIgnoreCase(content,searchText,fromIndex);
//        }
//        if(index > -1){
//            //搜索到了
//            mainPage.getContArea().select(index,index + searchText.length());
//
//        }else{
//            if(loopBox.isSelected() && !isFromLoop){
//                //选中循环搜索
//
//                doSearch(searchText,0,true);
//            }else{
//                JOptionPane.showMessageDialog(this,"找不到\""+searchText+"\"");
//            }
//        }
//    }
//}





package com.roadjava.notepad.view;

import org.apache.commons.lang3.StringUtils;  // 修正：使用正确的StringUtils
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//替换对话框
public class ReplaceDialog extends JDialog implements ActionListener {
    private MainPage mainPage;
    private JTextField searchField = new JTextField();
    private JTextField replaceField = new JTextField();
    private JCheckBox caseSensitiveBox = new JCheckBox("是否区分大小写");
    private JCheckBox loopBox = new JCheckBox("循环查找");
    private JButton searchNextBtn = new JButton("查找下一个");
    private JButton replaceBtn = new JButton("替换");
    private JButton replaceAllBtn = new JButton("替换全部");
    private JButton canceBtn = new JButton("取消");

    // 修正：声明并初始化主面板
    private JPanel mainPanel = new JPanel(null);

    public ReplaceDialog(MainPage mainPage) {
        super(mainPage, "替换", true);
        this.mainPage = mainPage;

        JLabel searchLabel = new JLabel("查找内容：");

        // 修正：使用 mainPanel 而不是 jPanel
        mainPanel.add(searchLabel);
        mainPanel.add(searchField);
        mainPanel.add(searchNextBtn);
        mainPanel.add(caseSensitiveBox);
        mainPanel.add(loopBox);
        mainPanel.add(canceBtn);

        JLabel replaceLabel = new JLabel("替换内容：");
        mainPanel.add(replaceLabel);
        mainPanel.add(replaceField);
        mainPanel.add(replaceBtn);
        mainPanel.add(replaceAllBtn);

        //对组件布局, x, y:相对左上角的顶点而言
        searchLabel.setBounds(30, 30, 100, 40);
        searchField.setBounds(150, 30, 100, 40);

        searchNextBtn.addActionListener(this);
        searchNextBtn.setBounds(320, 30, 150, 40);

        canceBtn.addActionListener(this);
        canceBtn.setBounds(320, 210, 150, 40);

        caseSensitiveBox.addActionListener(this);
        caseSensitiveBox.setBounds(30, 160, 150, 40);
        loopBox.addActionListener(this);
        loopBox.setBounds(30, 220, 150, 40);

        replaceLabel.setBounds(30, 90, 100, 40);
        replaceField.setBounds(150, 90, 150, 40);
        replaceBtn.addActionListener(this);
        replaceBtn.setBounds(320, 90, 150, 40);
        replaceAllBtn.addActionListener(this);
        replaceAllBtn.setBounds(320, 150, 150, 40);

        // 修正：使用 mainPanel
        this.getContentPane().add(mainPanel);

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //除了用actioncommand,也可以用getSource来判断谁被点击了
        if (e.getSource() == canceBtn) {
            this.dispose();
        } else if (e.getSource() == searchNextBtn) {
            //处理查找下一个按钮
            String searchText = searchField.getText();
            // 修正：使用正确的StringUtils.isBlank方法
            if (StringUtils.isBlank(searchText)) {
                return;
            }
            int carePosition = mainPage.getContArea().getCaretPosition();
            doSearch(searchText, carePosition, false);
        } else if (e.getSource() == replaceBtn) {
            //替换点击按钮处理
            String selectedText = mainPage.getContArea().getSelectedText();
            // 修正：使用正确的StringUtils.isBlank方法
            if (StringUtils.isBlank(selectedText)) {
                JOptionPane.showMessageDialog(this, "没有要被替换的内容");
                return;
            }
            //获取选中内容的起始下标
            int selectionStart = mainPage.getContArea().getSelectionStart();
            int selectionEnd = mainPage.getContArea().getSelectionEnd();
            mainPage.getContArea().replaceRange(replaceField.getText(), selectionStart, selectionEnd);
        } else if (e.getSource() == replaceAllBtn) {
            //全部替换按钮点击处理
            String content = mainPage.getContArea().getText();
            //获取替换后的字符串
            content = content.replaceAll(searchField.getText(), replaceField.getText());
            mainPage.getContArea().setText(content);
        }
    }

    //search要搜索的内容，fromIndex从哪里开始查找的下标，从0开始
    private void doSearch(String searchText, int fromIndex, boolean isFromLoop) {
        String content = mainPage.getContArea().getText();
        // 修正：使用正确的StringUtils.isBlank方法
        if (StringUtils.isBlank(content)) {
            return;
        }
        //从fromIndex所在的位置往后查找
        int index;
        if (caseSensitiveBox.isSelected()) {
            //要区分大小写
            index = content.indexOf(searchText, fromIndex);
        } else {
            //不区分大小写
            // 修正：使用正确的StringUtils.indexOfIgnoreCase方法
            index = StringUtils.indexOfIgnoreCase(content, searchText, fromIndex);
        }
        if (index > -1) {
            //搜索到了
            mainPage.getContArea().select(index, index + searchText.length());
        } else {
            if (loopBox.isSelected() && !isFromLoop) {
                //选中循环搜索
                doSearch(searchText, 0, true);
            } else {
                JOptionPane.showMessageDialog(this, "找不到\"" + searchText + "\"");
            }
        }
    }
}