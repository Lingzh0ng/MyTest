package com.wearapay.zxingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDayPicker;
import com.aigestudio.wheelpicker.widgets.WheelMonthPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;

public class MainActivity extends AppCompatActivity implements WheelPicker.OnItemSelectedListener {

  private WheelYearPicker yearPicker;
  private WheelMonthPicker monthPicker;
  private WheelDayPicker dayPicker;
  private int currentYear;
  private int currentMonth;
  private int currentDay;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.picker_view);
    yearPicker = (WheelYearPicker) findViewById(R.id.main_wheel_left);
    monthPicker = (WheelMonthPicker) findViewById(R.id.main_wheel_center);
    dayPicker = (WheelDayPicker) findViewById(R.id.main_wheel_right);

    init();
  }

  private void init() {
    yearPicker.setYearStart(1975);
    yearPicker.setYearEnd(2020);
    currentYear = yearPicker.getSelectedYear();
    currentMonth = monthPicker.getSelectedMonth();
    currentDay = dayPicker.getSelectedDay();
    yearPicker.setOnItemSelectedListener(this);
    monthPicker.setOnItemSelectedListener(this);
    dayPicker.setOnItemSelectedListener(this);
  }

  @Override public void onItemSelected(WheelPicker picker, Object data, int position) {
    System.out.println(data);
    currentYear = yearPicker.getSelectedYear();
    currentMonth = monthPicker.getSelectedMonth();
    dayPicker.setYearAndMonth(currentYear, currentMonth);
    currentDay = dayPicker.getSelectedDay();
    if (!(picker instanceof WheelDayPicker)) {
      dayPicker.setSelectedDay(1);
      dayPicker.setSelectedDay(1);
    }
  }
}
