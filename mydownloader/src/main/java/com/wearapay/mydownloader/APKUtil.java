package com.wearapay.mydownloader;

import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.io.File;
import java.util.Map;

import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * Created by Kindy on 2016-05-13.
 */
public class APKUtil {

  public static long downloadAPK(Context context, String apkUrl, String name, String description,
      Map<String, String> heads) {
    DownloadManager downloadManager =
        (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    if (name == null || name.length() == 0) {
      int end = apkUrl.indexOf("?");
      if (end < 0) {
        end = apkUrl.length();
      }
      int pos = apkUrl.lastIndexOf("/");
      if (pos > -1) {
        name = apkUrl.substring(pos + 1, end);
      }
    }
    //String apkUrl =  "http://115.231.37.154/dlied5.myapp.com/myapp/1104311365/rxcq/2017_mirmobile_1.0.0.535_20150819_etc1_pub_signed.apk?mkey=55f11cf9130bc090&f=1225&p=.apk";
    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
    //设置下载地址为sd卡的Download文件夹，文件名为MeiLiShuo.apk
    request.setDestinationInExternalPublicDir("Download", name);
    //request.allowScanningByMediaScanner();//表示允许MediaScanner扫描到这个文件，默认不允许
    request.setTitle(name);//设置下载中通知栏提示的标题
    request.setDescription(description);//设置下载中通知栏提示的介绍

    //表示下载进行中和下载完成的通知栏是否显示。默认只显示下载中通知。
    //VISIBILITY_VISIBLE_NOTIFY_COMPLETED表示下载完成后显示通知栏提示。
    //VISIBILITY_HIDDEN表示不显示任何通知栏提示，这个需要在AndroidMainfest中添加权限android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
    request.setNotificationVisibility(
        DownloadManager.Request.VISIBILITY_VISIBLE);//只在下载时显示进度，下载完成通知栏消失
    request.setVisibleInDownloadsUi(true);

    //表示下载允许的网络类型，默认在任何网络下都允许下载。
    //有NETWORK_MOBILE、NETWORK_WIFI、NETWORK_BLUETOOTH三种及其组合可供选择。
    //如果只允许wifi下载，而当前网络为3g，则下载会等待
    //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

    request.setAllowedOverRoaming(false);//移动网络情况下是否允许漫游

    //设置下载文件的mineType。
    //因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，所以我们可以利用这个属性。
    //比如上面设置了mimeType为application/cn.trinea.download.file，
    //我们可以同时设置某个Activity的intent-filter为application/cn.trinea.download.file，用于响应点击的打开文件。
    // request.setMimeType("application/cn.trinea.download.file");

    //request.addRequestHeader(Stringheader, String value);//添加请求下载的网络链接的http头，比如User-Agent，gzip压缩等
    if (heads != null) {
      for (Map.Entry<String, String> entry : heads.entrySet()) {
        request.addRequestHeader(entry.getKey(), entry.getValue());
      }
    }

    long downloadId = downloadManager.enqueue(request);
    return downloadId;
  }

  public static void installAPK(Context context, String filePath) {
    //执行动作
    Intent intent = new Intent(Intent.ACTION_VIEW);
    //执行的数据类型
    intent.setDataAndType(Uri.fromFile(new File(filePath)),
        "application/vnd.android.package-archive");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void installAPK(Context context, Uri fileUri) {
    //执行动作
    Intent intent = new Intent(Intent.ACTION_VIEW);
    //执行的数据类型
    intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    context.startActivity(intent);
  }

  public static void openDownloadList(Context context) {
    Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  /**
   * app available heap
   */
  public static int getHeapSize(Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
    int memoryClass = am.getMemoryClass();
    if (largeHeap && SDK_INT >= HONEYCOMB) {
      memoryClass = am.getLargeMemoryClass();
    }
    int heapSize = 1024 * 1024 * memoryClass;
    return heapSize;
  }
}
