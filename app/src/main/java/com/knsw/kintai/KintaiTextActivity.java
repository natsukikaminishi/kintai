package com.knsw.kintai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class KintaiTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // ContentViewにlayoutを設定
        setContentView(R.layout.kintai_text);

        // SharedPreferences ファイルの準備
        SharedPreferences preferences = KintaiApp.getSharedPreferences();
        // nameの読み込み
        String name = preferences.getString("name", "");

        // FileOperatorによるファイル作成
        FileOperator fo = new FileOperator();
        String kintai_text = fo.readFile(name + ".txt");

        // 設定する
        TextView kintaiTextView = findViewById(R.id.kintai_text_textview);
        kintaiTextView.setText(kintai_text);
    }
}