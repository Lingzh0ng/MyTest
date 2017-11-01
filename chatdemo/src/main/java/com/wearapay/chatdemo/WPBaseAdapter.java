package com.wearapay.chatdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * @author Kindy
 * @since 2015-07-16
 */
public abstract class WPBaseAdapter<T> extends android.widget.BaseAdapter {
  protected Context mContext;
  protected List<T> mDataList;
  protected LayoutInflater mInflater;
  protected int[] mLayoutIds;

  public WPBaseAdapter(Context context, List<T> dataList, int... layoutIds) {
    mContext = context;
    mDataList = dataList;
    mInflater = LayoutInflater.from(context);
    mLayoutIds = layoutIds;
  }

  public int getLayoutId(int position) {
    return mLayoutIds[getItemViewType(position)];
  }

  @Override public int getViewTypeCount() {
    return mLayoutIds.length;
  }

  /**
   * TODO 多布局需用户重写
   */
  @Override public int getItemViewType(int position) {
    return 0;
  }

  @Override public int getCount() {
    return mDataList.size();
  }

  @Override public Object getItem(int position) {
    return mDataList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void notifyDataSetChanged(List<T> dataList) {
    this.mDataList = dataList;
    this.notifyDataSetChanged();
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    WPViewHolder holder =
        WPViewHolder.create(convertView, mInflater, parent, getLayoutId(position));
    fillItem(position, holder);

    return holder.getConvertView();
  }

  public abstract void fillItem(int position, WPViewHolder holder);

  public List<T> getDataList() {
    return mDataList;
  }

  @Override public boolean isEmpty() {
    return false;
  }
}
