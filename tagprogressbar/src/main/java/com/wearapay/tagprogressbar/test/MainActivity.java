package com.wearapay.tagprogressbar.test;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wearapay.tagprogressbar.R;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private MyAdapter mAdapter;
  private List<TimerItem> timerItems;
  private RecyclerView mRecyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.aaa);
    //timerItems = TimerItemUtil.getTimerItemList();
    //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    //mAdapter = new MyAdapter(this, timerItems);
    //mRecyclerView.setAdapter(mAdapter);
    //mRecyclerView.getViewTreeObserver()
    //    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    //      @Override public void onGlobalLayout() {
    //        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    //        mAdapter.handleTime();
    //      }
    //    });
    //TestClass.LightColor a = TestClass.LightColor.RED;
  }

  //适配器
  public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<TimerItem> mDatas;
    //用于退出activity,避免countdown，造成资源浪费。
    //private SparseArray<CountDownTimer> countDownMap;

    public MyAdapter(Context context, List<TimerItem> datas) {
      mDatas = datas;
      //countDownMap = new SparseArray<>();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.list_item_common, parent, false);
      return new ViewHolder(view);
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
      //if (countDownMap == null) {
      //  return;
      //}
      //Log.e("TAG", "size :  " + countDownMap.size());
      //for (int i = 0, length = countDownMap.size(); i < length; i++) {
      //  CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
      //  if (cdt != null) {
      //    cdt.cancel();
      //  }
      //}
      countDownTimer.cancel();
    }

    @Override public void onBindViewHolder(final ViewHolder holder, int position) {
      final TimerItem data = mDatas.get(position);
      holder.statusTv.setText(data.name);
      long time = data.expirationTime;
      //将前一个缓存清除
      //if (holder.countDownTimer != null) {
      //  holder.countDownTimer.cancel();
      //}
      if (time > 0) {
        holder.timeTv.setText(TimeTools.getCountTimeByLong(time));
        //holder.countDownTimer = new CountDownTimer(time, 60 * 1000) {
        //  public void onTick(long millisUntilFinished) {
        //    holder.timeTv.setText(TimeTools.getCountTimeByLong(millisUntilFinished));
        //    Log.e("TAG", data.name + " :  " + millisUntilFinished);
        //  }
        //
        //  public void onFinish() {
        //    holder.timeTv.setText("00:00:00");
        //    holder.statusTv.setText(data.name + ":结束");
        //  }
        //}.start();

        //countDownMap.put(holder.timeTv.hashCode(), holder.countDownTimer);
      } else {
        holder.timeTv.setText("00:00:00");
        holder.statusTv.setText(data.name + ":结束");
      }
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60 * 1000 * 60 * 24,  1000) {
      @Override public void onTick(long millisUntilFinished) {
        for (int i = 0; i < mDatas.size(); i++) {
          TimerItem timerItem = mDatas.get(i);
          long expirationTime = timerItem.expirationTime;
          if (expirationTime > 1000) {
            timerItem.expirationTime -=  1000;
          } else {
            timerItem.expirationTime = 0;
          }
        }
        System.out.println("countDownTimer:" + millisUntilFinished);
        notifyDataSetChanged();
      }

      @Override public void onFinish() {
        countDownTimer.onFinish();
      }
    };

    public void handleTime() {
      countDownTimer.start();
    }

    @Override public int getItemCount() {
      if (mDatas != null && !mDatas.isEmpty()) {
        return mDatas.size();
      }
      return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      public TextView statusTv;
      public TextView timeTv;

      public ViewHolder(View itemView) {
        super(itemView);
        statusTv = (TextView) itemView.findViewById(R.id.tv_status);
        timeTv = (TextView) itemView.findViewById(R.id.tv_time);
      }
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mAdapter != null) {
      mAdapter.cancelAllTimers();
    }
  }
}
