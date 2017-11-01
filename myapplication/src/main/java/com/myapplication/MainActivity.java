package com.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  // 弹出的pop窗口
  private PopupWindow popupWindow;
  // 弹出窗口单行的高度
  private int mLinePopHeight;
  // 弹出窗口最大的高度
  private int mMaxPopHeight;
  //窗口布局
  private View view;
  //窗口布局根view
  private RelativeLayout pop_parent_layout;
  private GridView gridView;
  private TextView tv_userdiscuss_no_data_show, tv_no_data_show;
  private UsersDiscussPopAdapter mPopAdapter;
  private SwipeRefreshLayout mSryt;
  private List<String> mPopListData = new ArrayList<String>();
  private RelativeLayout rl;
  private CircleImageView imageView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    imageView = (CircleImageView) findViewById(R.id.iv_title_show_pop1);
    rl = (RelativeLayout) findViewById(R.id.rl);
    initData();
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showPopWindow(rl);
      }
    });
  }

  private void initData() {
    for (int i = 0; i < 20; i++) {
      mPopListData.add("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg");
    }
  }

  private void showPopWindow(View parent) {
    if (popupWindow == null) {
      mLinePopHeight = DisplayUtils.dip2px(this, 50);
      mMaxPopHeight = mLinePopHeight * 4;
      LayoutInflater layoutInflater =
          (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = layoutInflater.inflate(R.layout.layout_pop_users_discuss, null);
      pop_parent_layout = (RelativeLayout) view.findViewById(R.id.pop_parent_layout);
      tv_no_data_show = (TextView) view.findViewById(R.id.tv_no_data_show);
      popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, mMaxPopHeight);
      gridView = (GridView) view.findViewById(R.id.gridView);
      //TODO:接口一次性全部拉取,如果需要加载更多,布局文件中放开,此处放开即可
      //            mSryt = (SwipeRefreshLayout) view.findViewById(R.id.sryt_swipe_listview);
      //            mSryt.setColorSchemeColors(R.color.colorPrimary);
      //            mSwipeRefreshHelper = new SwipeRefreshHelper(mSryt);
      //            mSwipeRefreshHelper.setFooterView(new LoadMoreFooter());
      //            mSwipeRefreshHelper.setOnSwipeRefreshListener(new SwipeRefreshHelper.OnSwipeRefreshListener() {
      //                @Override
      //                public void onfresh() {
      //                    //去刷新
      //                }
      //            });
      //            mSwipeRefreshHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
      //                @Override
      //                public void loadMore() {
      //                    //去加载更多
      //                }
      //            });

      mPopAdapter =
          new UsersDiscussPopAdapter(this, mPopListData, R.layout.item_user_dis_pop_layout);
      gridView.setAdapter(mPopAdapter);
      if (pop_parent_layout != null) {
        pop_parent_layout.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
            popupWindow.dismiss();
          }
        });
      }
    }
    popupWindow.getContentView()
        .getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            popupWindow.getContentView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            initAnim(popupWindow.getContentView().getHeight());
          }
        });
    popupWindow.setFocusable(true);
    popupWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
    popupWindow.setAnimationStyle(R.style.popwin_anim_style);
    popupWindow.setBackgroundDrawable(new BitmapDrawable());
    int xPos = -popupWindow.getWidth() / 2;
    popupWindow.showAsDropDown(parent, 0, 0);
    //setGridHeight();
    mPopAdapter.notifyDataSetChanged();
  }

  private void initAnim(final int height) {
    popupWindow.getContentView().setTranslationY(- height);
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(height, 0).setDuration(500);
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        Float animatedValue = (Float) animation.getAnimatedValue();
        System.out.println(animatedValue);
        popupWindow.getContentView().setTranslationY(-animatedValue);
      }
    });
    valueAnimator.start();
    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override public void onDismiss() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(height, 0).setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override public void onAnimationUpdate(ValueAnimator animation) {
            Float animatedValue = (Float) animation.getAnimatedValue();
            System.out.println(animatedValue);
            popupWindow.getContentView().setTranslationY(animatedValue);
          }
        });
        valueAnimator.start();
      }
    });
  }

  // 设置弹窗的高度
  private void setGridHeight() {
    int numCount = (int) Math.ceil(mPopListData.size() / 4D);
    if (numCount <= 4) {
      //            RelativeLayout.LayoutParams parentlayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mLinePopHeight * 4 + mLinePopHeight / 2);
      //            mSryt.setLayoutParams(parentlayoutParams);
      RelativeLayout.LayoutParams layoutParams =
          new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              mLinePopHeight * 4 + mLinePopHeight / 2);
      gridView.setLayoutParams(layoutParams);
    } else {
      //            RelativeLayout.LayoutParams parentlayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mMaxPopHeight + mLinePopHeight / 2);
      //            mSryt.setLayoutParams(parentlayoutParams);
      RelativeLayout.LayoutParams layoutParams =
          new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mMaxPopHeight);
      gridView.setLayoutParams(layoutParams);
    }
  }
}
