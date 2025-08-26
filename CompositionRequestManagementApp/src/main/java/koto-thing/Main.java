package koto-thing;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final String APPLICATION_NAME = "Composition Request Manager";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens"; // 認証トークンの保存先

    // 読み取り専用スコープ。書き込みもする場合は SheetsScopes.SPREADSHEETS に変更
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "client_secret.json"; // JSONファイルへのパス

    private static Credential getCredentials() throws Exception {
        File credentialsFile = new File(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(credentialsFile)));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        // 初回実行時にブラウザが開き、Googleアカウントでの認証を求められます
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static void main(String[] args) throws Exception {
        // 認証を通してSheetsサービスオブジェクトをビルド
        Sheets service = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        // 読み込むスプレッドシートIDと範囲を指定
        final String spreadsheetId = "YOUR_SPREADSHEET_ID"; // ここにスプレッドシートのIDを記述
        final String range = "フォームの回答 1!A2:E"; // 読み込む範囲（シート名や列を環境に合わせる）

        // データを取得
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();

        // 取得したデータを表示
        if (values == null || values.isEmpty()) {
            System.out.println("データが見つかりません。");
        } else {
            System.out.println("依頼一覧:");
            for (List<Object> row : values) {
                // 各行のデータを取得 (例: A列が依頼者名, B列がメールアドレス...)
                System.out.printf("依頼者: %s, 内容: %s\n", row.get(0), row.get(1));
            }
        }
    }
}