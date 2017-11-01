package com.wearapay.mydownloader;

import android.os.AsyncTask;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by lyz on 2017/6/21.
 */
public class APKDownloader {
  private String mUrl = "";
  private String mSavePath = "";
  private String APK_NAME = "";
  private String APK_NAME_TEMP = "";
  private Map<String, String> heads;
  private int progress;
  private APKDownloadListener listener;

  public void downloader(String mUrl, String mSavePath, String APK_NAME, Map<String, String> heads,
      APKDownloadListener listener) {
    this.mUrl = mUrl;
    this.mSavePath = mSavePath;
    this.APK_NAME = APK_NAME;
    this.heads = heads;
    this.listener = listener;
    File file = new File(mSavePath, APK_NAME);
    if (file.exists()) {
      listener.onEnd(file.getPath());
    } else {
      APK_NAME_TEMP = APK_NAME + ".temp";
      new APKDownloadTask().execute();
    }
  }

  private class APKDownloadTask extends AsyncTask<Object, Integer, File> {

    @Override protected File doInBackground(Object[] params) {
      File apkFile = null;
      File apkFile1 = null;
      InputStream is = null;
      FileOutputStream fos = null;
      try {
        URL url = new URL(mUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setConnectTimeout(5000);
        if (heads != null) {
          for (Map.Entry<String, String> entry : heads.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
          }
        }
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
          is = connection.getInputStream();
          File file = new File(mSavePath);
          // 判断文件目录是否存在
          if (!file.exists()) {
            file.mkdirs();
          }
          apkFile = new File(mSavePath, APK_NAME_TEMP);
          fos = new FileOutputStream(apkFile);
          int contentLength = connection.getContentLength();
          float count = 0;
          int len = -1;
          // 缓存
          byte[] buffer = new byte[4096];
          // 写入到文件中
          while ((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
            count += len;
            int innerProgress = (int) (count / contentLength * 100);
            //System.out.println(innerProgress);

            if (innerProgress > progress) {
              progress = innerProgress;
              System.out.println(progress);
              publishProgress(innerProgress);
            }
          }
          apkFile1 = new File(mSavePath, APK_NAME);
          if (apkFile.exists()) {
            apkFile.delete();
          }
          if (!apkFile.renameTo(apkFile1)) {
            return apkFile;
          }
        }
      } catch (MalformedURLException e) {
        e.printStackTrace();
        listener.onError(e);
      } catch (IOException e) {
        e.printStackTrace();
        listener.onError(e);
      } finally {
        try {
          if (fos != null) fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        try {
          if (is != null) is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return apkFile1;
    }

    @Override protected void onPreExecute() {
      super.onPreExecute();
      listener.onStart();
    }

    @Override protected void onPostExecute(File o) {
      super.onPostExecute(o);
      listener.onEnd(o.getPath());
    }

    @Override protected void onProgressUpdate(Integer[] values) {
      super.onProgressUpdate(values);
      listener.onProgress((values[0]));
    }
  }

  public interface APKDownloadListener {
    void onProgress(int progress);

    void onStart();

    void onEnd(String path);

    void onError(Exception e);
  }
}
