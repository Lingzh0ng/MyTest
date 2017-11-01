package com.wearapay.camerademo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lyz on 2017/3/29.
 */

public class Utils {

  public static DisplayMetrics getAndroidScreen(Context activity) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity) activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics;
  }

  public static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth, int reqHeight) {
    String path = file.getAbsolutePath();
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);

    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(path, options);
  }

  public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);

    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(path, options);
  }

  private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
      int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and keeps both
      // height and width larger than the requested height and width.
      while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
      }
    }

    return inSampleSize;
  }

  public static File createTempImage(Context context, String suffixName, boolean isPNG)
      throws IOException {
    String timeStamp = suffixName;
    if (suffixName == null || suffixName.length() == 0) {
      timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
    }
    File storageDir = getExternalImagesDir(context);
    File image = File.createTempFile(timeStamp,  /* prefix */
        isPNG ? ".png" : ".jpeg",         /* suffix */
        storageDir      /* directory */);
    return image;
  }

  private static File getExternalImagesDir(Context context) {
    PackageManager pm = context.getPackageManager();
    String appName = context.getApplicationInfo().loadLabel(pm).toString();
    File appDir = new File(Environment.getExternalStorageDirectory(), appName);
    if (!appDir.exists()) {
      appDir.mkdir();
    }

    return appDir;
    //return context.getExternalFilesDir("image");
  }
}
