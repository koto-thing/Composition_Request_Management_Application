package com.example.compositionApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *  依頼詳細パネルクラス
 */
public class RequestDetailPanel extends JPanel {
    /**
     * コンストラクタ
     */
    public RequestDetailPanel() {
        setLayout(new BorderLayout());                                                                     // レイアウトをBorderLayoutに設定
        JLabel placeholder = new JLabel("詳細情報を表示します");               // プレースホルダーテキスト
        placeholder.setFont(new Font("Meiryo", Font.PLAIN, 14)); // プレースホルダーテキスト
        add(placeholder, BorderLayout.CENTER);                                                     // 中央に追加
    } 

    /**
     * 依頼の詳細情報を更新するメソッド
     * @param rowData 依頼の詳細データの配列
     */
    public void updateDetails(Object[] rowData) {
        removeAll(); // 既存のコンポーネントを削除
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 縦方向のBoxLayoutに設定
        
        // 各データをJLabelとして追加
        for (Object data : rowData) {
            JLabel label = new JLabel(data.toString());
            label.setFont(new Font("Meiryo", Font.PLAIN, 14));
            add(label);
        }
        
        revalidate(); // レイアウトを再計算
        repaint();      // パネルを再描画
    }

    /**
     * 指定された列の値を表示するメソッド
     * @param columnIndex 列のインデックス
     * @param columnName 列の名前
     * @param columnValues 列の値のリスト
     */
    public void showColumnValues(int columnIndex, String columnName, List<Object> columnValues) {
        removeAll();                                                                                                 // 既存のコンポーネントを削除
        
        setLayout(new BorderLayout());                                                                // レイアウトをBorderLayoutに設定
        JLabel titleLabel = new JLabel("列: " + columnName);                // タイトルラベル
        titleLabel.setFont(new Font("Meiryo", Font.BOLD, 16)); // タイトルフォントを太字に設定
        add(titleLabel, BorderLayout.NORTH);                                                     // タイトルラベルを上部に追加

        // 列の値をJListで表示
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Object value : columnValues) {
            listModel.addElement(value.toString());
        }
        
        JList<String> list = new JList<>(listModel);                                 // JListを作成
        list.setFont(new Font("Meiryo", Font.PLAIN, 14)); // フォント設定
        add(new JScrollPane(list), BorderLayout.CENTER);                       // JListをスクロールペインに追加して中央に配置

        revalidate(); // レイアウトを再計算
        repaint();      // パネルを再描画
    }
}