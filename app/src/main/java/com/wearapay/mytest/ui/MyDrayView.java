package com.wearapay.mytest.ui;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by lyz on 2016/12/15.
 */
public class MyDrayView extends LinearLayout {

  private ViewDragHelper dragHelper;
  private int initX;
  private int initY;
  private int width;
  private int height;

  public MyDrayView(Context context) {
    this(context, null);
  }

  public MyDrayView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MyDrayView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    dragHelper = ViewDragHelper.create(this, 1.0f, callback);
    init(context);
  }

  private void init(Context context) {
    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

    width = wm.getDefaultDisplay().getWidth();
    height = wm.getDefaultDisplay().getHeight() - getStatusBarHeight();
    Log.d("DragLayout", "tryCaptureView "
        + width
        + ","
        + height
        + ","
        + getStatusBarHeight()
        + ","
        + getStatusHeight(context));
  }

  public int getStatusBarHeight() {
    int result = 0;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static int getStatusHeight(Context context) {

    int statusHeight = -1;
    try {
      Class clazz = Class.forName("com.android.internal.R$dimen");
      Object object = clazz.newInstance();
      int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
      statusHeight = context.getResources().getDimensionPixelSize(height);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return statusHeight;
  }

  ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
    @Override public boolean tryCaptureView(View child, int pointerId) {
      initX = (int) child.getX();
      initY = (int) child.getY();
      Log.d("DragLayout",
          "tryCaptureView " + initX + "," + initY + "," + MyDrayView.this.getMeasuredHeight());
      return true;
    }

    @Override public void onViewDragStateChanged(int state) {
      super.onViewDragStateChanged(state);
    }

    @Override public void onViewCaptured(View capturedChild, int activePointerId) {
      super.onViewCaptured(capturedChild, activePointerId);
    }

    @Override
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

    }

    @Override public void onViewReleased(View releasedChild, float xvel, float yvel) {
      super.onViewReleased(releasedChild, xvel, yvel);
      Log.d("DragLayout", "onViewReleased " + xvel + "," + yvel);
      releasedChild.layout(initX, initY, initX + releasedChild.getMeasuredWidth(),
          initY + releasedChild.getMeasuredHeight());
    }

    @Override public int getViewHorizontalDragRange(View child) {
      Log.d("DragLayout", "getViewHorizontalDragRange " + child.getX() + "," + child.getY());
      return 100;
    }

    @Override public int getViewVerticalDragRange(View child) {
      return 100;
    }

    @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
      Log.d("DragLayout", "getViewHorizontalDragRange " + left + "," + dx);
      int x = (int) child.getX() + dx;
      if (x > width) {
        x = width;
      }
      if (x <= 0) {
        x = 0;
      }
      return x;
    }

    @Override public int clampViewPositionVertical(View child, int top, int dy) {
      Log.d("DragLayout", "clampViewPositionVertical " + top + "," + dy);
      int y = (int) child.getY() + dy;
      if (y > MyDrayView.this.getMeasuredHeight() - child.getHeight()) {
        y = (int) (MyDrayView.this.getMeasuredHeight() - child.getHeight());
      }
      if (y <= 0) {
        y = 0;
      }
      return y;
    }
  };

  @Override public boolean onInterceptTouchEvent(MotionEvent event) {
    return dragHelper.shouldInterceptTouchEvent(event);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    dragHelper.processTouchEvent(event);
    return true;
  }
}
