package com.wearapay.chatdemo;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Kindy
 * @since 2015-07-16
 */
public class WPViewHolder {
  private View mConvertView;
  private SparseArray<View> mViews;

  private WPViewHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
    mConvertView = inflater.inflate(layoutId, parent, false);
    mViews = new SparseArray<>();
  }

  public static WPViewHolder create(View convertView, LayoutInflater inflater, ViewGroup parent,
      int layoutId) {
    WPViewHolder holder;

    if (convertView == null) {
      holder = new WPViewHolder(inflater, parent, layoutId);
      holder.mConvertView.setTag(holder);
    } else {
      holder = (WPViewHolder) convertView.getTag();
    }

    return holder;
  }

  public View getConvertView() {
    return mConvertView;
  }

  @SuppressWarnings("unchecked") public <T extends View> T findView(int viewId) {
    View view = mViews.get(viewId);
    if (view == null) {
      view = mConvertView.findViewById(viewId);
      mViews.put(viewId, view);
    }
    return (T) view;
  }

  /**
   * for TextView
   */
  public void setText(int viewId, CharSequence text) {
    TextView tv = findView(viewId);
    tv.setText(text);
  }

  /**
   * for TextView
   */
  public void setText(int viewId, int resId) {
    TextView tv = findView(viewId);
    tv.setText(resId);
  }

  /**
   * for ImageView
   */
  public void setImage(int viewId, int resId) {
    ImageView iv = findView(viewId);
    iv.setImageResource(resId);
  }

  /**
   * Make view invisible
   */
  public void setVisibility(int viewId, int visibility){
    View v = findView(viewId);
    v.setVisibility(visibility);
  }
}
