package com.knsw.kintai;

import com.applandeo.materialcalendarview.EventDay;

import java.util.List;

/*
 * CalcUtil
 * Databaseに登録時などの時間計算Util
 */
public class CalcUtil {

    /*
     * 勤務時間計算
     * 計算方法）終了時間 - 開始時間 - 休憩時間
     */
    public String calcWorkTime(String startTime, String endTime, String breakTime) {

        String workTime = CommonConst.DEFAULT_TIME;

        if(!"".equals(endTime)) {
            workTime = convertIntMinuteToStringTime(
                    convertStringTimeToIntMinute(endTime)
                            - convertStringTimeToIntMinute(startTime)
                            - convertStringTimeToIntMinute(breakTime));
        }

        return workTime;
    }

    /*
     * 残業時間計算（普通）
     * 計算方法）勤務時間 - 基準時間(8時間) - 残業時間（深夜）
     */
    public String calcOutOfTimeNormal(String startTime, String endTime, String breakTime) {

        String outOfTimeNormal = CommonConst.DEFAULT_TIME;

        if(!"".equals(endTime)) {
            outOfTimeNormal = convertIntMinuteToStringTime(
                    convertStringTimeToIntMinute(calcWorkTime(startTime, endTime, breakTime))
                            - convertStringTimeToIntMinute(CommonConst.STANDARD_WORK_TIME)
                            - convertStringTimeToIntMinute(calcOutOfTimeMidNight(endTime)));
        }
        return outOfTimeNormal;
    }

    /*
     * 残業時間計算（深夜）
     * 終了時間が22:00を超えている場合のみ行う
     * 計算方法）終了時間 - 22:00
     */
    public String calcOutOfTimeMidNight(String endTime) {

        String outOfTimeMidNight = CommonConst.DEFAULT_TIME;

        if ("".equals(endTime)) {
            return outOfTimeMidNight;
        } else if (Integer.parseInt(endTime.split(":")[0]) < 22) {
            return outOfTimeMidNight;
        }

        outOfTimeMidNight = convertIntMinuteToStringTime(
                convertStringTimeToIntMinute(endTime)
                        - convertStringTimeToIntMinute(CommonConst.STANDARD_MIDNIGHT_TIME));

        return outOfTimeMidNight;
    }

    /*
     * 合計勤務時間、合計残業時間計算
     */
    public String[] calcTotal(List<KintaiValueObject> kintaiVoList) {

        int totalWorkingTime = 0;
        int totalOverTime = 0;

        for (KintaiValueObject kintaiVo : kintaiVoList) {
            if(!"".equals(kintaiVo.getEndTime())) {
                totalWorkingTime += convertStringTimeToIntMinute(kintaiVo.getWorkTime());
                totalOverTime += convertStringTimeToIntMinute(kintaiVo.getOutOfTimeNormal())
                        + convertStringTimeToIntMinute(kintaiVo.getOutOfTimeMidNight());

            }
        }

        String[] retArgs = new String[]{convertIntMinuteToStringTime(totalWorkingTime), convertIntMinuteToStringTime(totalOverTime)};


        return retArgs;
    }

    /*
     * String時間をint分に変換するMethod
     * 例）"10:30" → 630
     */
    private int convertStringTimeToIntMinute(String time) {
        int retTime = 0;

        if ("".equals(time)) {
            return retTime;
        }
        retTime = Integer.parseInt(time.split(":")[0]) * 60 + Integer.parseInt(time.split(":")[1]);

        return retTime;
    }

    /*
     * int分をString時間に変換するMethod
     * 例）630 → "10:30"
     */
    private String convertIntMinuteToStringTime(int minute) {
        String retTime = CommonConst.DEFAULT_TIME;

        if (minute == 0) {
            return retTime;
        }
        retTime = String.format("%02d", (minute / 60)) + ":" + String.format("%02d", (minute % 60));

        return retTime;
    }

}
