package com.wearapay.constraintdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lyz on 2017/3/28.
 */

public class CView extends View {

  private Paint mPaint;
  private Path mPath;

  public CView(Context context) {
    super(context);
    init();
  }

  public CView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setColor(Color.RED);
    mPath = new Path();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.save();
    mPath.moveTo(100,100);
    //mPath.cubicTo();

  }
}
