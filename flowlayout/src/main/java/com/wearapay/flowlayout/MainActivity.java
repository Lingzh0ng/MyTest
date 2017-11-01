package com.wearapay.flowlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private FlowLayout flowLayout;
  private List<String> list;
  private AutoScrollView scrollView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
    scrollView = (AutoScrollView) findViewById(R.id.scrollView);
    list = new ArrayList<>();

    flowLayout.setContacts(list);
    //for (int i = 0; i < 100; i++) {
    //  TextView view = new TextView(this);
    //  view.setText("text " + i);
    //  view.setBackgroundResource(R.drawable.bg);
    //  LinearLayout.LayoutParams layoutParams =
    //      new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(25));
    //  layoutParams.leftMargin = 10;
    //  layoutParams.rightMargin = 10;
    //  layoutParams.topMargin = 5;
    //  layoutParams.bottomMargin = 5;
    //  view.setLayoutParams(layoutParams);
    //  flowLayout.addView(view);
    //}
    //EditText editText = new EditText(this);
    ////view.setText("text " + i);
    ////editText.setBackgroundResource(R.drawable.bg);
    //LinearLayout.LayoutParams layoutParams =
    //    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    //layoutParams.leftMargin = 10;
    //layoutParams.rightMargin = 10;
    ////layoutParams.topMargin = 5;
    ////layoutParams.bottomMargin = 5;
    //editText.setLayoutParams(layoutParams);
    ////editText.setCursorVisible(false);
    //editText.setBackground(null);
    //editText.setTextSize(14);
    //flowLayout.addView(editText);
    //scrollToBottom();
    Button byId = (Button) findViewById(R.id.btn);
    byId.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        for (int i = 0; i < 10; i++) {
          list.add("测试" + i);
        }
        flowLayout.setContacts(list);
        new Handler().postDelayed(new Runnable() {
          @Override public void run() {
            scrollView.fullScroll(View.FOCUS_DOWN);
          }
        }, 100);
      }
    });
  }

  public int dip2px(float dpValue) {
    final float scale = getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static void scrollToBottom(final View scroll, final View inner) {

    Handler mHandler = new Handler();

    mHandler.post(new Runnable() {
      public void run() {
        if (scroll == null || inner == null) {
          return;
        }
        int offset = inner.getMeasuredHeight() - scroll.getHeight();
        if (offset < 0) {
          offset = 0;
        }

        scroll.scrollTo(0, offset);
      }
    });
  }
}
