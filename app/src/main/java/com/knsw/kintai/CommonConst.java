package com.knsw.kintai;

import java.util.HashMap;
import java.util.Map;
/*
 * CommonConst
 * 何でもかんでもConst化は、NG
 * 可読性重視
 */
public class CommonConst {

    /* 時間のNumberPicker用のConst */

    /* 時配列 */
    public static final String[] argHour =
            new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                    "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    /* 時配列 */
    public static final String[] argHourSingle =
            new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08"};


    /* 分配列 */
    public static final String[] argMinute =  new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

    /* 時Map */
    public static final Map<String, Integer> MAP_HOUR = new HashMap<>();
    static {
        MAP_HOUR.put("01", 1);
        MAP_HOUR.put("02", 2);
        MAP_HOUR.put("03", 3);
        MAP_HOUR.put("04", 4);
        MAP_HOUR.put("05", 5);
        MAP_HOUR.put("06", 6);
        MAP_HOUR.put("07", 7);
        MAP_HOUR.put("08", 8);
        MAP_HOUR.put("09", 9);
        MAP_HOUR.put("10", 10);
        MAP_HOUR.put("11", 11);
        MAP_HOUR.put("12", 12);
        MAP_HOUR.put("13", 13);
        MAP_HOUR.put("14", 14);
        MAP_HOUR.put("15", 15);
        MAP_HOUR.put("16", 16);
        MAP_HOUR.put("17", 17);
        MAP_HOUR.put("18", 18);
        MAP_HOUR.put("19", 19);
        MAP_HOUR.put("20", 20);
        MAP_HOUR.put("21", 21);
        MAP_HOUR.put("22", 22);
        MAP_HOUR.put("23", 23);
        MAP_HOUR.put("24", 24);
    }

    /* 時Map */
    public static final Map<String, Integer> MAP_HOUR_SINGLE = new HashMap<>();
    static {
        MAP_HOUR_SINGLE.put("00", 0);
        MAP_HOUR_SINGLE.put("01", 1);
        MAP_HOUR_SINGLE.put("02", 2);
        MAP_HOUR_SINGLE.put("03", 3);
        MAP_HOUR_SINGLE.put("04", 4);
        MAP_HOUR_SINGLE.put("05", 5);
        MAP_HOUR_SINGLE.put("06", 6);
        MAP_HOUR_SINGLE.put("07", 7);
        MAP_HOUR_SINGLE.put("08", 8);
    }

    /* 分Map */
    public static final Map<String, Integer> MAP_MINUTE = new HashMap<>();
    static {
        MAP_MINUTE.put("00", 1);
        MAP_MINUTE.put("05", 2);
        MAP_MINUTE.put("10", 3);
        MAP_MINUTE.put("15", 4);
        MAP_MINUTE.put("20", 5);
        MAP_MINUTE.put("25", 6);
        MAP_MINUTE.put("30", 7);
        MAP_MINUTE.put("35", 8);
        MAP_MINUTE.put("40", 9);
        MAP_MINUTE.put("45", 10);
        MAP_MINUTE.put("50", 11);
        MAP_MINUTE.put("55", 12);
    }

    /* 休暇Map */
    public static final Map<String, Integer> MAP_DAY_OFF = new HashMap<>();
    static {
        MAP_DAY_OFF.put(" ", 0);
        MAP_DAY_OFF.put("特休", 1);
        MAP_DAY_OFF.put("振休", 2);
        MAP_DAY_OFF.put("欠勤", 3);
        MAP_DAY_OFF.put("有休", 4);
        MAP_DAY_OFF.put("祝日", 5);
    }

    /* 遅刻・早退Map */
    public static final Map<String, Integer> MAP_LATE_OR_EARLY = new HashMap<>();
    static {
        MAP_LATE_OR_EARLY.put(" ", 0);
        MAP_LATE_OR_EARLY.put("遅刻", 1);
        MAP_LATE_OR_EARLY.put("早退", 2);
    }

    /* 月Map */
    @Deprecated
    public static final Map<String, String> MAP_MONTH = new HashMap<>();
    static {
        MAP_MONTH.put("January", "1");
        MAP_MONTH.put("February", "2");
        MAP_MONTH.put("March", "3");
        MAP_MONTH.put("April", "4");
        MAP_MONTH.put("May", "5");
        MAP_MONTH.put("June", "6");
        MAP_MONTH.put("July", "7");
        MAP_MONTH.put("August", "8");
        MAP_MONTH.put("September", "9");
        MAP_MONTH.put("October", "10");
        MAP_MONTH.put("November", "11");
        MAP_MONTH.put("December", "12");
    }

    /* Defalut Start Time Set */
    public static final String DEFAULT_START_TIME =  "09:00";

    /* Defalut Start Time Set */
    public static final String DEFAULT_END_TIME =  "18:00";

    /* Defalut Single Time Set */
    public static final String DEFAULT_SINGLE_TIME =  "00:00";

    /* Defalut Time Set */
    public static final String DEFAULT_TIME =  "00:00";

    /* Standard Midnight Time Set */
    public static final String STANDARD_MIDNIGHT_TIME =  "22:00";

    /* Standard WorkTime Set */
    public static final String STANDARD_WORK_TIME =  "08:00";

    /* KNSWのFTP SERVER IPアドレス(TODO:定期的に変わる) */
    public static final String KNSW_FTP_SERVER =  "164.46.57.28";

    /* FTP Directoryパス */
    public static final String REMOTE_DIRCTORY =  "";

    /* FTP Directoryパス（部内動作確認用） */
    public static final String REMOTE_DIRCTORY_KAIHATSU =  "";

    /* 改行文字 */
    public static final String NL =  "\n";

    /* タブ */
    public static final String TB =  "\t";
}
