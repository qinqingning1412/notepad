package com.roadjava.notepad.view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//字体对话框

public class FontDialog extends JDialog implements ActionListener {
    private MainPage mainPage;
    private JComboBox<String>  fontFamilyComboBox;
    private JComboBox<String>  fontStyleComboBox;
    private JComboBox<Integer>  fontSizeComboBox;
    private JButton confirmBtn = new JButton("确认");

    public FontDialog(MainPage mainPage) {
        super(mainPage, "字体",true);
        this.mainPage = mainPage;
        //默认流式布局，传入null，表示绝对布局
        JPanel jPanel = new JPanel(null);

        //设置系统字体名称
        GraphicsEnvironment localGraphicsEnvironment =  GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFontFamilyNames =  localGraphicsEnvironment.getAvailableFontFamilyNames();
        fontFamilyComboBox = new JComboBox<>(availableFontFamilyNames);
        jPanel.add(fontFamilyComboBox);

        String[] fontStyleArr = {"Plain","Bold","Italic"};
        fontStyleComboBox = new JComboBox<>(fontStyleArr);
        jPanel.add(fontStyleComboBox);

        Integer[] fontSizeArr = {10,20,30,40,50};
        fontSizeComboBox = new JComboBox<>(fontSizeArr);
        jPanel.add(fontSizeComboBox);

        confirmBtn.addActionListener(this);
        jPanel.add(confirmBtn);

        //给元素定位
        fontFamilyComboBox.setBounds(50,50,200,40);
        fontStyleComboBox.setBounds(300,50,100,40);
        fontSizeComboBox.setBounds(430,50,80,40);
        confirmBtn.setBounds(200,150,100,40);


        this.getContentPane().add(jPanel);


        setSize(550,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //除了用actioncommand,也可以用getSource来半段谁被点击了
        if(e.getSource() == confirmBtn) {
           String fontFamily = (String) fontFamilyComboBox.getSelectedItem();
           String fontStyleStr = (String) fontStyleComboBox.getSelectedItem();
           Integer fontSize = (Integer) fontSizeComboBox.getSelectedItem();
           int fontStyle = 0;
           if("Plain".equals(fontStyle)) {
               fontStyle = Font.PLAIN;
           }else if("Bold".equals(fontStyle)) {
               fontStyle = Font.BOLD;
           }else if("Italic".equals(fontStyle)) {
               fontStyle = Font.ITALIC;
           }
           Font font = new Font(fontFamily,fontStyle,fontSize);
           //给编辑区域设置新字体
           mainPage.getContArea().setFont(font);
            this.dispose();

        }
    }

}
