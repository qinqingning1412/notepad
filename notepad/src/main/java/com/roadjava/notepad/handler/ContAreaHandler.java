package com.roadjava.notepad.handler;


import com.roadjava.notepad.view.MainPage;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;


//ContArea组件事件处理
public class ContAreaHandler implements DocumentListener, CaretListener {
    private MainPage mainPage;
    public ContAreaHandler(MainPage mainPage) {this.mainPage = mainPage; }

//插入内容时自动回调
    @Override
    public void insertUpdate(DocumentEvent e) {
        processContChange();
    }

    private void processContChange() {
       String title = mainPage.getTitle();
       if(title.startsWith("*")){
           return;
       }
       mainPage.setTitle("*" + title);
       mainPage.setContSaved(false);
    }

    //删除内容时自动回调
    @Override
    public void removeUpdate(DocumentEvent e) {
        processContChange();

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    //导致光标移动的原因很多：输入字符，删除字符，按键盘的箭头按键灯，只要是光标一栋楼，会自动回调吃方法
    @Override
    public void caretUpdate(CaretEvent e) {
        JTextArea contArea= mainPage.getContArea();
        int carePosition = contArea.getCaretPosition();
        int lineNum = 1;
        int columnNum = 1;
        try {
            //通过偏移量（下标）获取行号，参数和返回值从0开始
            lineNum = contArea.getLineOfOffset(carePosition) + 1;
            //获取行首的偏移量(下标)，参数和返回值从0开始
            int lineStartOffset =  contArea.getLineStartOffset(lineNum - 1);
            columnNum = carePosition - lineStartOffset + 1;

        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
        mainPage.getStatusLabel().setText("第" + lineNum + "第" + columnNum + "列");

    }
}
