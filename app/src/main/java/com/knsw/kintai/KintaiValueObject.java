package com.knsw.kintai;

/*
 * ValueObject
 * 引数や戻り値が多い場合に用いる
 */
public class KintaiValueObject {

    /* 年 */
    private String year = "";

    /* 月 */
    private String month = "";

    /* 日 */
    private String day = "";

    /* 曜日 */
    private String dayOfWeek = "";

    /* 休暇区分 */
    private String dayOff = "";

    /* 開始時間 */
    private String startTime = "";

    /* 終了時間 */
    private String endTime = "";

    /* 休憩時間 */
    private String breakTime = "";

    /* 時間外（普通） */
    private String outOfTimeNormal = "";

    /* 時間外（深夜） */
    private String outOfTimeMidNight = "";

    /* 勤務時間 */
    private String workTime = "";

    /* 作業内容 */
    private String workContents = "";

    /* 控除（早退または遅刻） */
    private String lateOrEarly = "";

    /* 控除時間 */
    private String lateOrEarlyTime = "";

    /*
     * 自動生成されたgetter、setter
     */
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOff() {
        return dayOff;
    }

    public void setDayOff(String dayOff) {
        this.dayOff = dayOff;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getOutOfTimeNormal() {
        return outOfTimeNormal;
    }

    public void setOutOfTimeNormal(String outOfTimeNormal) {
        this.outOfTimeNormal = outOfTimeNormal;
    }

    public String getOutOfTimeMidNight() {
        return outOfTimeMidNight;
    }

    public void setOutOfTimeMidNight(String outOfTimeMidNight) {
        this.outOfTimeMidNight = outOfTimeMidNight;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkContents() {
        return workContents;
    }

    public void setWorkContents(String workContents) {
        this.workContents = workContents;
    }

    public String getLateOrEarly() {
        return lateOrEarly;
    }

    public void setLateOrEarly(String lateOrEarly) {
        this.lateOrEarly = lateOrEarly;
    }

    public String getLateOrEarlyTime() {
        return lateOrEarlyTime;
    }

    public void setLateOrEarlyTime(String lateOrEarlyTime) {
        this.lateOrEarlyTime = lateOrEarlyTime;
    }

}
