package com.wearapay.mytest.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.wearapay.mytest.APP;
import com.wearapay.mytest.util.ToastUtil;

/**
 * Created by lyz on 2016/12/16.
 */
public class TelPhoneView extends View {
  private static final String TAG = "TelPhoneView";
  private final String[] arr = new String[] {
      "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
      "S", "T", "U", "V", "W", "X", "Y", "Z"
  };
  private final int size = arr.length;
  private Paint paint;
  private int downY;
  private int mHeight;
  private int index = -1;
  private int type = -1;

  public TelPhoneView(Context context) {
    this(context, null);
  }

  public TelPhoneView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TelPhoneView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(Color.BLUE);
    paint.setTextSize(30f);
    //setBackgroundColor(0xFFE4E5DA);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //measure(0, 0);
    int measuredHeight = getMeasuredHeight();
    int measuredWidth = getMeasuredWidth();
    Log.d(TAG, "measuredHeight:" + measuredHeight);
    Log.d(TAG, "measuredWidth:" + measuredWidth);
    mHeight = measuredHeight / size;
    for (int i = 0; i < size; i++) {
      paint.setColor(Color.BLUE);
      paint.setTextSize(30f);
      if (index == i) {
        paint.setColor(Color.RED);
        paint.setTextSize(50f);
      }
      if (index == -1 && type == i) {
        paint.setColor(Color.RED);
        paint.setTextSize(50f);
      }
      Rect rect = new Rect();
      paint.getTextBounds(arr[i], 0, arr[i].length(), rect);
      int w = rect.width();
      int h = rect.height();
      Log.d(TAG, "w=" + w + "  h=" + h);
      canvas.drawText(arr[i], (measuredWidth - w) / 2, mHeight * i + h + 20, paint);
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        setBackgroundColor(0x00ffffff);
        type = -1;
        downY = (int) event.getY();
        index = downY / mHeight;
        ToastUtil.show(arr[fixIndex(index)]);
        if (listener != null) {
          listener.onClick(index, arr[fixIndex(index)]);
        }
        invalidate();
        return true;
      case MotionEvent.ACTION_MOVE:
        downY = (int) event.getY();
        index = downY / mHeight;
        ToastUtil.show(arr[fixIndex(index)]);
        if (listener != null) {
          listener.onClick(index, arr[fixIndex(index)]);
        }
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        //setBackgroundColor(0xFFE4E5DA);
        if (listener != null) {
          listener.onUp(index, arr[fixIndex(index)]);
        }
        type = index;
        index = -1;
        invalidate();
        APP.mHandler.postDelayed(new Runnable() {
          @Override public void run() {
            type = -1;
            invalidate();
          }
        }, 300L);
        break;
    }
    return super.onTouchEvent(event);
  }

  private int fixIndex(int index) {
    if (index < 0) {
      index = 0;
    } else if (index > size - 1) {
      index = size - 1;
    }
    return index;
  }

  public interface TelPhoneChangeListener {
    void onClick(int index, String string);

    void onUp(int index, String string);
  }

  private TelPhoneChangeListener listener;

  public void setTelPhoneChangeListener(TelPhoneChangeListener listener) {
    this.listener = listener;
  }
}
