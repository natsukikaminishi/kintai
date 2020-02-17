package com.knsw.kintai;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

/*
 * FtpOperator
 * 非同期処理を行うAsyncTask<Params, Progress, Result>を継承
 */
public class FtpOperator extends AsyncTask<Integer, Integer, Integer> {

    private String remotoDirectoryPass = "";

    // コンストラクタ
    public FtpOperator() {
    }

    // コンストラクタ
    public FtpOperator(String remotoDirectoryPass) {
        this.remotoDirectoryPass = remotoDirectoryPass;
    }

    public int ftpUpload(Integer param) {

        // ホスト
        String host = CommonConst.KNSW_FTP_SERVER;
        // ポート番号
        int port = 21;
        // ユーザ名
        String user = "";
        // パスワード
        String password = "";
        // タイムアウト時間[ms]
        int timeout = 60000; // デバッグ用タイムアウト秒
        // 成功判断
        int successFlg = 0;

        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("SJIS");

        // SharedPreferences ファイルの準備
        SharedPreferences preferences = KintaiApp.getSharedPreferences();
        // name, ftpAccount, ftpPasswordの読み込み
        String name = preferences.getString("name", "");
        String ftpAccount = preferences.getString("ftpAccount", "");
        String ftpPassword = preferences.getString("ftpPassword", "");
        // user, passwordをSharedPreferencesより設定
        user = ftpAccount;
        password = ftpPassword;

        try {
            // paramが0または、1の場合は、ログイン
            if (param == 0 || param == 1) {
                // 接続
                ftpClient.setDefaultTimeout(timeout);
                ftpClient.connect(host, port);
                if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) == false) {
                    // 接続エラー
                    successFlg = 1;
                    throw new Exception("接続エラー");
                }
                // ログイン
                ftpClient.setSoTimeout(timeout);
                if (ftpClient.login(user, password) == false) {
                    // 認証エラー
                    successFlg = 2;
                    throw new Exception("認証エラー");
                }

                // ログイン成功は、successFlg 11
                successFlg = 10;
            }
            // paramが1の場合は、ファイル転送
            if (param == 1) {
                // 指定ディレクトリに移動 【setControlEncoding("SJIS")必須】
                ftpClient.setDataTimeout(timeout);
                // 開発用パスを使用　/E06_システム開発2部/01_部会/2019年度/02_部内取組/アプリ開発/bk
//                if (ftpClient.changeWorkingDirectory(remotoDirectoryPass) == false) {
                if (ftpClient.changeWorkingDirectory(CommonConst.REMOTE_DIRCTORY_KAIHATSU) == false) {
                    // ディレクトリ移動エラー
                    successFlg = 3;
                    throw new Exception("ディレクトリ移動エラー");
                }
                // ファイル転送モード設定
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                // アップロード
                FileInputStream is = null;
                // ローカルファイル名設定
                is = KintaiApp.getAppContext().openFileInput(name + ".txt");
                // リモートファイル名設定
                ftpClient.storeFile(name + ".txt", is);
                is.close();

                // FTP転送成功は、successFlg 11
                successFlg = 11;
            }
        } catch (FileNotFoundException fne) {
            // 例外時処理
            fne.printStackTrace();
            successFlg = 4;
        }catch (ConnectException e){
            // 例外時処理
            successFlg = 5;
        }catch (Exception e) {
            // 例外時処理
            e.printStackTrace();
        } finally {
            // 切断
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    // 例外時処理
                    successFlg = 6;
                }
            }
        }
        return successFlg;
    }

    // 非同期処理用 Listener
    private Listener listener;

    // 非同期処理
    @Override
    protected Integer doInBackground(Integer... params) {

        int sf = 0;

        try {
            Thread.sleep(1000);
            // パラメータ制御で、接続・認証のみか、ファイル転送まで
            sf = ftpUpload(params[0]);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Log.d("debug", "" + params[0]);

        return sf;
    }

    // 途中経過をメインスレッドに返す
    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (listener != null) {
            listener.onSuccess(progress[0]);
        }
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(Integer result) {
        if (listener != null) {
            listener.onSuccess(result);
        }
    }

    // setListener
    void setListener(Listener listener) {
        this.listener = listener;
    }

    // Listener interface
    interface Listener {
        void onSuccess(int count);
    }
}