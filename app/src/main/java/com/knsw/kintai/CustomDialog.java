package com.knsw.kintai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.beardedhen.androidbootstrap.BootstrapButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CustomDialog extends AppCompatActivity {

    // メンバ変数
    private String startTime = "";
    private String endTime = "";
    private String breakTime = "";
    private String lateOrEarlyTime = "";
    private String outOfTimeNormal = "";
    private String outOfTimeMidNight = "";
    private String workTime = "";

    private SqliteOpenHelper helper = new SqliteOpenHelper(KintaiApp.getAppContext());
    private SQLiteDatabase db = helper.getWritableDatabase();
    private CalcUtil calcUtil = new CalcUtil();
    private MainActivity mainActivity = null;

    /**
     * コンストラクタ
     */
    public CustomDialog(MainActivity main) {
        this.mainActivity = main;
    }

    public CustomDialog() {}

    /* 設定ダイアログを表示 */
    public void showSettingDialog(Activity activity, Calendar calendar) {
        //コンテキストからインフレータを取得
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_setting, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);
        builder.setTitle(new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime()));

        // 使用するViewの一覧
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        EditText startTimeEditText = view.findViewById(R.id.startTime_edittext);
        EditText endTimeEditText = view.findViewById(R.id.endTime_edittext);
        EditText workContentEditText = view.findViewById(R.id.workContent_edittext);
        EditText breakTimeEditText = view.findViewById(R.id.breakTime_edittext);
        Spinner dayOffSpinner = view.findViewById(R.id.dayOffListSpinner);
        Spinner lateOrEarlySpinner = view.findViewById(R.id.lateOrEarlySpinner);
        EditText lateOrEarlyTimeEdittext = view.findViewById(R.id.lateOrEarlyTime_edittext);

        KintaiValueObject kintaiValueObject;

        try {
            kintaiValueObject = helper.readDayData(helper, db, year, month, day);
        } catch (SQLiteException e) {
            helper.onCreate(db);
            kintaiValueObject = helper.readDayData(helper, db, year, month, day);
        }

        // データが存在していなかったらSharedPreferencesから設定
        if (kintaiValueObject == null) {
            // SharedPreferences ファイルの準備
            SharedPreferences preferences = KintaiApp.getSharedPreferences();
            // workContentの読み込み
            String startTime = preferences.getString("startTime", "");
            String endTime = preferences.getString("endTime", "");
            String breakTime = preferences.getString("breakTime", "");
            String workContent = preferences.getString("workContent", "");
            // EditTextにSet
            startTimeEditText.setText(startTime);
            endTimeEditText.setText(endTime);
            breakTimeEditText.setText(breakTime);
            workContentEditText.setText(workContent);
        }
        // 存在してればデータから読み出し
        else {
            startTimeEditText.setText(kintaiValueObject.getStartTime());
            endTimeEditText.setText(kintaiValueObject.getEndTime());
            breakTimeEditText.setText(kintaiValueObject.getBreakTime());
            workContentEditText.setText(kintaiValueObject.getWorkContents());
            dayOffSpinner.setSelection(Integer.parseInt(kintaiValueObject.getDayOff()));
            lateOrEarlySpinner.setSelection(CommonConst.MAP_LATE_OR_EARLY.get(kintaiValueObject.getLateOrEarly()));
            lateOrEarlyTimeEdittext.setText(kintaiValueObject.getLateOrEarlyTime());
        }

        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
                startTimeEditText.setTextIsSelectable(true);
                showTimePicker(activity, startTimeEditText, null, null, null);
            }
        });

        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimeEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
                endTimeEditText.setTextIsSelectable(true);
                showTimePicker(activity, null, endTimeEditText, null, null);
            }
        });

        breakTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakTimeEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
                breakTimeEditText.setTextIsSelectable(true);
                showTimePicker(activity, null, null, breakTimeEditText, null);
            }
        });

        dayOffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();
                if(!item.equals(" ")) {
                    startTimeEditText.setText("");
                    endTimeEditText.setText("");
                    breakTimeEditText.setText("");
                    workContentEditText.setText("");
                }
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        lateOrEarlyTimeEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lateOrEarlyTimeEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
                lateOrEarlyTimeEdittext.setTextIsSelectable(true);
                showTimePicker(activity, null, null, null, lateOrEarlyTimeEdittext);
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // OKクリック時の処理
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                String day = String.valueOf(calendar.get(Calendar.DATE));
                String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
                String startTime = startTimeEditText.getText().toString();
                String endTime = endTimeEditText.getText().toString();
                String breakTime = breakTimeEditText.getText().toString();
                String workContents = workContentEditText.getText().toString();
                String dayOff = CommonConst.MAP_DAY_OFF.get(dayOffSpinner.getSelectedItem()).toString();
                String lateOrEarly = lateOrEarlySpinner.getSelectedItem().toString();
                String lateOrEarlyTime = lateOrEarlyTimeEdittext.getText().toString();

                // 登録用計算処理
                outOfTimeNormal = calcUtil.calcOutOfTimeNormal(startTime, endTime, breakTime);
                outOfTimeMidNight = calcUtil.calcOutOfTimeMidNight(endTime);
                workTime = calcUtil.calcWorkTime(startTime, endTime, breakTime);

                KintaiValueObject kintaiVo = new KintaiValueObject();
                kintaiVo.setYear(year);
                kintaiVo.setMonth(month);
                kintaiVo.setDay(day);
                kintaiVo.setDayOfWeek(week);
                kintaiVo.setDayOff(dayOff);
                kintaiVo.setStartTime(startTime);
                kintaiVo.setEndTime(endTime);
                kintaiVo.setBreakTime(breakTime);
                kintaiVo.setOutOfTimeNormal(outOfTimeNormal);
                kintaiVo.setOutOfTimeMidNight(outOfTimeMidNight);
                kintaiVo.setWorkTime(workTime);
                kintaiVo.setWorkContents(workContents);
                kintaiVo.setLateOrEarly(lateOrEarly);
                kintaiVo.setLateOrEarlyTime(lateOrEarlyTime);

                // 比較用 データの読み直し
                KintaiValueObject kintaiVoCompare = helper.readDayData(helper, db, year, month, day);
                // データが存在していなかったらInsert
                if (kintaiVoCompare == null) {
                    helper.insertData(db, kintaiVo);
                }
                // データが存在したらUpdate
                else {
                    helper.updateData(db, kintaiVo);
                }
                // mainActivityに反映
                if(mainActivity != null) {
                    mainActivity.initDisplay();
                    mainActivity.displayTotal();
                }
            }
        });
        builder.setNeutralButton("削除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Deleteクリック時の処理
                helper.deleteData(db, year, month, day);
                // mainActivityに反映
                if(mainActivity != null) {
                    mainActivity.initDisplay();
                    mainActivity.displayTotal();
                }
            }
        });
        builder.setNegativeButton("キャンセル", null);
        builder.setView(view);
        builder.show();
    }

    /* 数値(整数)の入力ダイアログを表示 */
    public void showTimePicker(Activity activity,
                               EditText startTime_edittext,
                               EditText endTime_edittext,
                               EditText breakTime_edittext,
                               EditText lateOrEarlyTime_edittext) {
        //コンテキストからインフレータを取得
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_time_picker, null, false);

        if (startTime_edittext != null) {
            startTime = startTime_edittext.getText().toString();
        } else if (endTime_edittext != null) {
            endTime = endTime_edittext.getText().toString();
        } else if (breakTime_edittext != null) {
            breakTime = breakTime_edittext.getText().toString();
        } else if (lateOrEarlyTime_edittext != null) {
            lateOrEarlyTime = lateOrEarlyTime_edittext.getText().toString();
        }

        NumberPicker timeHour = view.findViewById(R.id.timeHour);

        // 時の設定
        if (startTime_edittext != null || endTime_edittext != null) {
            timeHour.setMaxValue(24);
            timeHour.setMinValue(1);
            timeHour.setDisplayedValues(CommonConst.argHour);
            timeHour.setValue(CommonConst.MAP_HOUR.get(getStartTimeHour()));

            if (startTime_edittext != null) {
                timeHour.setValue(CommonConst.MAP_HOUR.get(getStartTimeHour()));
            } else if (endTime_edittext != null) {
                timeHour.setValue(CommonConst.MAP_HOUR.get(getEndTimeHour()));
            }

        } else if (breakTime_edittext != null || lateOrEarlyTime_edittext != null) {

            timeHour.setMaxValue(8);
            timeHour.setMinValue(0);
            timeHour.setDisplayedValues(CommonConst.argHourSingle);

            if (breakTime_edittext != null) {
                timeHour.setValue(CommonConst.MAP_HOUR_SINGLE.get(getBreakTimeHour()));
            } else if (lateOrEarlyTime_edittext != null) {
                timeHour.setValue(CommonConst.MAP_HOUR_SINGLE.get(getBreakTimeHour()));
            }
        }

        // 分の設定
        NumberPicker timeMinute = view.findViewById(R.id.timeMinute);
        timeMinute.setMaxValue(12);
        timeMinute.setMinValue(1);
        timeMinute.setDisplayedValues(CommonConst.argMinute);

        if (startTime_edittext != null) {
            timeMinute.setValue(CommonConst.MAP_MINUTE.get(getStartTimeMinute()));
        } else if (endTime_edittext != null) {
            timeMinute.setValue(CommonConst.MAP_MINUTE.get(getEndTimeMinute()));
        } else if (breakTime_edittext != null) {
            timeMinute.setValue(CommonConst.MAP_MINUTE.get(getLateOrEarlyTimeMinute()));
        } else if (lateOrEarlyTime_edittext != null) {
            timeMinute.setValue(CommonConst.MAP_MINUTE.get(getLateOrEarlyTimeMinute()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);

        // タイトルの設定
        if (startTime_edittext != null) {
            builder.setTitle("開始時間");//"StartTime"
        } else if (endTime_edittext != null) {
            builder.setTitle("終了時間");//"EndTime"
        } else if (breakTime_edittext != null) {
            builder.setTitle("休憩時間");//"BreakTime"
        } else if (lateOrEarlyTime_edittext != null) {
            builder.setTitle("控除時間");//"LateOrEarlyTime"
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // OKクリック時の処理

                // 時間をメンバ変数へ設定
                // 表示の反映
                if (startTime_edittext != null) {
                    setStartTime(CommonConst.argHour[timeHour.getValue() - 1] + ":" + CommonConst.argMinute[timeMinute.getValue() - 1]);
                    startTime_edittext.setText(getStartTime());
                } else if (endTime_edittext != null) {
                    setEndTime(CommonConst.argHour[timeHour.getValue() - 1] + ":" + CommonConst.argMinute[timeMinute.getValue() - 1]);
                    endTime_edittext.setText(getEndTime());
                } else if (breakTime_edittext != null) {
                    setBreakTime(CommonConst.argHourSingle[timeHour.getValue()] + ":" + CommonConst.argMinute[timeMinute.getValue() - 1]);
                    breakTime_edittext.setText(getBreakTime());
                } else if (lateOrEarlyTime_edittext != null) {
                    setLateOrEarlyTime(CommonConst.argHourSingle[timeHour.getValue()] + ":" + CommonConst.argMinute[timeMinute.getValue() - 1]);
                    lateOrEarlyTime_edittext.setText(getLateOrEarlyTime());
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setView(view);
        builder.show();
    }

    // getter・setter類
    public String getStartTimeHour() {

        if ("".equals(this.startTime)) {
            this.startTime = CommonConst.DEFAULT_START_TIME;
        }

        String startTimeHour = this.startTime.split(":")[0];
        return startTimeHour;
    }

    public String getStartTimeMinute() {
        if ("".equals(this.startTime)) {
            this.startTime = CommonConst.DEFAULT_START_TIME;
        }

        String startTimeMinute = this.startTime.split(":")[1];
        return startTimeMinute;
    }

    public String getEndTimeHour() {
        if ("".equals(this.endTime)) {
            this.endTime = CommonConst.DEFAULT_END_TIME;
        }

        String endTimeHour = this.endTime.split(":")[0];
        return endTimeHour;
    }

    public String getEndTimeMinute() {
        if ("".equals(this.endTime)) {
            this.endTime = CommonConst.DEFAULT_END_TIME;
        }

        String endTimeMinute = this.endTime.split(":")[1];
        return endTimeMinute;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBreakTimeHour() {

        if ("".equals(this.breakTime)) {
            this.breakTime = CommonConst.DEFAULT_SINGLE_TIME;
        }

        String breakTimeHour = this.breakTime.split(":")[0];
        return breakTimeHour;
    }

    public String getBreakTimeMinute() {
        if ("".equals(this.breakTime)) {
            this.breakTime = CommonConst.DEFAULT_SINGLE_TIME;
        }

        String breakTimeMinute = this.breakTime.split(":")[1];
        return breakTimeMinute;
    }

    public String getLateOrEarlyTimeHour() {
        if ("".equals(this.lateOrEarlyTime)) {
            this.lateOrEarlyTime = CommonConst.DEFAULT_SINGLE_TIME;
        }

        String lateOrEarlyTimeHour = this.lateOrEarlyTime.split(":")[0];
        return lateOrEarlyTimeHour;
    }

    public String getLateOrEarlyTimeMinute() {
        if ("".equals(this.lateOrEarlyTime)) {
            this.lateOrEarlyTime = CommonConst.DEFAULT_SINGLE_TIME;
        }

        String lateOrEarlyTimeMinute = this.lateOrEarlyTime.split(":")[1];
        return lateOrEarlyTimeMinute;
    }

    public String getBreakTime() {
        return this.breakTime;
    }

    public String getLateOrEarlyTime() {
        return this.lateOrEarlyTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public void setLateOrEarlyTime(String lateOrEarlyTime) {
        this.lateOrEarlyTime = lateOrEarlyTime;
    }
}