package com.example.compositionApp;

import javax.swing.*;
import java.awt.*;

public class CalenderPanel  extends JPanel {
    public CalenderPanel(){
        // パネルの背景色を設定する
        setBackground(new Color(230, 255, 230));
        
        // 表示するラベルを作成する
        JLabel label = new JLabel("ここはカレンダー画面です。");
        label.setFont(new Font("Meiryo", Font.PLAIN, 24));
        
        // パネルにラベルを追加
        add(label);
    }
}
