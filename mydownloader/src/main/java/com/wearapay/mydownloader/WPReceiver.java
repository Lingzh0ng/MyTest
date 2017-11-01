package com.wearapay.mydownloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import java.io.File;

/**
 * Created by lyz on 2017/6/21.
 */
public class WPReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
      long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
      DownloadManager manager =
          (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
      DownloadManager.Query query = new DownloadManager.Query();
      query.setFilterById(downId);
      Cursor c = manager.query(query);
      String filename = null;
      if (c.moveToFirst()) {
        //获取文件下载路径
        filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
        System.out.println(" ==== " + downId + " : " + filename);
      }
      c.close();
      //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
      if (filename != null) {
        //APKUtil.installAPK(context, filename);
        if (Build.VERSION.SDK_INT > 23) {
          File file = new File(filename);
          System.out.println("file :"
              + file.getPath()
              + " size:"
              + file.length()
              + " canRead:"
              + file.canRead()
              + " lastModified:"
              + file.lastModified());
          Uri uriForFile =
              FileProvider.getUriForFile(context, "com.wearapay.wallet.fileprovider", file);
          APKUtil.installAPK(context, uriForFile);
        } else {
          APKUtil.installAPK(context, filename);
        }
      }
    }
  }
}
