package com.wearapay.ypulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lyz on 2017/7/4.
 */
public class YPullRefreshLayout extends FrameLayout {

  private float density;
  private View mHeaderView;
  private int mHeaderViewHeight;
  private int mHeaderViewPadding;
  private int currentPadding;
  private float v;
  private float lastMove;
  private ImageView ivArrow;
  private ImageView ivLoading;
  private TextView tv;

  private float mDownY;

  private float mMinimumFling;

  private int recylerViewPosition;

  private RefreshStatus currentStatus = RefreshStatus.CLOSE;
  private int measuredWidth;
  private View headerView;
  private View bodyView;

  enum RefreshStatus {
    REFRESHING, CLOSE, OPEN, COMPLETE
  }

  private OnRefreshListener onRefreshListener;

  public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
    this.onRefreshListener = onRefreshListener;
  }

  public interface OnRefreshListener {
    void onRefresh();
  }

  private void updateStatus() {
    System.out.println("currentStatus : " + currentStatus);
    if (currentStatus == RefreshStatus.OPEN) {
      if (v > 0 && v <= mHeaderViewHeight) {
        //TODO
        tv.setText("下拉刷新");
        ivArrow.setVisibility(VISIBLE);
        ivLoading.setVisibility(GONE);
      } else if (v > mHeaderViewHeight && v <= mHeaderViewPadding) {
        ivArrow.setVisibility(VISIBLE);
        ivLoading.setVisibility(GONE);
        tv.setText("释放刷新");
      }
      rotateImageView(ivArrow, v);
    } else if (currentStatus == RefreshStatus.CLOSE) {
      v = 0;
      getChildAt(0).setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
      getChildAt(1).setPadding(0, (int) v, 0, 0);
      rotateImageView(ivArrow, v);
    } else if (currentStatus == RefreshStatus.REFRESHING) {
      v = mHeaderViewHeight;
      ivArrow.setVisibility(GONE);
      ivLoading.setVisibility(VISIBLE);
      startLoadingAnimation(ivLoading);
      tv.setText("正在刷新");
      getChildAt(0).setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
      getChildAt(1).setPadding(0, (int) v, 0, 0);
      if (onRefreshListener != null) {
        onRefreshListener.onRefresh();
      }
    } else if (currentStatus == RefreshStatus.COMPLETE) {
      currentStatus = RefreshStatus.CLOSE;
      ivArrow.setVisibility(GONE);
      ivLoading.setVisibility(GONE);
      ivLoading.clearAnimation();
      tv.setText("刷新成功");
      updateStatus();
    }
  }

  public void finishRefresh() {
    if (currentStatus == RefreshStatus.REFRESHING) {
      currentStatus = RefreshStatus.COMPLETE;
      updateStatus();
    }
  }

  private void rotateImageView(ImageView imageView, float spend) {
    imageView.setPivotX(imageView.getWidth() / 2);
    imageView.setPivotY(imageView.getHeight() / 2);
    imageView.setRotation(spend / mHeaderViewPadding * 180);
  }

  private void startLoadingAnimation(ImageView imageView) {
    RotateAnimation loadingAnimation =
        new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f);
    loadingAnimation.setDuration(1000);
    loadingAnimation.setRepeatCount(-1);
    loadingAnimation.setInterpolator(new LinearInterpolator());
    loadingAnimation.setFillAfter(false);
    imageView.setAnimation(loadingAnimation);
    loadingAnimation.start();
  }

  public int getRecylerViewPosition() {
    return recylerViewPosition;
  }

  public void setRecylerViewPosition(int recylerViewPosition) {
    this.recylerViewPosition = recylerViewPosition;
  }

  public YPullRefreshLayout(Context context) {
    this(context, null);
  }

  public YPullRefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public YPullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public float getDensity() {
    return getContext().getResources().getDisplayMetrics().density;
  }

  private void init() {
    density = getDensity();
    mHeaderView = View.inflate(getContext(), R.layout.view_header, null);
    ViewGroup.LayoutParams layoutParams =
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    layoutParams.height = (int) (density * 66);
    mHeaderView.setLayoutParams(layoutParams);
    this.addView(mHeaderView);
    ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
    ivLoading = (ImageView) mHeaderView.findViewById(R.id.iv_loading);
    tv = (TextView) mHeaderView.findViewById(R.id.tv);
    mHeaderViewHeight = layoutParams.height;
    mHeaderViewPadding = (int) (mHeaderViewHeight + density * 66);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    mMinimumFling = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    measuredWidth = getMeasuredWidth();
  }

  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    headerView = getChildAt(0);
    headerView.layout(left, top, right, 0);
    bodyView = getChildAt(1);
    bodyView.layout(left, mHeaderViewHeight, right, bottom);
    headerView.setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
    bodyView.setPadding(0, (int) v, 0, 0);
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      mDownY = ev.getY();
    }
    if (recylerViewPosition == 0 && currentStatus != RefreshStatus.REFRESHING) {

      switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
          mDownY = ev.getY();
          break;
        case MotionEvent.ACTION_MOVE:
          if (mDownY == 0) {
            mDownY = ev.getY();
          }
          v = ev.getY() - mDownY;
          if (v > 0 && v <= mHeaderViewPadding) {
            currentStatus = RefreshStatus.OPEN;
            //if (lastAppBarStatus != AppBarStateChangeListener.State.COLLAPSED) {
            headerView.setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
            bodyView.setPadding(0, (int) v, 0, 0);
            System.out.println("x: " + getChildAt(0).getPaddingTop());
            //} else {
            //  getChildAt(0).setPadding(0, (int) (-mHeaderViewHeight + v + appBarHeight), 0, 0);
            //  getChildAt(1).setPadding(0, (int) v + appBarHeight, 0, 0);
            //}
            System.out.println(v);

            updateStatus();
            return true;
          }
          if (v > mHeaderViewPadding) {
            v = mHeaderViewPadding;
          }
          break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
          mDownY = 0;
          if (v > 0 && v <= mHeaderViewPadding / 2) {
            currentStatus = RefreshStatus.CLOSE;
            updateStatus();
          } else if (v > mHeaderViewPadding / 2 && v <= mHeaderViewPadding) {
            currentStatus = RefreshStatus.REFRESHING;
            updateStatus();
          }
          return super.dispatchTouchEvent(ev);
      }
    }
    if ((ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL)
        && currentStatus == RefreshStatus.OPEN) {
      mDownY = 0;
      if (v > 0 && v <= mHeaderViewPadding / 2) {
        currentStatus = RefreshStatus.CLOSE;
        updateStatus();
      } else if (v > mHeaderViewPadding / 2 && v <= mHeaderViewPadding) {
        currentStatus = RefreshStatus.REFRESHING;
        updateStatus();
      }
    }
    if (currentStatus == RefreshStatus.CLOSE) {
      v = 0;
      headerView.setPadding(0, -mHeaderViewHeight, 0, 0);
      bodyView.setPadding(0, 0, 0, 0);
    }
    return super.dispatchTouchEvent(ev);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    return super.onInterceptTouchEvent(ev);
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    return true;
  }
}
