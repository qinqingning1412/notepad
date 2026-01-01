package com.roadjava.notepad;

import com.roadjava.notepad.view.MainPage;

import javax.swing.*;

public class NotepadAPP {
    public static void main(String[] args) {
        //设置观感，美化界面
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainPage(true);
    }
}
