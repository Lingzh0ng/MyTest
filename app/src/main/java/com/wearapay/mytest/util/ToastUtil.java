package com.wearapay.mytest.util;

import android.view.Gravity;
import android.widget.Toast;
import com.wearapay.mytest.APP;

/**
 * Created by lyz on 2016/12/16.
 */
public class ToastUtil {
  private static Toast toast;
  public static void show(String msg) {
    if (toast == null) {
      toast =Toast.makeText(APP.context,msg,Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
    }
    toast.setText(msg);
    toast.show();
  }
}
