package com.knsw.kintai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // クラス変数
    private SqliteOpenHelper helper = new SqliteOpenHelper(KintaiApp.getAppContext());
    private SQLiteDatabase db = helper.getWritableDatabase();
    private CustomDialog customDialog = new CustomDialog();
    private CalcUtil calcUtil = new CalcUtil();
    private int counter = 0;
    private String overTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* CalendarView */
        List<EventDay> events = new ArrayList<>();
        CalendarView calendarView = findViewById(R.id.calendarView);

        /* CalendarViewの最大・最小月設定 */
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -2);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 2);
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        /* 現在月表示設定 */
        calendarView.showCurrentMonthPage();

        // デフォルトカラー変更
        TextView saturdayLabel = findViewById(R.id.saturdayLabel);
        TextView sundayLabel = findViewById(R.id.sundayLabel);
        saturdayLabel.setTextColor(Color.BLUE);
        sundayLabel.setTextColor(Color.RED);

        // アイコン表示
        initDisplay();
        // 勤務時間・残業時間合計表示
        displayTotal();

        /* calendarView押下時イベント */
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                customDialog.showSettingDialog(MainActivity.this, clickedDayCalendar);
            }
        });

        /* calendarView前ページイベント */
        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                // 勤務時間・残業時間合計表示
                displayTotal();
            }
        });

        /* calendarView次ページイベント */
        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                // 勤務時間・残業時間合計表示
                displayTotal();
            }
        });

        /* workingTime_textview押下時イベント */
        AwesomeTextView workingTime = findViewById(R.id.workingTime_textview);
        workingTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                counter++;
                if(counter >= 10) {
                    Intent intent = new Intent(getApplication(), KintaiTextActivity.class);
                    startActivity(intent);
                }
            }

    });
        /* workingTime_display_textview押下時イベント */
        AwesomeTextView workingTimeisplay = findViewById(R.id.workingTime_display_textview);
        workingTimeisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                counter++;
                if(counter >= 10) {
                    // counterリセット
                    counter = 0;
                    Intent intent = new Intent(getApplication(), KintaiTextActivity.class);
                    startActivity(intent);
                }
            }
        });

        /* RefreshButton押下時イベント */
        BootstrapButton refreshButton = findViewById(R.id.RefreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // アイコン表示
                initDisplay();
                // 勤務時間・残業時間合計表示
                displayTotal();
            }
        });

        /* SettingBotton押下時イベント */
        BootstrapButton settingButton = findViewById(R.id.SettingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SettingActivityへの遷移
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * アイコンの表示を行う
     * SqliteよりSelectし存在すれば、アイコンチェック
     */
    public void initDisplay(){
        // CalendarView
        List<EventDay> events = new ArrayList<>();
        CalendarView calendarView = findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        // 全クリアする
        calendarView.setEvents(events);
        // 月数分ループを行う
        for(int i = -1; i < 4; i++) {
            List<KintaiValueObject> kintaiVoList;
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH) + i);
            try {
                kintaiVoList = helper.readMonthData(helper, db, year, month);
            } catch (SQLiteException e) {
                helper.onCreate(db);
                kintaiVoList = helper.readMonthData(helper, db, year, month);
            }
            if (kintaiVoList != null) {
                // 取得したリスト分アイコン表示を行う
                for (KintaiValueObject kintaiVo : kintaiVoList) {
                    calendar = Calendar.getInstance();
                    calendar.set(Integer.parseInt(kintaiVo.getYear()), Integer.parseInt(kintaiVo.getMonth()) - 1, Integer.parseInt(kintaiVo.getDay()));
                    if(kintaiVo.getDayOff().equals("0")){
                        events.add(new EventDay(calendar, R.drawable.ic_brightness_green_24dp));
                    } else if (kintaiVo.getDayOff().equals("5")){
                        events.add(new EventDay(calendar, R.drawable.ic_brightness_orange_24dp));
                    } else {
                        events.add(new EventDay(calendar, R.drawable.ic_brightness_yellow_24dp));
                    }
                    calendarView.setEvents(events);
                }
            }
        }
    }

    /*
     * 現在表示月の勤務時間合計・残業時間合計の計算を行い、表示する
     */
    public void displayTotal(){
        Calendar calendar = Calendar.getInstance();
        List<KintaiValueObject> kintaiVoList;
        Calendar current = this.<CalendarView>findViewById(R.id.calendarView).getCurrentPageDate();
        String year = String.valueOf(current.get(Calendar.YEAR));
        String month = String.valueOf(current.get(Calendar.MONTH) + 1);
        String totalWorkingTime = CommonConst.DEFAULT_TIME;
        String totalOverTime = CommonConst.DEFAULT_TIME;
        String[] totalTime;
        try {
            kintaiVoList = helper.readMonthData(helper, db, year, month);
        } catch (SQLiteException e) {
            helper.onCreate(db);
            kintaiVoList = helper.readMonthData(helper, db, year, month);
        }
        if (kintaiVoList != null) {
            totalTime = calcUtil.calcTotal(kintaiVoList);
            totalWorkingTime = totalTime[0];
            totalOverTime = totalTime[1];
        }

        // 勤務時間合計表示
        AwesomeTextView workingTime = findViewById(R.id.workingTime_display_textview);
        workingTime.setText(totalWorkingTime);

        // 残業時間合計表示
        AwesomeTextView overTime = findViewById(R.id.overTime_display_textview);
        overTime.setText(totalOverTime);

        // ファイルの準備
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        // データの保存
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("totalOverTime", totalOverTime);
        editor.commit();
    }
}