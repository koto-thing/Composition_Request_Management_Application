package com.example.compositionApp;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    /**
     *  エントリーポイント
     * @param args コマンドライン引数
     */
    public static void main(String[] args){
        
        // GUIを作成する前に、システムのルックアンドフィールを設定する
        FlatDarkLaf.setup();
        
        // イベントディスパッチャスレッドでGUIを生成・表示する
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }
}