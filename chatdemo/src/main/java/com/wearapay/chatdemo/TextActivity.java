package com.wearapay.chatdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by lyz on 2017/1/6.
 */
public class TextActivity extends AppCompatActivity {

  private FlowLayout selectContactsView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_text);
    init();
  }

  private void init() {
    selectContactsView = (FlowLayout) findViewById(R.id.selectContactsView);
    for (int i = 0; i < 10; i++) {
      TextView textView = (TextView) View.inflate(this,R.layout.tv,null);
      textView.setText("1557474741" + i);
      //textView.setGravity(Gravity.CENTER);
      selectContactsView.addView(textView);
    }
    EditText editText = (EditText) View.inflate(this,R.layout.edittext,null);
    selectContactsView.addView(editText);
    selectContactsView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            selectContactsView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            Log.d("11111111111", "-------------"
                + selectContactsView.getChildCount()
                + ""
                + selectContactsView.getMeasuredHeight()
                + "____"
                + selectContactsView.getMeasuredWidth());
          }
        });
    Log.d("11111111111", "-------------"
        + selectContactsView.getChildCount()
        + ""
        + selectContactsView.getMeasuredHeight()
        + "____"
        + selectContactsView.getMeasuredWidth());
  }

  @Override protected void onStart() {
    super.onStart();
    Log.d("11111111111", "-------------"
        + selectContactsView.getChildCount()
        + ""
        + selectContactsView.getMeasuredHeight()
        + "____"
        + selectContactsView.getMeasuredWidth());
  }
}
