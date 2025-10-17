package com.example.compositionApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 *  メインウィンドウクラス
 *  JSplitPaneを使用して、画面を左右に分割する。
 */
public class MainWindow extends JFrame {
    private CardLayout cardLayout; // カードレイアウト
    private JPanel rightPanel;           // 右側パネル
    
    /**
     *  コンストラクタ
     */
    public MainWindow(){
        /* ウィンドウの基本設定 */
        setTitle("Composition Application"); // ウィンドウのタイトル
        setSize(1500, 1000);   // ウィンドウのサイズ
        setLocationRelativeTo(null);             // 画面中央に表示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 閉じるボタンでプログラムを終了する。
        
        /* 左側のメニューパネル */
        String[] menuItems = { "依頼一覧", "カレンダー", "設定"};    // メニュー項目
        JList<String> menuList = new JList<>(menuItems);                          // メニューJList
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 単一選択モード
        menuList.setFont(new Font("Meiryo", Font.PLAIN, 16));   // フォント設定

        // JListをスクロール可能にする
        JScrollPane leftPanel = new JScrollPane(menuList);                           // 左側パネル
        leftPanel.setMinimumSize(new Dimension(150, 0)); // 最小幅を設定
        leftPanel.setBorder(BorderFactory.createTitledBorder("メニュー"));        // 枠タイトルを設定

        /* 右側のコンテンツパネル */
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);
        
        // 各コンテンツパネルを作成して右側パネルに追加する
        RequestListPanel requestListPanel = new RequestListPanel(); // 依頼一覧パネル
        CalenderPanel calenderPanel = new CalenderPanel();             // カレンダーパ
        
        // 右側パネルに各コンテンツパネルを追加
        rightPanel.add(requestListPanel, "依頼一覧");
        rightPanel.add(calenderPanel, "カレンダー");
        
        /* JSplitPaneで左右のパネルを結合する */
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,  rightPanel); // 水平方向に分割
        splitPane.setDividerLocation(200);                                                                                         // 初期の分割位置を設定
        
        /* ウィンドウにJSplitPaneを追加する */
        add(splitPane);
        
        /* メニューが選択された時の処理を追加する */
        menuList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 選択が確定したとき
                if (!e.getValueIsAdjusting()) {
                    
                    // 選択されたメニュー項目を取得
                    String selectedMenu = menuList.getSelectedValue();
                    if (selectedMenu != null) {
                        cardLayout.show(rightPanel, selectedMenu);
                    }
                }
            }});
        
        /* ウィンドウを表示状態にする */
        menuList.setSelectedIndex(0);
        setVisible(true);
    }
}
