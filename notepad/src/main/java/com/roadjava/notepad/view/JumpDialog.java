package com.roadjava.notepad.view;

import com.sun.tools.javac.util.StringUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//跳转对话框

public class JumpDialog extends JDialog implements ActionListener {
    private MainPage mainPage;
    private JTextArea lineNumField = new JTextArea();
    private JButton jumpButton = new JButton("跳转");

    public JumpDialog(MainPage mainPage) {
        super(mainPage, "查找",true);
        this.mainPage = mainPage;
        //默认流式布局，传入null，表示绝对布局
        JPanel jPanel = new JPanel(null);
        JLabel tipLabel = new JLabel("输入行号：");
        jPanel.add(tipLabel);
        jPanel.add(lineNumField);
        jPanel.add(jumpButton);
        tipLabel.setBounds(30,40,100,40);
        lineNumField.setBounds(150,40,150,40);

        jumpButton.addActionListener(this);
        jumpButton.setBounds(320,40,100,40);


        this.getContentPane().add(jPanel);

        setSize(500,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //除了用actioncommand,也可以用getSource来半段谁被点击了
        if(e.getSource() == jumpButton) {
            String lineNumStr = lineNumField.getText();
            int lineNum;
            try {
                lineNum = Integer.parseInt(lineNumStr);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"请输入数字");
                return;
            }
            //获取编辑区域的总行书
            int lineCount =  mainPage.getContArea().getLineCount();
            if(lineNum < lineCount) {
                JOptionPane.showMessageDialog(this,"请输入不大于" + lineCount +"的行号" );
                return;
            }
            try {
                //参数从0开始
                int LineStartOffset =  mainPage.getContArea().getLineStartOffset(lineNum - 1);
                mainPage.getContArea().setCaretPosition(LineStartOffset);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            this.dispose();

        }
    }

}
