package com.wearapay.mytest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by lyz on 2016/12/16.
 */
public class APP extends Application {
  public static Context context;
  public static Handler mHandler;
  @Override public void onCreate() {
    super.onCreate();
    this.context = this;
    mHandler = new Handler();
  }
}
