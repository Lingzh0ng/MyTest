package com.wearapay.camerademo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by lyz on 2017/3/30.
 */

public class PhotoActivity extends AppCompatActivity {

  private ArrayList<String> select = new ArrayList<>();
  private ViewPager viewPager;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo);
    getSupportActionBar().hide();
    init();
  }

  private Handler mHandler = new Handler(new Handler.Callback() {
    @Override public boolean handleMessage(Message msg) {
      int what = msg.what;
      if (what == 1) {
        show();
      }
      return true;
    }
  });

  private void show() {

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

  private List<AlbumModel> current = new ArrayList<>();

  private void init() {
    select.addAll(getIntent().getStringArrayListExtra("select"));
    viewPager = (ViewPager) findViewById(R.id.vp);
    MyViewPagerAdapter adapter = new MyViewPagerAdapter();
    viewPager.setAdapter(adapter);
  }

  private class MyViewPagerAdapter extends PagerAdapter {
    @Override public int getCount() {
      return select.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      PhotoView photoView = new PhotoView(PhotoActivity.this);
      Glide.with(PhotoActivity.this)
          .load(new File(select.get(position)))
          .placeholder(R.mipmap.ic_launcher)
          .into(photoView);
      container.addView(photoView);
      return photoView;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }
  }
}
