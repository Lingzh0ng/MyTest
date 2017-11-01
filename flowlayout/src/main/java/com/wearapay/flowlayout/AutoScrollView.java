package com.wearapay.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lyz on 2017/8/3.
 */
public class AutoScrollView extends ScrollView {

  private int maxHeight;

  public AutoScrollView(Context context) {
    this(context, null);
  }

  public AutoScrollView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AutoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollView);
    maxHeight = (int) ta.getDimension(R.styleable.AutoScrollView_max_height, 200);
    ta.recycle();
  }

  public int dip2px(float dpValue) {
    final float scale = getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    try {
      heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
    } catch (Exception e) {
      e.printStackTrace();
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
