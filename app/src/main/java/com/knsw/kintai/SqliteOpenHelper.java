package com.knsw.kintai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/*
 * SQLの実行を行うOpenHelper
 */
public class SqliteOpenHelper extends SQLiteOpenHelper {

    /*  データーベースのバージョン　*/
    private static final int DATABASE_VERSION = 1;

    /*  データーベース名　*/
    private static final String DATABASE_NAME = "Sqlite.db";

    /* Create Table文 */
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE kintai (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "year TEXT, " +
                    "month TEXT, " +
                    "day TEXT, " +
                    "dayOfWeek TEXT, " +
                    "dayOff TEXT, " +
                    "startTime TEXT, " +
                    "endTime TEXT, " +
                    "breakTime TEXT, " +
                    "outOfTimeNormal TEXT, " +
                    "outOfTimeMidNight TEXT, " +
                    "workTime TEXT, " +
                    "workContents TEXT, " +
                    "lateOrEarly TEXT, " +
                    "lateOrEarlyTime TEXT);";

    /* Create Index文 */
    private static final String SQL_CREATE_UNIQE_INDEX =
            "CREATE UNIQUE INDEX INDEX1 " +
                    "ON kintai (" +
                    "year, " +
                    "month, " +
                    "day, " +
                    "dayOfWeek);";

    /* Drop Table文（未使用） */
    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS kintai";

    // コンストラクタ
    public SqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成
        // SQLiteファイルがなければSQLiteファイルが作成される
        db.execSQL(SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_UNIQE_INDEX);

        Log.d("debug", "onCreate(SQLiteDatabase db)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別
        db.execSQL(
                SQL_DROP_TABLE
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /*
     * 年月日指定による1件Select
     * 値がなければKintaiValueObjectがnullで返る
     */
    public KintaiValueObject readDayData(SqliteOpenHelper helper, SQLiteDatabase db,String year, String month, String day){

        Log.d("debug","readData");

        Cursor cursor = db.query(
               "kintai",
               null,
               "year=? and month=? and day =?",
                new String[] {year, month, day}, // selectionArgs
               null,
               null,
               null
        );

        cursor.moveToFirst();

        if(cursor.getCount() > 1){
            //　例外処理
            return null;
        }else if(cursor.getCount() == 0){
            //例外処理
            return null;
        }

        String[] args = new String[15];
        for (int i = 0; i < cursor.getCount(); i++) {
            for(int j = 1; j <= 14; j++) {
                args[j] = cursor.getString(j);
            }
            cursor.moveToNext();
        }

        // カーソルクローズ
        cursor.close();

        KintaiValueObject kintaiVo = new KintaiValueObject();
        kintaiVo.setYear(args[1]);
        kintaiVo.setMonth(args[2]);
        kintaiVo.setDay(args[3]);
        kintaiVo.setDayOfWeek(args[4]);
        kintaiVo.setDayOff(args[5]);
        kintaiVo.setStartTime(args[6]);
        kintaiVo.setEndTime(args[7]);
        kintaiVo.setBreakTime(args[8]);
        kintaiVo.setOutOfTimeNormal(args[9]);
        kintaiVo.setOutOfTimeMidNight(args[10]);
        kintaiVo.setWorkTime(args[11]);
        kintaiVo.setWorkContents(args[12]);
        kintaiVo.setLateOrEarly(args[13]);
        kintaiVo.setLateOrEarlyTime(args[14]);

        return kintaiVo;
    }

    /*
     * 年月指定による複数件Select
     * 値がなければList<KintaiValueObject>がnullで返る
     */
    public ArrayList<KintaiValueObject> readMonthData(SqliteOpenHelper helper, SQLiteDatabase db, String year, String month){

        Log.d("debug","readData");

        Cursor cursor = db.query(
                "kintai",
                null,
                "year=? and month=?",
                new String[] {year, month}, // selectionArgs
                null,
                null,
                null
        );

        cursor.moveToFirst();

        if(cursor.getCount() == 0){
            //例外処理
            return null;
        }

        ArrayList<KintaiValueObject> kintaiVoList = new ArrayList<KintaiValueObject>();
        String[] args = new String[15];
        for (int i = 0; i < cursor.getCount(); i++) {
            for(int j = 1; j <= 14; j++) {
                args[j] = cursor.getString(j);
            }
            KintaiValueObject kintaiVo = new KintaiValueObject();
            kintaiVo.setYear(args[1]);
            kintaiVo.setMonth(args[2]);
            kintaiVo.setDay(args[3]);
            kintaiVo.setDayOfWeek(args[4]);
            kintaiVo.setDayOff(args[5]);
            kintaiVo.setStartTime(args[6]);
            kintaiVo.setEndTime(args[7]);
            kintaiVo.setBreakTime(args[8]);
            kintaiVo.setOutOfTimeNormal(args[9]);
            kintaiVo.setOutOfTimeMidNight(args[10]);
            kintaiVo.setWorkTime(args[11]);
            kintaiVo.setWorkContents(args[12]);
            kintaiVo.setLateOrEarly(args[13]);
            kintaiVo.setLateOrEarlyTime(args[14]);
            kintaiVoList.add(kintaiVo);
            args = null;
            args = new String[15];
            cursor.moveToNext();
        }
        // 忘れずに！
        cursor.close();

        Log.d("debug", ""+ cursor.getCount());

        return kintaiVoList;
    }

    /*
     * 引数のValueObjectの登録を行う
     */
    public void insertData(SQLiteDatabase db, KintaiValueObject kintaiVo){

        ContentValues values = new ContentValues();
        values.put("year", kintaiVo.getYear());
        values.put("month", kintaiVo.getMonth());
        values.put("day", kintaiVo.getDay());
        values.put("dayOfWeek", kintaiVo.getDayOfWeek());
        values.put("dayOff", kintaiVo.getDayOff());
        values.put("startTime", kintaiVo.getStartTime());
        values.put("endTime", kintaiVo.getEndTime());
        values.put("breakTime", kintaiVo.getBreakTime());
        values.put("outOfTimeNormal", kintaiVo.getOutOfTimeNormal());
        values.put("outOfTimeMidNight", kintaiVo.getOutOfTimeMidNight());
        values.put("workTime", kintaiVo.getWorkTime());
        values.put("workContents", kintaiVo.getWorkContents());
        values.put("lateOrEarly", kintaiVo.getLateOrEarly());
        values.put("lateOrEarlyTime", kintaiVo.getLateOrEarlyTime());

        db.insert("kintai", null, values);
    }

    /*
     * 引数のValueObjectの更新を行う
     */
    public void updateData(SQLiteDatabase db, KintaiValueObject kintaiVo){

        ContentValues values = new ContentValues();
        values.put("year", kintaiVo.getYear());
        values.put("month", kintaiVo.getMonth());
        values.put("day", kintaiVo.getDay());
        values.put("dayOfWeek", kintaiVo.getDayOfWeek());
        values.put("dayOff", kintaiVo.getDayOff());
        values.put("startTime", kintaiVo.getStartTime());
        values.put("endTime", kintaiVo.getEndTime());
        values.put("breakTime", kintaiVo.getBreakTime());
        values.put("outOfTimeNormal", kintaiVo.getOutOfTimeNormal());
        values.put("outOfTimeMidNight", kintaiVo.getOutOfTimeMidNight());
        values.put("workTime", kintaiVo.getWorkTime());
        values.put("workContents", kintaiVo.getWorkContents());
        values.put("lateOrEarly", kintaiVo.getLateOrEarly());
        values.put("lateOrEarlyTime", kintaiVo.getLateOrEarlyTime());

        db.update("kintai"
                , values
                ,"year = ? and month = ? and day = ?"
                , new String[]{kintaiVo.getYear(), kintaiVo.getMonth(), kintaiVo.getDay()});
    }

    /*
     * 年月日指定の削除を行う
     */
    public void deleteData(SQLiteDatabase db, String year, String month, String day){

        db.delete("kintai"
                ,"year = ? and month = ? and day = ?"
                , new String[]{year,month, day});
    }

}
