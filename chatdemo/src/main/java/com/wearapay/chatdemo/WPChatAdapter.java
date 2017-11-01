package com.wearapay.chatdemo;

import android.content.Context;
import android.view.View;
import java.util.List;
import java.util.Random;

/**
 * Created by lyz on 2017/1/4.
 */
public class WPChatAdapter extends WPBaseAdapter<Message> {

  private final int MSG_RECE = 0;
  private final int MSG_SEND = 1;

  public WPChatAdapter(Context context, List<Message> list) {
    super(context, list);
    mLayoutIds = new int[] {
        R.layout.item_receive, R.layout.item_send
    };
  }

  @Override public int getItemViewType(int position) {
    Message message = mDataList.get(position);
    if (message.isIncoming()) {
      return MSG_RECE;
    }
    return MSG_SEND;
  }

  @Override public void fillItem(final int position, WPViewHolder holder) {
    Message message = mDataList.get(position);
    holder.setText(R.id.tvMessage, message.getContent());
    View view = holder.findView(R.id.ibRest);
    if (view != null) {
      view.setVisibility(View.GONE);
      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          ToastUtil.show(mContext, "点击了:" + position);
        }
      });
      if (new Random().nextBoolean()) {
        view.setVisibility(View.VISIBLE);
      }
    }
  }
}
