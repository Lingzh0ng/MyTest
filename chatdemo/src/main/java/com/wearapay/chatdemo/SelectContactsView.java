package com.wearapay.chatdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/1/6.
 */
public class SelectContactsView extends ViewGroup {

  private int mWidth;
  private int mHeight;
  private int noPadingWidth;
  private int mWidthSpace = 0;
  private int mHeightSpace = 0;

  private List<Line> lineArrayList = new ArrayList<>();

  public void setmWidthSpace(int mWidthSpace) {
    this.mWidthSpace = mWidthSpace;
  }

  public void setmHeightSpace(int mHeightSpace) {
    this.mHeightSpace = mHeightSpace;
  }

  public SelectContactsView(Context context) {
    super(context);
  }

  public SelectContactsView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SelectContactsView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();
    int top = getPaddingTop();

    for (int i = 0; i < lineArrayList.size(); i++) {
      Line line = lineArrayList.get(i);
      int blank = noPadingWidth - line.getWidth();
      if (i > 0) {
        top += line.getHeight() + mHeightSpace;
      }
      ArrayList<View> views = (ArrayList<View>) line.getViews();
      int eLiubai = blank / views.size();
      for (int j = 0; j < views.size(); j++) {

        View view = views.get(j);
        int measureSpec =
            MeasureSpec.makeMeasureSpec(view.getMeasuredWidth() + eLiubai, MeasureSpec.EXACTLY);
        view.measure(measureSpec, 0);
        if (j == 0) {
          view.layout(paddingLeft, top, paddingLeft + view.getMeasuredWidth(),
              top + line.getHeight());
        } else {
          int left = views.get(j - 1).getRight() + mWidthSpace;
          System.out.println("left:" + left);
          System.out.println("r:" + (paddingLeft + left + view.getMeasuredWidth()));
          view.layout(left, top, left + view.getMeasuredWidth(), top + line.getHeight());
        }
        // int left = views.get(j).getMeasuredWidth() + mWidthSpace;
        // views.get(j).layout(paddingLeft + left, top,paddingLeft + left+views.get(j).getMeasuredWidth(), top + line.getHeight());
      }
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    mWidth = MeasureSpec.getSize(widthMeasureSpec);
    //mWidth = MeasureSpec.getSize(widthMeasureSpec);
    // mHeight = MeasureSpec.getSize(heightMeasureSpec);
    noPadingWidth = mWidth - getPaddingLeft() - getPaddingRight();
    System.out.println("noPadingWidth:" + noPadingWidth);
    int count = getChildCount();
    Line line = new Line();
    for (int i = 0; i < count; i++) {
      View childAt = getChildAt(i);
      childAt.measure(0, 0);
      if (line.getViews().size() == 0) {
        line.addViewToLine(childAt);
      } else if (childAt.getMeasuredWidth() + line.getWidth() + mWidthSpace < noPadingWidth) {
        line.addViewToLine(childAt);
      } else {
        lineArrayList.add(line);
        line = new Line();
        line.addViewToLine(childAt);
      }
      if (i == count - 1) {
        lineArrayList.add(line);
      }
    }
    for (int i = 0; i < lineArrayList.size(); i++) {
      mHeight += lineArrayList.get(i).getHeight() + mHeightSpace;
    }
    mHeight = mHeight - mHeightSpace;
    System.out.println("mHeight:" + mHeight);
    heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //设置当前控件的宽高，或者向父VIew申请宽高
    //setMeasuredDimension(mWidth, mHeight);
  }

  class Line {

    private List<View> views;

    private int width;

    private int height;

    public Line() {
      views = new ArrayList<>();
    }

    public int addViewToLine(View view) {
      if (!views.contains(view)) {
        views.add(view);
        if (views.size() == 1) {
          width = view.getMeasuredWidth();
        } else {
          width += view.getMeasuredWidth() + mWidthSpace;
        }
      }
      height = Math.max(height, view.getMeasuredHeight());
      return width;
    }

    public int getHeight() {
      return height;
    }

    public int getWidth() {
      return width;
    }

    public List<View> getViews() {
      return views;
    }
  }
}
