package com.roadjava.notepad.handler;

import org.apache.commons.lang3.StringUtils;
import com.roadjava.notepad.view.*;
//import com.sun.tools.javac.util.StringUtils;

// 该包为 JDK 内部实现，不应被引用；实际使用 Apache Commons IO 的 FileUtils，故删除此行

import javax.swing.*;
import javax.swing.JFileChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

import static com.sun.tools.javac.util.StringUtils.*;

public class MenuHandler implements ActionListener {

    private MainPage mainPage;
    public MenuHandler(MainPage mainPage) {this.mainPage = mainPage; }

    //监听到事件后会自动回调该方法
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("actionCommand: " + actionCommand);
        if ("newMenuItem".equals(actionCommand)) {
            processNewMenuItem();
        } else if ("newDialogMenuItem".equals(actionCommand)) {
            new MainPage(false);
        } else if ("openMenuItem".equals(actionCommand)) {
            processOpenMenuItem();
        } else if ("saveMenuItem".equals(actionCommand)) {
            doSave();
        }else if ("pageMenuItem".equals(actionCommand)) {
            processPageMenuItem();
        }else if ("printMenuItem".equals(actionCommand)) {
            try {
                processPrintMenuItem();
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        } else if ("exitMenuItem".equals(actionCommand)) {
            System.exit(0);
        }else if ("undoMenuItem".equals(actionCommand)) {
            if(mainPage.getUndoManager().canUndo()) {
                mainPage.getUndoManager().undo();
            }
        }else if ("cutMenuItem".equals(actionCommand)) {
            mainPage.getContArea().cut();
        }else if ("copeMenuItem".equals(actionCommand)) {
            mainPage.getContArea().copy();
        }else if ("pasteMenuItem".equals(actionCommand)) {
            mainPage.getContArea().paste();
        }else if ("deleteMenuItem".equals(actionCommand)) {
            processDeleteMenuItem();
        }else if ("searchMenuItem".equals(actionCommand)) {
            processSearchMenuItem();
        }else if ("replaceMenuItem".equals(actionCommand)) {
            processReplaceMenuItem();
        }else if ("jumpMenuItem".equals(actionCommand)) {
            processJumpMenuItem();
        }else if ("selectAllMenuItem".equals(actionCommand)) {
            mainPage.getContArea().selectAll();
        }else if ("datetimeMenuItem".equals(actionCommand)) {
            processDatetimeMenuItem();
        }else if ("autoBreakLineMenuItem".equals(actionCommand)) {
            boolean selected =  mainPage.getAutoBreakLineMenuItem().isSelected();
            //是否设置自动换行
            mainPage.getContArea().setLineWrap(selected);
        }else if ("fontMenuItem".equals(actionCommand)) {
            new FontDialog(mainPage);
        }else if ("fontColorMenuItem".equals(actionCommand)) {
            processFontColorMenuItem();
        }else if ("backgroundMenuItem".equals(actionCommand)) {
            processBackgroundMenuItem();
        }else if ("zoomInMenuItem".equals(actionCommand)) {
            processZoomInMenuItem();
        }else if ("zoomOutMenuItem".equals(actionCommand)) {
            processZoomOutMenuItem();
        }else if ("defaultFontSizeMenuItem".equals(actionCommand)) {
            processDefaultFontSizeMenuItem();
        }else if ("statusMenuItem".equals(actionCommand)) {
            mainPage.getStatusPanel().setVisible(mainPage.getStatusMenuItem().isSelected());
        }
    }

    private void processStatusMenuItem() {

    }

    private void processDefaultFontSizeMenuItem() {
        Font font =  mainPage.getContArea().getFont();
        Font newFont = new Font(font.getFamily(), font.getStyle(),mainPage.getDefaultFontSize());
        mainPage.getContArea().setFont(newFont);
        
    }

    private void processZoomOutMenuItem() {
        Font font =  mainPage.getContArea().getFont();
        Font newFont = new Font(font.getFamily(), font.getStyle(), font.getSize() - 1);
        mainPage.getContArea().setFont(newFont);
    }

    private void processZoomInMenuItem() {
        Font font =  mainPage.getContArea().getFont();
        Font newFont = new Font(font.getFamily(), font.getStyle(), font.getSize() + 1);
        mainPage.getContArea().setFont(newFont);
    }

    private void processBackgroundMenuItem() {
        JColorChooser.showDialog(mainPage,"选择背景颜色", Color.WHITE);
        //设置编辑器背景颜色
        Color color = JColorChooser.showDialog(mainPage,"选择背景颜色", Color.WHITE);
        if (color != null) {
            mainPage.getContArea().setBackground(color);
        }
    }

    private void processFontColorMenuItem() {
        JColorChooser.showDialog(mainPage,"选择字体颜色", Color.BLACK);
        //设置字体颜色
        Color color = JColorChooser.showDialog(mainPage,"选择字体颜色", Color.BLACK);
        if (color != null) {
            mainPage.getContArea().setForeground(color);
        }
    }

    private void processDatetimeMenuItem() {
        LocalDateTime now = LocalDateTime.now();
        String nowStr =  now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int carePosition = mainPage.getContArea().getCaretPosition();
        mainPage.getContArea().insert(nowStr,carePosition);
    }

    private void processSelectAllMenuItem() {
      new JumpDialog(mainPage);
    }

    private void processJumpMenuItem() {
        new JumpDialog(mainPage);
    }

    private void processReplaceMenuItem() {
        new ReplaceDialog(mainPage);

    }

    private void processSearchMenuItem() {
        new SearchDialog(mainPage);
    }

    private void processDeleteMenuItem() {
        //获取光标位置:光标前面所有行的字符的个数（包括换行符）
        int carePosition = mainPage.getContArea().getCaretPosition();
        if(carePosition == mainPage.getContArea().getCaretPosition()) {
            return;
        }
        mainPage.getContArea().replaceRange("", carePosition - 1, carePosition);
    }


    private void processPrintMenuItem() throws PrinterException {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        if(printerJob.printDialog()) {
            try {
                printerJob.print();
            }catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }

    private void processPageMenuItem() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.pageDialog(printerJob.defaultPage());
    }

    private void processNewMenuItem() {
        if(mainPage.isContSaved()){
            //已保存
            reset();
        }else{
            int i= JOptionPane.showConfirmDialog(mainPage,"是否保存","记事本-提示",JOptionPane.YES_NO_OPTION);
            if(i == JOptionPane.YES_OPTION){
                doSave();
            }else if(i == JOptionPane.NO_OPTION){
                reset();
            }
        }
    }

    private void processOpenMenuItem() {
        JFileChooser jFileChooser = new JFileChooser();
        //设置默认打开的目录
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        //文件选择对话框
        int clickBtn = jFileChooser.showOpenDialog(mainPage);
        if(clickBtn == JFileChooser.APPROVE_OPTION){
            //选中了某个文件然后点击了打开按钮
            File selectedFile = jFileChooser.getSelectedFile();
            try{
                String fileContent = FileUtils.readFileToString(selectedFile,StandardCharsets.UTF_8);
                mainPage.getContArea().setText(fileContent);
                mainPage.setTitle(selectedFile.getName());
                mainPage.setContSaved(true);
                mainPage.setFilePath(selectedFile.getAbsolutePath());
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

//    private void doSave() {
//        String filePath = mainPage.getFilePath();
//        String title = mainPage.getTitle();
//        if (filePath == null || filePath.trim().isEmpty()) {
//            //未保存到磁盘上
//            //文件选择器
//            JFileChooser chooser = new JFileChooser();
//            //文件保存对话框
//            int savedOption = JFileChooser.showSaveDialog(mainPage);
//            if (savedOption == JFileChooser.APPROVE_OPTION) {
//
//                File selectedFile = JFileChooser.getSelectedFile();
//                filePath = selectedFile.getAbsolutePath();
//                title = selectedFile.getName();
//                mainPage.setFilePath(filePath);
//                mainPage.setTitle(title);
//
//            } else {
//                return;
//            }
//        } else {
//            //已经保存到磁盘上
//            mainPage.setTitle(StringUtils.substringAfter(title, "*"));
//        }
//        mainPage.setContSaved(true);
//        try {
//            FileUtils.writeStringToFile(new File(filePath), mainPage.getContArea().getText(), StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void doSave() {
        String filePath = mainPage.getFilePath();
        String title = mainPage.getTitle();
        if (filePath == null || filePath.trim().isEmpty()) {
            //未保存到磁盘上
            //文件选择器
            JFileChooser chooser = new JFileChooser();
            //文件保存对话框
            // 修正：使用chooser实例调用方法，而不是JFileChooser类
            int savedOption = chooser.showSaveDialog(mainPage);
            if (savedOption == JFileChooser.APPROVE_OPTION) {
                // 修正：使用chooser实例调用方法
                File selectedFile = chooser.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();
                title = selectedFile.getName();
                mainPage.setFilePath(filePath);
                mainPage.setTitle(title);
            } else {
                return;
            }
        } else {
            //已经保存到磁盘上
            mainPage.setTitle(StringUtils.substringAfter(title, "*"));
        }
        mainPage.setContSaved(true);
        try {
            FileUtils.writeStringToFile(new File(filePath), mainPage.getContArea().getText(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void reset() {
        mainPage.getContArea().setText("");
        try {
            Thread.sleep(500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        mainPage.setContSaved(true);
        mainPage.setFilePath(null);
        mainPage.setTitle("未命名文档.txt");
    }
}

