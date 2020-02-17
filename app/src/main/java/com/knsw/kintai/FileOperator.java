package com.knsw.kintai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import static com.knsw.kintai.CommonConst.NL;
import static com.knsw.kintai.CommonConst.TB;

public class FileOperator {

    // クラス変数
    private SqliteOpenHelper helper;
    private SQLiteDatabase db;

    // コンストラクタ
    public FileOperator(){
        helper = new SqliteOpenHelper(KintaiApp.getAppContext());
        db = helper.getWritableDatabase();
    }

    // ファイルを読み出し
    public String readFile(String file) {
        String text = "";

        // try-with-resources
        try (FileInputStream fileInputStream = KintaiApp.getAppContext().openFileInput(file);
             BufferedReader reader= new BufferedReader(
                     new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {

            String lineBuffer;
            while( (lineBuffer = reader.readLine()) != null ) {
                text += lineBuffer.replace("\t", "    ") + "\n" ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    // ファイルを保存
    public boolean saveFile(String file, String year, String month) {

        // 成功失敗判断のreturn値
        boolean successFlg = false;

        // 現行の仕様に準拠
        System.setProperty("file.encoding", "SJIS");

        // ファイルに出力するText作成
        String text = createText(year, month);

        // try-with-resources
        try (FileOutputStream  fileOutputstream =
                     KintaiApp.getAppContext().openFileOutput(file, Context.MODE_PRIVATE);){

            // ファイル書き込み
            fileOutputstream.write(text.getBytes());
            successFlg = true;

            fileOutputstream.close();

        } catch (IOException e) {
            e.printStackTrace();
            successFlg = false;
        }

        return successFlg;
    }

    /*
     * text作成を行うPrivateMethod
     * \n 改行 \t タブ
     */
    private String createText(String year, String month){
        StringBuffer textStrBuf = new StringBuffer();

        // カレンダーインスタンスを作成
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        // 指定月の最大日取得
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 指定月の開始曜日取得 (Sunday始まりの1～7のため+1、1の補正はfor中で行う)
        Calendar firstDay = Calendar.getInstance();
        firstDay.set( Calendar.DATE, 1 );
        int firstDateWeek = firstDay.get( Calendar.DAY_OF_WEEK );

        // まず先頭行にyear\tmonth
        textStrBuf.append("\"").append(year).append(TB).append(String.format("%02d",Integer.parseInt(month))).append(NL);
        // 日数分ループ
        // 日	曜日	休暇	開始時間	終了時間	時間外（普通）	時間外（深夜）	労働時間	作業内容	控除	控除時間
        for(int day = 1; day <= maximum; day++){
            // 共通出力部分
            int dayWeek = (firstDateWeek + day)%7 - 1;
            if(dayWeek == 0){
                dayWeek = 7;
            } else if (dayWeek == -1){
                dayWeek = 6;
            }
            // SQL 実行
            KintaiValueObject kintaiVo = helper.readDayData(helper, db, year, month, "" + day);
            // データがある場合
            if(kintaiVo != null){
                textStrBuf.append(String.format("%02d",day))
                        .append(TB).append(dayWeek)
                        .append(TB).append(convertEmptyToZero(kintaiVo.getDayOff().replace("5","0")))
                        .append(TB).append(trimPreviousZero(convertEmptyToZero(kintaiVo.getStartTime())))
                        .append(TB).append(trimPreviousZero(convertEmptyToZero(kintaiVo.getEndTime())))
                        .append(TB).append(trimPreviousZero(convertEmptyToZero(kintaiVo.getOutOfTimeNormal())))
                        .append(TB).append(trimPreviousZero(convertEmptyToZero(kintaiVo.getOutOfTimeMidNight())))
                        .append(TB).append(trimPreviousZero(convertEmptyToZero(kintaiVo.getWorkTime())))
                        .append(TB).append(kintaiVo.getWorkContents())
                        .append(TB).append(convertEmptyToZero(kintaiVo.getLateOrEarly()))
                        .append(TB).append(trimPreviousZero(convertEmptyToZero(kintaiVo.getLateOrEarlyTime()))).append(NL);
            }
            // ない場合
            else{
                textStrBuf.append(String.format("%02d",day))
                        .append(TB).append(dayWeek)
                        .append(TB).append(0)
                        .append(TB)
                        .append(TB)
                        .append(TB)
                        .append(TB)
                        .append(TB)
                        .append(TB)
                        .append(TB).append(0)
                        .append(TB).append(NL);
            }
        }

        // 行末付与
        textStrBuf.append("\"").append(NL);

        return textStrBuf.toString();
    }

    /*
     * 空文字を"0"に変換するPrivateMethod
     */
    private String convertEmptyToZero(String str){

        if(str == null){
            return "";
        } else if (str.equals("") || str.equals(" ")) {
            str = "0";
        } else if (str.equals("00:00")){
            return "";
        }

        return str;
    }

    /*
     * 前0を削除するPrivateMethod
     * 例）"00:00" → "0:00"
     */
    private String trimPreviousZero(String str){

        if(str == null || str.length() == 0){
            return "";
        } else if ("0".equals(str.substring(0,1))) {
            str = str.substring(1, str.length());
        }

        return str;
    }

}
