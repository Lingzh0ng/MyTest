package com.wearapay.mytest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wearapay.mytest.R;
import com.wearapay.mytest.bean.ContactBean;
import com.wearapay.mytest.ui.github.MaterialBadgeTextView;
import java.util.List;
import java.util.Random;

/**
 * Created by lyz on 2016/12/16.
 */
public class MyListViewAdapter extends BaseAdapter {
  private List list;
  private Context context;

  public MyListViewAdapter(Context context, List<Object> list) {
    this.list = list;
    this.context = context;
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public Object getItem(int i) {
    return null;
  }

  @Override public long getItemId(int i) {
    return 0;
  }

  @Override public int getItemViewType(int position) {
    if (list.get(position) instanceof ContactBean) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override public int getViewTypeCount() {
    return 2;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    int itemViewType = getItemViewType(i);
    if (itemViewType == 0) {
      if (view == null || view instanceof RelativeLayout) {
        view = View.inflate(context, R.layout.item, null);
      }
      TextView tv = (TextView) view.findViewById(R.id.tvName);
      MaterialBadgeTextView materialBadgeTextView =
          (MaterialBadgeTextView) view.findViewById(R.id.material_tv);
      materialBadgeTextView.setText(new Random().nextInt(100) + "");
      ContactBean bean = (ContactBean) list.get(i);
      tv.setText(bean.getName());
    } else {
      view = View.inflate(context, R.layout.item2, null);
      TextView tv = (TextView) view.findViewById(R.id.tvName);
      String a = (String) list.get(i);
      tv.setText(a);
      view.setClickable(false);
      //.setSelector(new ColorDrawable());
    }

    return view;
  }
}
