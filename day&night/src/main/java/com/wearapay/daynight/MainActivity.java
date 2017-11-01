package com.wearapay.daynight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  private SharedPreferences sharedPreferences;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
    boolean nightMode = sharedPreferences.getBoolean("nightMode", false);
    //MODE_NIGHT_NO： 亮色(light)主题，不使用夜间模式
    //MODE_NIGHT_YES：暗色(dark)主题，使用夜间模式
    //MODE_NIGHT_AUTO：根据当前时间自动切换 亮色(light)/暗色(dark)主题（22：00-07：00时间段内自动切换为夜间模式）
    //MODE_NIGHT_FOLLOW_SYSTEM(默认选项)：设置为跟随系统，通常为MODE_NIGHT_NO
    AppCompatDelegate.setDefaultNightMode(
        nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

    TextView textView = findViewById(R.id.textView);
    textView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, SetActivity.class));
      }
    });
  }
}
