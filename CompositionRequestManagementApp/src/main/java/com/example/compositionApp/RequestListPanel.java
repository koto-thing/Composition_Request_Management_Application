package com.example.compositionApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *  依頼一覧パネルクラス
 */
public class RequestListPanel extends JPanel  {
    /**
     *  コンストラクタ
     */
    public RequestListPanel() {
        // レイアウト設定
        setLayout(new BorderLayout());

        // テーブルに表示するデータとヘッダーを定義
        String[] columnNames = { "ステータス", "依頼人", "タイトル", "期限", "概要" };
        Object[][] data = {
            { "未対応", "山田太郎", "作曲依頼1", "2024-07-15", "ポップスの作曲をお願いします。" },
            { "対応中", "鈴木花子", "編曲依頼2", "2024-07-20", "ジャズ風の編曲をお願いします。" },
            { "完了", "佐藤次郎", "ミキシング依頼3", "2024-07-10", "ロック調のミキシングをお願いします。" }
        };
        
        // テーブルモデルを作成する
        DefaultTableModel  tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column){
                return false; // セルを編集不可にする
            }
        };
        
        // テーブルモデルを作成し、JTableに設定
        JTable requestTable = new JTable(tableModel);
        requestTable.setFont(new Font("Meiryo", Font.PLAIN, 14));
        requestTable.setRowHeight(24);
        requestTable.getTableHeader()
                .setFont(new Font("Meiryo", Font.BOLD, 14));
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // テーブルをスクロールペインに追加
        JScrollPane tableScrollPane = new JScrollPane(requestTable);
        
        // 詳細パネルを作成
        RequestDetailPanel detailPanel = new RequestDetailPanel();
        
        // スクロールペインと詳細パネルを分割するためのJSplitPaneを作成
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailPanel);
        splitPane.setDividerLocation(450);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);
        
        // テーブルの選択イベントを追加
        requestTable.getSelectionModel()
                        .addListSelectionListener(e -> {
                            // 選択が確定したとき
                            if (!e.getValueIsAdjusting()){
                                
                                // 選択された行のインデックスを取得
                                int selectedRow = requestTable.getSelectedRow();
                                if (selectedRow != -1){
                                    // 選択された行のデータを詳細パネルに表示
                                    Object[] rowData = new Object[columnNames.length];
                                    for (int i = 0 ; i < columnNames.length ; i++){
                                        rowData[i] = tableModel.getValueAt(selectedRow, i);
                                    }
                                    
                                    // 詳細パネルの表示を更新
                                    detailPanel.updateDetails(rowData);
                                }
                            }
                        });
        
        // テーブルヘッダーのクリックイベントを追加
        requestTable.getTableHeader()
                .addMouseListener(new MouseAdapter() {
                    // 列ヘッダーがクリックされたときの処理
                    @Override
                    public void mouseClicked(MouseEvent e){
                        int viewCol = requestTable.getTableHeader()
                                .columnAtPoint(e.getPoint());
                        if (viewCol == -1) return;
                        
                        // モデルの列インデックスを取得
                        int modelCol = requestTable.convertColumnIndexToModel(viewCol);
                        String colName = tableModel.getColumnName(modelCol);

                        // 列の全データを取得
                        List<Object> colValues = new ArrayList<>();
                        for (int r = 0 ; r < tableModel.getRowCount() ; r++){
                            colValues.add(tableModel.getValueAt(r, modelCol));
                        }
                        
                        // 詳細パネルに列データを表示
                        detailPanel.showColumnValues(modelCol, colName, colValues);
                    }
                });
        
        // パネルにスクロールペインを追加
        add(splitPane, BorderLayout.CENTER);
    }
}
