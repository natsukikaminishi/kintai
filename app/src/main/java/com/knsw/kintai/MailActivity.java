package com.knsw.kintai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.Calendar;
import java.util.List;


public class MailActivity extends AppCompatActivity implements TextWatcher {
    // クラス変数
    private String totalOverTime, toAddress, ccAddress, mailTitleText, mailMainText;
    private SqliteOpenHelper helper = new SqliteOpenHelper(KintaiApp.getAppContext());
    private SQLiteDatabase db = helper.getWritableDatabase();
    private CalcUtil calcUtil = new CalcUtil();

    /* TextWatcherのabstract@Override */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    /* TextWatcherのabstract@Override */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    /* TextWatcherのabstract@Override */
    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ContentViewにlayoutを設定
        setContentView(R.layout.activity_mail);

        // Layoutから各パーツ取得
        BootstrapEditText editToAddress= findViewById(R.id.to_edittext);
        BootstrapEditText editCcAddress = findViewById(R.id.cc_edittext);
        BootstrapEditText editTitleText = findViewById(R.id.title_edittext);
        BootstrapEditText editMainText = findViewById(R.id.text_edittext);
        AwesomeTextView textOvertime = findViewById(R.id.overtime_fifteen_textview);

        // editToAddressのリスナーを登録
        editToAddress.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("toAddress", inputStr);
                editor.commit();
            }
        });

        // editCcAddressリスナーを登録
        editCcAddress.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ccAddress", inputStr);
                editor.commit();
            }
        });

        // editTitleTextのリスナーを登録
        editTitleText.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mailTitleText", inputStr);
                editor.commit();
            }
        });

        // editMainTextのリスナーを登録
        editMainText.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mailMainText", inputStr);
                editor.commit();
            }
        });

        // ファイルの準備
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);

        // データの読込
        toAddress = preferences.getString("toAddress", "");
        ccAddress = preferences.getString("ccAddress", "");
        mailTitleText = preferences.getString("mailTitleText", "");
        mailMainText = preferences.getString("mailMainText", "");
        totalOverTime = preferences.getString("totalOverTime", "");

        // データ表示
        editToAddress.setText(toAddress);
        editCcAddress.setText(ccAddress);
        editTitleText.setText(mailTitleText);
        editMainText.setText(mailMainText);
        textOvertime.setText("15日時点の残業時間:" + totalOverTime);

        BootstrapButton mailButton = findViewById(R.id.mailler_button);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // データの読込
                toAddress = preferences.getString("toAddress", "");
                ccAddress = preferences.getString("ccAddress", "");
                mailTitleText = preferences.getString("mailTitleText", "");
                mailMainText = preferences.getString("mailMainText", "");
                totalOverTime = preferences.getString("totalOverTime", "");

                //残業時間置換処理
                mailMainText = mailMainText.replaceAll("ZANGYO",String.valueOf(totalOverTime));

                // mailer 呼び出し
                callMailer();
            }
        });
    }

    //既存メールアプリへデータを保持したまま転送
    private void callMailer(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);

        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:" + toAddress));
        intent.putExtra(Intent.EXTRA_CC, new String[]{ccAddress});
        intent.putExtra(Intent.EXTRA_SUBJECT, mailTitleText);
        intent.putExtra(Intent.EXTRA_TEXT, mailMainText);

        startActivity(Intent.createChooser(intent, null));

    }
}