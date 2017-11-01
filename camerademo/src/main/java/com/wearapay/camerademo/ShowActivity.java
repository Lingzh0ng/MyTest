package com.wearapay.camerademo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/3/29.
 */

public class ShowActivity extends AppCompatActivity
    implements GvAdapter.OnCheckListener, View.OnClickListener {

  private int index = 1;

  private List<PhotoModel> all = new ArrayList<>();

  private List<PhotoModel> current = new ArrayList<>();

  private ArrayList<String> selectPath = new ArrayList<>();

  private Handler mHandler = new Handler(new Handler.Callback() {
    @Override public boolean handleMessage(Message msg) {
      int what = msg.what;
      if (what == 1) {
        show();
      }
      return true;
    }
  });
  private Button button;
  private Button button2;
  private PullToRefreshGridView gv;
  private GridView gridView;
  private GvAdapter adapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    getWindow().addFlags(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show);
    getSupportActionBar().hide();
    init();
  }

  private void init() {
    gv = (PullToRefreshGridView) findViewById(R.id.gv);
    gridView = gv.getRefreshableView();
    gv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
      @Override public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
        gv.onRefreshComplete();
      }

      @Override public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
        if (all.size() - 50 * index > 50) {
          for (int i = 50 * (index - 1); i < 50 * index; i++) {
            current.add(all.get(i));
          }
        } else {
          for (int i = 50 * (index - 1); i < all.size(); i++) {
            current.add(all.get(i));
          }
        }
        show();
      }
    });
    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String originalPath = all.get(position).getOriginalPath();
        ArrayList<String> list = new ArrayList<String>();
        list.add(originalPath);
        Intent intent = new Intent(ShowActivity.this, PhotoActivity.class);
        intent.putStringArrayListExtra("select", list);
        startActivity(intent);
      }
    });
    button2 = (Button) findViewById(R.id.btn2);
    button = (Button) findViewById(R.id.btn3);
    button.setOnClickListener(this);
    button2.setOnClickListener(this);
    adapter = new GvAdapter(this, current);
    adapter.setOnCheckListener(this);
    gridView.setAdapter(adapter);
    new Thread(new Runnable() {

      @Override public void run() {
        AlbumController albumController = new AlbumController(ShowActivity.this);
        all = albumController.getCurrent();
        if (all.size() - 50 * index > 50) {
          for (int i = 50 * (index - 1); i < 50 * index; i++) {
            current.add(all.get(i));
          }
        } else {
          for (int i = 50 * (index - 1); i < all.size(); i++) {
            current.add(all.get(i));
          }
        }

        mHandler.sendEmptyMessage(1);
      }
    }).start();
  }

  private void show() {
    index++;
    adapter.notifyDataSetChanged();
    gv.onRefreshComplete();
  }

  @Override public void onCheck(boolean isCheck, String path) {
    if (isCheck) {
      selectPath.add(path);
    } else {
      selectPath.remove(path);
    }
  }

  @Override public void onClick(View v) {
    if (button == v) {
      Intent data = new Intent();
      data.putStringArrayListExtra("select", selectPath);
      setResult(0, data);
      finish();
    } else if (button2 == v) {
      Intent data = new Intent(this, PhotoActivity.class);
      data.putStringArrayListExtra("select", selectPath);
      startActivity(data);
    }
  }

  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus && Build.VERSION.SDK_INT >= 19) {
      View decorView = getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }
}
