package com.wearapay.chatdemo;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by lyz on 2017/1/4.
 */
public class ToastUtil {
  private static Toast toast;
  public static void show(Context context,String message){
    if (toast == null) {
      toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
    }
    toast.setGravity(Gravity.CENTER,0,0);
    toast.setText(message);
    toast.show();
  }
}
