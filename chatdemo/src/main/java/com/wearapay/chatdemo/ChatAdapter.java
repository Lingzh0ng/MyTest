package com.wearapay.chatdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import java.util.Random;

/**
 * Created by lyz on 2017/1/4.
 */
public class ChatAdapter extends BaseAdapter {

  private final int MSG_RECE = 0;
  private final int MSG_SEND = 1;

  private Context context;
  private List<MsgBean> list;

  public ChatAdapter(Context context, List<MsgBean> list) {
    this.context = context;
    this.list = list;
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
    MsgBean msgBean = list.get(position);
    return msgBean.getType();
  }

  @Override public int getViewTypeCount() {
    return 2;
  }

  @Override public View getView(int position, View convertView, ViewGroup viewGroup) {
    ViewHolder holder;
    int type = getItemViewType(position);
    if (convertView == null) {
      holder = new ViewHolder();
      switch (type) {
        case MSG_RECE:
          convertView = View.inflate(context, R.layout.item_receive, null);
          holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
          holder.tvMsg = (TextView) convertView.findViewById(R.id.tvMessage);
          holder.ibRest = (ImageButton) convertView.findViewById(R.id.ibRest);
          break;
        case MSG_SEND:
          convertView = View.inflate(context, R.layout.item_send, null);
          holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
          holder.tvMsg = (TextView) convertView.findViewById(R.id.tvMessage);
          holder.ibRest = (ImageButton) convertView.findViewById(R.id.ibRest);
          break;
        default:
          break;
      }
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    holder.tvTime.setVisibility(View.GONE);
    holder.ibRest.setVisibility(View.GONE);
    boolean b = new Random().nextBoolean();
    if (b) {
      holder.tvTime.setVisibility(View.VISIBLE);
      holder.ibRest.setVisibility(View.VISIBLE);
    }
    holder.tvTime.setText(list.get(position).getTime());
    holder.tvMsg.setText(list.get(position).getMessage());
    return convertView;
  }

  static class ViewHolder {
    TextView tvTime;
    TextView tvMsg;
    ImageButton ibRest;
  }

}
