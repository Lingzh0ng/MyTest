package com.wearapay.notificationdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

/**
 * Created by lyz54 on 2017/4/24.
 */

public class NotificationManager {

  private final WindowManager windowManager;
  private static NotificationManager manager;
  private View view;
  private float downX;
  private float downY;
  private static Handler handler;
  private static int scaledEdgeSlop;
  private final int h;
  private int i;

  private NotificationManager(Context context) {
    windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    h = context.getResources().getDimensionPixelOffset(R.dimen.h);
  }

  public static NotificationManager getNotificationManager(Context context) {
    if (manager == null) {
      manager = new NotificationManager(context);
      handler = new Handler();
      scaledEdgeSlop = ViewConfiguration.get(context).getScaledEdgeSlop();
    }
    return manager;
  }

  public void showNotification(Context context, String msg) {
    if (view != null) {
      return;
    }
    i = 0;
    view = View.inflate(context, R.layout.notification_item, null);
    view.setOnTouchListener(new View.OnTouchListener() {

      @Override public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            downX = event.getX();
            downY = event.getY();
            break;
          case MotionEvent.ACTION_UP:
            float x = event.getX();
            float y = event.getY();
            float ax = Math.abs(x - downX);
            float ay = Math.abs(y - downY);
            if (ax < scaledEdgeSlop && ay < scaledEdgeSlop) {
              removerView();
            } else if (ax < 800) {
              System.out.println("000000000000");
              removerView();
            }
            break;
        }
        return true;
      }
    });

    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.width = WindowManager.LayoutParams.MATCH_PARENT;
    params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
    params.format = PixelFormat.TRANSLUCENT;
    params.type = WindowManager.LayoutParams.TYPE_TOAST;

    params.gravity = Gravity.TOP + Gravity.LEFT;
    params.x = 0;
    params.y = 0;
    //view.setPadding(0, -h, 0, 0);
    windowManager.addView(view, params);
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(200);
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        //System.out.println("animatedFraction: " + value);
        //view.setPadding(0, (int) -(h - value * h), 0, 0);
        //System.out.println("h: " + value * h);
        view.setTranslationY(-(h - value * h));
      }
    });
    //startAnim();

    valueAnimator.start();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        removerView();
      }
    }, 1500);
  }

  private void startAnim() {
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        if (i * 20 > 200) return;
        view.setPadding(0, (int) -(h - i++ * 20 / 200f * h), 0, 0);
        System.out.println(view.getPaddingTop());
        System.out.println((h - i * 20 / 200f * h));
        startAnim();
      }
    }, 20);
  }

  public void removerView() {
    if (view != null) {
      windowManager.removeViewImmediate(view);
      view = null;
    }
    handler.removeCallbacksAndMessages(null);
  }
}
