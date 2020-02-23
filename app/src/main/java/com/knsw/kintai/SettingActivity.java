package com.knsw.kintai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity implements TextWatcher {

    // クラス変数
    private String name, startTime, endTime, breakTime, workContent, ftpAccount, ftpPassword;
    private CustomDialog customDialog = new CustomDialog();
    private FtpOperator ftpOperator;

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
        setContentView(R.layout.setting);
        // Layoutから各パーツ取得
        EditText nameEditText = findViewById(R.id.name_edittext);
        EditText startTimeEditText = findViewById(R.id.startTime_edittext);
        EditText endTimeEditText = findViewById(R.id.endTime_edittext);
        EditText breakTimeEditText = findViewById(R.id.breakTime_edittext);
        EditText ftpAccountEditText = findViewById(R.id.ftpAccount_edittext);
        EditText ftpPasswordEditText = findViewById(R.id.ftpPassword_edittext);
        EditText workContentEditText = findViewById(R.id.workContent_edittext);
        BootstrapButton confirmEditButton = findViewById(R.id.confirm_Button);
        EditText localdirEdittext = findViewById(R.id.localdir_edittext);
        EditText remotedirEdittext = findViewById(R.id.remotedir_edittext);
        Spinner monthSpinner = findViewById(R.id.month_spinner);
        BootstrapButton createFileButton = findViewById(R.id.create_file_Button);
        BootstrapButton transferFileButton = findViewById(R.id.transfer_file_Button);
        BootstrapButton confirmFileButton = findViewById(R.id.confirm_file_Button);
        BootstrapButton mailButton = findViewById(R.id.mail_Button);

        // directoryとmonthSpinnerについては、初期セットを行う
        localdirEdittext.setText(getFilesDir().toString());
        remotedirEdittext.setText(CommonConst.REMOTE_DIRCTORY_KAIHATSU);
        monthSpinner.setSelection(1);

        // nameEditTextのリスナーを登録
        nameEditText.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", inputStr);
                editor.commit();
            }
        });

        // startTimeEditTextのリスナーを登録
        startTimeEditText.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("startTime", inputStr);
                editor.commit();
            }
        });

        // endTimeEditTextのリスナーを登録
        endTimeEditText.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("endTime", inputStr);
                editor.commit();
            }
        });

        // breakTimeEditTextのリスナーを登録
        breakTimeEditText.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("breakTime", inputStr);
                editor.commit();
            }
        });

        // workContentEditTextのリスナーを登録
        workContentEditText.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("workContent", inputStr);
                editor.commit();
            }
        });

        // ftpAccountEditTextのリスナーを登録
        ftpAccountEditText.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ftpAccount", inputStr);
                editor.commit();
            }
        });

        // ftpPasswordEditTextのリスナーを登録
        ftpPasswordEditText.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ftpPassword", inputStr);
                editor.commit();
            }
        });

        // localdirEdittextのリスナーを登録
        localdirEdittext.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("localdir", inputStr);
                editor.commit();
            }
        });

        // remotedirEdittextのリスナーを登録
        remotedirEdittext.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // テキスト変更後に変更されたテキストを取り出す
                String inputStr = s.toString();
                // ファイルの準備
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                // データの保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remotedir", inputStr);
                editor.commit();
            }
        });

        // ファイルの準備
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);

        // データの読込
        name = preferences.getString("name", "");
        startTime = preferences.getString("startTime", "");
        endTime = preferences.getString("endTime", "");
        breakTime = preferences.getString("breakTime", "");
        workContent = preferences.getString("workContent", "");
        ftpAccount = preferences.getString("ftpAccount", "");
        ftpPassword = preferences.getString("ftpPassword", "");

        // データ表示
        nameEditText.setText(name);
        startTimeEditText.setText(startTime);
        endTimeEditText.setText(endTime);
        breakTimeEditText.setText(breakTime);
        workContentEditText.setText(workContent);
        ftpAccountEditText.setText(ftpAccount);
        ftpPasswordEditText.setText(ftpPassword);

        // startTimeEditTextクリック時のダイアログ呼び出し
        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TimePickerの呼び出し
                customDialog.showTimePicker(SettingActivity.this, startTimeEditText, null,null,null);
            }
        });

        // endTimeEditTextクリック時のダイアログ呼び出し
        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TimePickerの呼び出し
                customDialog.showTimePicker(SettingActivity.this, null, endTimeEditText,null,null);
            }
        });

        // breakTimeEditTextクリック時のダイアログ呼び出し
        breakTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TimePickerの呼び出し
                customDialog.showTimePicker(SettingActivity.this, null,null,breakTimeEditText, null);
            }
        });

        // confirmEditButtonクリック時のダイアログ呼び出し
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FtpOperatorによる認証を実行
                ftpOperator = new FtpOperator();
                ftpOperator.setListener(createListener());
                ftpOperator.execute(0);
            }
        });

        // createFileButtonクリック時のダイアログ呼び出し
        createFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FileOperatorによるファイル作成
                FileOperator fo = new FileOperator();
                Calendar calendar = Calendar.getInstance();
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                String month = "";

                // 作成するファイル判断をSpinnerの値から行う
                if("今月".equals(monthSpinner.getSelectedItem().toString())) {
                    month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                } else if("前月".equals(monthSpinner.getSelectedItem().toString())){
                    month = String.valueOf(calendar.get(Calendar.MONTH));
                    if("1".equals(month)){
                        year = "" + (Integer.parseInt(year) -1);
                    }
                }

                // ファイルを作成する
                boolean successFlg = fo.saveFile(name + ".txt", year, month);

                // FileOperatorのResultによるToastメッセージを制御
                if(successFlg) {
                    // "Successful file creation"
                    Toast toast = Toast.makeText(SettingActivity.this, "ファイルを作成しました", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    // "File creation failure"
                    Toast toast = Toast.makeText(SettingActivity.this, "ファイルの作成に失敗しました", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        // transferFileButtonクリック時のダイアログ呼び出し
        transferFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 転送先のファイルを指定
                // FileOperatorによるファイル作成
                FileOperator fo = new FileOperator();
                Calendar calendar = Calendar.getInstance();
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                String month = "";

                // 作成するファイル判断をSpinnerの値から行う
                if("今月".equals(monthSpinner.getSelectedItem().toString())) {
                    month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                } else if("前月".equals(monthSpinner.getSelectedItem().toString())){
                    month = String.valueOf(calendar.get(Calendar.MONTH));
                    if("1".equals(month)){
                        year = "" + (Integer.parseInt(year) -1);
                    }
                }
                // 転送先ファイルパス
                String remotoDirectoryPass =
                        CommonConst.REMOTE_DIRCTORY + "/" + year + "年/" + year + "年" + month + "月";

                // FtpOperatorによる認証～転送までを実行
                ftpOperator = new FtpOperator(remotoDirectoryPass);
                ftpOperator.setListener(createListener());
                ftpOperator.execute(1);
            }
        });

        // confirmFileButtonクリック時のダイアログ呼び出し
        confirmFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), KintaiTextActivity.class);
                // intent.putExtra("overTime", overTime);
                startActivity(intent);
            }
        });

        // mailButtonクリック時のダイアログ呼び出し
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MailActivity.class);
                //  intent.putExtra("overTime", overTime);
                startActivity(intent);
            }
        });
    }

    /*
     * CustomToastの制御メソッド
     */
    public void showCustomToast(String showText) {
        TextView text = new TextView(getApplicationContext());
        //Toastに表示する文字
        text.setText(showText);
        //フォントの種類
        // text.setTypeface(Typeface.SANS_SERIF);
        //フォントの大きさ
        text.setTextSize(20);
        //フォントの色
        text.setTextColor(Color.WHITE);
        //文字の背景色(ARGB)
        //text.setBackgroundColor(getResources().getColor(R.color.colorSuccess));

        //Toastの表示
        Toast toast = new Toast(getApplicationContext());
        toast.setView(text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /*
     * FtpOperatorのリスナー
     * sucessFlgをResultで受け取り、Toastによるエラーメッセージ制御
     */
    private FtpOperator.Listener createListener() {
        return new FtpOperator.Listener() {
            @Override
            public void onSuccess(int successFlg) {
                String infoMessage = "";
                if (successFlg == 10) {
                    infoMessage = "接続成功"; //"Successful connection";
                } else if (successFlg == 11) {
                    infoMessage = "ファイル転送成功";//"Successful file transfer";
                } else if (successFlg == 1) {
                    infoMessage = "接続に失敗しました";//"Connection Failed";
                } else if (successFlg == 2) {
                    infoMessage = "認証に失敗しました";//"Authentication failure";
                } else if (successFlg == 3) {
                    infoMessage = "転送先のディレクトリーが存在しません";//"Directory does not exist";
                } else if (successFlg == 4) {
                    infoMessage = "転送するファイルが存在しません";//"The file does not exist";
                } else if (successFlg == 5) {
                    infoMessage = "ネットワークに接続できません";//"Network is unreachable";
                } else if (successFlg == 6) {
                    infoMessage = "FTPの切断に失敗しました";//"Connection is missing";
                } else {
                    infoMessage =  "予期せぬエラーが発生しました";//"Other exceptions : successFlg " + successFlg;
                }
                Toast toast = Toast.makeText(SettingActivity.this, infoMessage, Toast.LENGTH_LONG);
                toast.show();
            }
        };
    }
}