package com.wearapay.daynight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SetActivity extends AppCompatActivity {

  private SharedPreferences sharedPreferences;
  private Button button;
  private TextView textView;
  private boolean nightMode;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set);

    sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
    nightMode = sharedPreferences.getBoolean("nightMode", false);
    //MODE_NIGHT_NO： 亮色(light)主题，不使用夜间模式
    //MODE_NIGHT_YES：暗色(dark)主题，使用夜间模式
    //MODE_NIGHT_AUTO：根据当前时间自动切换 亮色(light)/暗色(dark)主题（22：00-07：00时间段内自动切换为夜间模式）
    //MODE_NIGHT_FOLLOW_SYSTEM(默认选项)：设置为跟随系统，通常为MODE_NIGHT_NO
    AppCompatDelegate.setDefaultNightMode(
        nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

    button = findViewById(R.id.button);
    textView = findViewById(R.id.textView);
    textView.setText(nightMode ? "夜间" : "白天");

    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        nightMode = sharedPreferences.getBoolean("nightMode", false);
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //  将是否为夜间模式保存到SharedPreferences
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("nightMode",!nightMode);
        edit.apply();
        AppCompatDelegate.setDefaultNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ?
            AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        startActivity(new Intent(SetActivity.this,SetActivity.class));
        //overridePendingTransition(R.anim.animo_alph_close, R.anim.animo_alph_close);
        finish();
      }
    });
  }
}
