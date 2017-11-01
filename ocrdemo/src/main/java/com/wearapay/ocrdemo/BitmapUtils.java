package com.wearapay.ocrdemo;

import android.graphics.Bitmap;

/**
 * Created by lyz on 2017/8/30.
 */

public class BitmapUtils {

  public static Bitmap cropBitmap(Bitmap bitmap) {
    int w = bitmap.getWidth(); // 得到图片的宽，高
    int h = bitmap.getHeight();
    int startWidth = (int) ((400f / 1080f) * w);
    int endWidth = (int) ((680f / 1080f) * w);
    int startHeight = (int) ((1620f / 1920f) * h);
    int endHeight = (int) ((1680f / 1920f) * h);
    return Bitmap.createBitmap(bitmap, startWidth, startHeight, endWidth - startWidth,
        endHeight - startHeight, null, false);
  }
}
