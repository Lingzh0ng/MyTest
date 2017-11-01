package com.wearapay.tagprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lyz on 2017/9/26.
 */

public class BallView extends View {

  private int largeRadus = 100;
  private int smallRadus = 10;
  private int mWidth = 300;//view的宽
  private int mHeight = 300;//view的高
  private float perAngle;//将圆均分的角度
  private float restBallStartAngle = 60;//剩下的五个小球的开始分布的角度
  private float phaseAngle;//当两个小球碰撞时他们之间相差的角度

  private Paint mPaint;
  private int mCount = 6;

  private int runningPosition = 0;
  private float runAngle = 0;
  private float runOffsetAngle = 2;

  public BallView(Context context) {
    this(context, null);
  }

  public BallView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    init();
  }

  private void init() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.BLUE);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mWidth = MeasureSpec.getSize(widthMeasureSpec);
    mHeight = MeasureSpec.getSize(heightMeasureSpec);
    int max = Math.max(mHeight, mWidth);
    mWidth = max;
    mHeight = max;
    setMeasuredDimension(mWidth, mHeight);

    largeRadus = (mWidth - getPaddingLeft() - getPaddingRight()) / 2;
    smallRadus = largeRadus / 20;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.save();
    canvas.translate(mWidth / 2, mHeight / 2);
    mPaint.setStyle(Paint.Style.FILL);
    int angle = 0;
    for (int i = 0; i < mCount; i++) {
      canvas.rotate(angle, 0, 0);
      canvas.drawCircle(0, -largeRadus + smallRadus * 2, smallRadus, mPaint);
      canvas.rotate(-angle, 0, 0);
      angle += restBallStartAngle;
    }
    canvas.restore();

    canvas.save();
    canvas.translate(mWidth / 2, mHeight / 2);
    canvas.rotate(runAngle);
    canvas.drawCircle(0, -largeRadus + smallRadus * 2, smallRadus, mPaint);
    canvas.rotate(-runAngle);
    runAngle += runOffsetAngle;
    canvas.restore();

    double atan = Math.atan(0.75);
    double atan1 = Math.tan(30);
    System.out.println(atan);
    System.out.println(atan1);
    System.out.println(atan * Math.PI);
    System.out.println(Math.toDegrees(atan * Math.PI));
    System.out.println(Math.toDegrees(atan));
    System.out.println(Math.toRadians(30));

    //invalidate();
  }
}
