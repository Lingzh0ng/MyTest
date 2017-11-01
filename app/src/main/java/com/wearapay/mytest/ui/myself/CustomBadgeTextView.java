package com.wearapay.mytest.ui.myself;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.wearapay.mytest.R;

/**
 * Created by lyz on 2016/12/29.
 */
public class CustomBadgeTextView extends TextView {

  private static final int DEFAULT_FILL_TYPE = 0;
  private Paint mPaint;
  private float density;

  private float x_offest = 0;

  private float y_offest = 1.75f;
  private static final float SHADOW_RADIUS = 3.5f;
  private static final int FILL_SHADOW_COLOR = 0x55000000;
  private static final int KEY_SHADOW_COLOR = 0x55000000;

  private int backgroundColor;
  private int borderColor;
  private float borderWidth;
  private float borderAlpha;
  private int ctType;
  private int diffWH;

  public CustomBadgeTextView(Context context) {
    this(context, null);
  }

  public CustomBadgeTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomBadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    mPaint = new Paint();
    density = getContext().getResources().getDisplayMetrics().density;
    x_offest = x_offest * density;
    y_offest = y_offest * density;
    mShadowRadius = (int) (density * SHADOW_RADIUS);
    float textHeight = getTextSize();
    float textWidth = textHeight / 4;
    diffWH = (int) (Math.abs(textHeight - textWidth) / 2);
    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.MaterialBadgeTextView);
    backgroundColor =
        typedArray.getColor(R.styleable.MaterialBadgeTextView_mbtv_backgroundColor, 0xEF4738);
    borderColor =
        typedArray.getColor(R.styleable.MaterialBadgeTextView_mbtv_border_color, Color.TRANSPARENT);
    borderWidth = typedArray.getDimension(R.styleable.MaterialBadgeTextView_mbtv_border_width, 0);
    borderAlpha = typedArray.getFloat(R.styleable.MaterialBadgeTextView_mbtv_border_alpha, 1);
    ctType = typedArray.getInt(R.styleable.MaterialBadgeTextView_mbtv_type, DEFAULT_FILL_TYPE);
    typedArray.recycle();
  }

  private boolean isHighLightMode;

  @Override
  protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter);
    /** 纯色小红点模式下若有文本需要从无变为有, 要归位view的大小.*/
    String strText = text == null ? "" : text.toString().trim();
    if (isHighLightMode && !"".equals(strText)) {
      ViewGroup.LayoutParams lp = getLayoutParams();
      lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
      lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
      setLayoutParams(lp);
      isHighLightMode = false;
    }
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    refreshTextBackground(w, h);
  }

  private void refreshTextBackground(int targetWidth, int targetHeight) {
    if (targetWidth < 0 || targetHeight < 0) {
      return;
    }
    CharSequence text = getText();
    if (text.length() == 1) {
      int max = Math.max(targetWidth, targetHeight);
      ShapeDrawable circle;
      final int diameter = max - (2 * mShadowRadius);
      OvalShape oval = new OvalShadow(mShadowRadius, diameter);
      circle = new ShapeDrawable(oval);
      ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.getPaint());
      circle.getPaint().setShadowLayer(mShadowRadius, x_offest, y_offest, KEY_SHADOW_COLOR);
      circle.getPaint().setColor(backgroundColor);
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
        setBackgroundDrawable(circle);
      } else {
        setBackground(circle);
      }
    } else if (text.length() > 1) {
      MaterialBadgeDrawable badgeDrawable = new MaterialBadgeDrawable();
      ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, badgeDrawable.drawablePaint);
      badgeDrawable.getDrawablePaint()
          .setShadowLayer(mShadowRadius, x_offest, y_offest, KEY_SHADOW_COLOR);
      badgeDrawable.getDrawablePaint().setColor(backgroundColor);
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
        setBackgroundDrawable(badgeDrawable);
      } else {
        setBackground(badgeDrawable);
      }
    } else {

    }
  }

  /**
   *
   * @param isDisplayInToolbarMenu
   */
  public void setHighLightMode(boolean isDisplayInToolbarMenu) {
    isHighLightMode = true;
    ViewGroup.LayoutParams params = getLayoutParams();
    params.width = dp2px(8);
    params.height = params.width;
    if (isDisplayInToolbarMenu && params instanceof FrameLayout.LayoutParams) {
      ((FrameLayout.LayoutParams) params).topMargin = dp2px(8);
      ((FrameLayout.LayoutParams) params).rightMargin = dp2px(8);
    }
    setLayoutParams(params);
    ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
    ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, drawable.getPaint());
    drawable.getPaint().setColor(backgroundColor);
    drawable.getPaint().setAntiAlias(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      setBackground(drawable);
    } else {
      setBackgroundDrawable(drawable);
    }
    setText("");
    setVisibility(View.VISIBLE);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  private int mShadowRadius;

  private class OvalShadow extends OvalShape {
    private RadialGradient mRadialGradient;
    private Paint ovalPaint;
    private int mCircleDiameter;

    public OvalShadow(int shadowRadius, int circleDiameter) {
      ovalPaint = new Paint();
      mShadowRadius = shadowRadius;
      mCircleDiameter = circleDiameter;
      mRadialGradient = new RadialGradient(mCircleDiameter / 2, mCircleDiameter / 2, mShadowRadius,
          new int[] { FILL_SHADOW_COLOR, Color.TRANSPARENT }, null, Shader.TileMode.CLAMP);
      ovalPaint.setShader(mRadialGradient);
    }

    @Override public void draw(Canvas canvas, Paint paint) {
      super.draw(canvas, paint);
      int width = CustomBadgeTextView.this.getWidth();
      int height = CustomBadgeTextView.this.getHeight();
      canvas.drawCircle(width / 2, height / 2, mCircleDiameter / 2 + mShadowRadius, ovalPaint);
      canvas.drawCircle(width / 2, height / 2, mCircleDiameter / 2, paint);
    }
  }

  private class MaterialBadgeDrawable extends Drawable {
    private Paint drawablePaint;
    private RectF rectF;

    public Paint getDrawablePaint() {
      return drawablePaint;
    }

    public MaterialBadgeDrawable() {
      super();
      drawablePaint = new Paint();
      mPaint.setAntiAlias(true);
    }

    @Override public void setBounds(int left, int top, int right, int bottom) {
      super.setBounds(left, top, right, bottom);
      if (rectF == null) {
        rectF = new RectF(left + diffWH, top + mShadowRadius + 4, right - diffWH,
            bottom - mShadowRadius - 4);
      } else {
        rectF.set(left + diffWH, top + mShadowRadius + 4, right - diffWH,
            bottom - mShadowRadius - 4);
      }
    }

    @Override public void draw(Canvas canvas) {
      float R = rectF.bottom * 0.4f;
      if (rectF.right < rectF.bottom) {
        R = rectF.right * 0.4f;
      }
      canvas.drawRoundRect(rectF, R, R, mPaint);
    }

    @Override public void setAlpha(int i) {
      drawablePaint.setAlpha(i);
    }

    @Override public void setColorFilter(ColorFilter colorFilter) {
      drawablePaint.setColorFilter(colorFilter);
    }

    @Override public int getOpacity() {
      return PixelFormat.TRANSPARENT;
    }
  }

  public int dp2px(int dp) {
    try {
      float density = getContext().getResources().getDisplayMetrics().density;
      return (int) (dp * density + 0.5f);
    } catch (Exception e) {
      return (int) (dp + 0.5f);
    }
  }
}
