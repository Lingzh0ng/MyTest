package com.wearapay.tagprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by lyz on 2017/3/17.
 */
public class TagProgressBar1 extends View {

  private Paint pbPaint;

  private Paint bgPaint;

  private Paint textPaint;

  private RectF pbRect;
  private RectF bgRect;
  private RectF textBgRect;

  private float mWidth;
  private float mHeight;
  private float mPgHeight;
  private float mTextBgOffset;

  private boolean isRoundRect = true;

  private float currentProgress;
  private float maxProgress = 100f;//最大进度默认100

  private String progressText;

  private int bgColor = getContext().getResources().getColor(R.color.hui);
  private int[] colors = getContext().getResources().getIntArray(R.array.gradients);
  private float radius;
  private float textHeight;
  private float currentProgressWidth;
  private float textWidth;
  private float c;

  public float getMaxProgress() {
    return maxProgress;
  }

  public void setMaxProgress(float maxProgress) {
    this.maxProgress = maxProgress;
    invalidate();
  }

  public float getCurrentProgress() {
    return currentProgress;
  }

  public void setCurrentProgress(float currentProgress) {
    this.currentProgress = currentProgress;
    invalidate();
  }

  public TagProgressBar1(Context context) {
    this(context, null);
  }

  public TagProgressBar1(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TagProgressBar1(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientProgressBar);
    radius = a.getFloat(R.styleable.GradientProgressBar_pp_round_radius, 0f);
    maxProgress = a.getFloat(R.styleable.GradientProgressBar_pp_max_progress, 100f);
    currentProgress = a.getFloat(R.styleable.GradientProgressBar_pp_current_progress, 0f);
    isRoundRect = a.getBoolean(R.styleable.GradientProgressBar_pp_is_round, true);
    a.recycle();
    init();
  }

  private void init() {
    pbPaint = new Paint();
    pbPaint.setAntiAlias(true);
    pbPaint.setStyle(Paint.Style.FILL);

    bgPaint = new Paint();
    bgPaint.setAntiAlias(true);

    textPaint = new Paint();
    textPaint.setAntiAlias(true);
    textPaint.setColor(Color.WHITE);
    textPaint.setTextSize(sp2px(10));

    mTextBgOffset = dp2px(5);
  }

  @Override protected void onDraw(Canvas canvas) {
    initCurrentRect();
    canvas.save();

    canvas.translate(0, (mHeight - mPgHeight) / 2);

    if (isRoundRect) {
      radius = mPgHeight / 2;
    } else {
      radius = 0;
    }

    bgPaint.setStyle(Paint.Style.FILL);
    bgPaint.setColor(bgColor);
    canvas.drawRoundRect(bgRect, radius, radius, bgPaint);

    if (currentProgress != 0) {

      canvas.drawRoundRect(pbRect, radius, radius, pbPaint);

      canvas.drawRoundRect(textBgRect, -textHeight, -textHeight, pbPaint);

      canvas.drawText(progressText, c  ,
          -textHeight + (mHeight - mPgHeight) / 2, textPaint);
    }
    canvas.restore();
  }

  private void initCurrentRect() {
    float ratio = currentProgress / maxProgress;
    currentProgressWidth = (mWidth) * ratio;
    if (currentProgressWidth > mWidth - getPaddingLeft()) {
      currentProgressWidth = mWidth - getPaddingLeft();
    } else if (currentProgressWidth < getPaddingLeft()) {
      currentProgressWidth = getPaddingLeft();
    }
    //if (currentProgress == 0) {
    //
    //} else {
    //  if (currentProgressWidth > mWidth - getPaddingLeft()) {
    //    currentProgressWidth = mWidth - getPaddingLeft();
    //  } else if (currentProgressWidth < getPaddingLeft()) {
    //    currentProgressWidth = getPaddingLeft() ;
    //  }
    //}

    pbRect = new RectF(getPaddingLeft() , 0, currentProgressWidth, mPgHeight );
    LinearGradient linearGradient =
        new LinearGradient(getPaddingLeft() , 0, currentProgressWidth, mPgHeight , colors,
            null, Shader.TileMode.MIRROR);
    pbPaint.setShader(linearGradient);

    bgRect = new RectF(currentProgressWidth, 0, mWidth  - getPaddingLeft(), mPgHeight );

    progressText = (int) (ratio * 100) + "%";
    //拿到字体的宽度和高度
    textWidth = textPaint.measureText(progressText);
    textHeight = (textPaint.descent() + textPaint.ascent()) / 2;

    float x = currentProgressWidth - mTextBgOffset ;
    float v = textWidth  + currentProgressWidth + mTextBgOffset;
    c = currentProgressWidth;
    if (v > mWidth - dp2px(5)) {
      v =  mWidth - dp2px(5);
      x = v - textWidth - 2 * mTextBgOffset;
      c = x + mTextBgOffset;
    }

    textBgRect = new RectF(x,
        -(mHeight - mPgHeight) / 2 + dp2px(1), v,
        mHeight / 2 + mPgHeight / 2 - dp2px(1));

    System.out.println("currentProgressWidth = "+currentProgressWidth + " , ratio = " + ratio);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) {
      mWidth = widthSize;
    }

    if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.UNSPECIFIED) {
      mHeight = getDensity(getContext()) * 12;
    } else {
      mHeight = heightSize;
    }
    setMeasuredDimension((int) mWidth, (int) mHeight);

    //mWidth = getMeasuredWidth() - getPaddingLeft();
    mPgHeight = mHeight / 2;

    System.out.println("mWidth = "+mWidth);
  }

  /**
   * dp 2 px
   */
  protected int dp2px(int dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
        getResources().getDisplayMetrics());
  }

  /**
   * sp 2 px
   */
  protected int sp2px(int spVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
        getResources().getDisplayMetrics());
  }

  public static float getDensity(final Context destContext) {
    return destContext.getResources().getDisplayMetrics().density;
  }
}
