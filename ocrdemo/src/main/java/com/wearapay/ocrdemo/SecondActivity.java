package com.wearapay.ocrdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by lyz on 2017/9/1.
 */

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

  private Button btn;

  private int current = mode1;

  private static final int mode1 = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
      | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
  private static final int mode2 = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
      | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    btn = (Button) findViewById(R.id.btn);
    btn.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    if (current == mode1) {
      getWindow().setSoftInputMode(mode2);
      current = mode2;
      btn.setText("mode2");
    } else {
      getWindow().setSoftInputMode(mode1);
      current = mode1;
      btn.setText("mode1");
    }
  }
}
