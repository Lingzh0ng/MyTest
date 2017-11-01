package com.wearapay.mydownloader;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static String apkUrl = "http://192.168.0.143:8080/alipay.apk";
  private static String fileUrl =
      "http://cj.weather.com.cn/support/Detail.aspx?id=51837fba1b35fe0f8411b6df";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        down();
      }
    });
    findViewById(R.id.down1).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        down1();
      }
    });
    findViewById(R.id.down3).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        down3();
      }
    });
  }

  private void down3() {
    File file = new File(Environment.getExternalStorageDirectory().getPath(), "myCity.json");
    List<City> list = new ArrayList<>();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
      StringBuffer stringBuffer = new StringBuffer();
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line);
      }
      bufferedReader.close();
      String[] split = stringBuffer.toString().split("==");
      City city = null;
      for (int i = 0; i < split.length; i++) {
        city = new City(split[i]);
        list.add(city);
        System.out.println(city);
      }
      System.out.println("citys:" + list.size());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void down() {
    //APKUtil.downloadAPK(MainActivity.this, apkUrl, "alipay.apk", "正在下载", null);

    APKDownloader apkDownloader = new APKDownloader();
    apkDownloader.downloader(apkUrl, Environment.getExternalStorageDirectory().getPath(),
        "alipay.apk", null, new APKDownloader.APKDownloadListener() {
          @Override public void onProgress(int progress) {
            System.out.println(progress);
          }

          @Override public void onStart() {
            System.out.println("start");
          }

          @Override public void onEnd(String path) {
            System.out.println("onEnd:" + path);
            APKUtil.installAPK(MainActivity.this, path);
          }

          @Override public void onError(Exception e) {
            System.out.println("onError");
          }
        });
  }

  private String a = "</p><p style=\"text-align:left;\">";
  private String b = "</p><p><br /></p>";
  private String c =
      "<p style=\"text-align:left;\"><strong>citycode 城市 二级 &nbsp;一级</strong></p><p style=\"text-align:left;\">";

  private void down1() {
    //APKUtil.downloadAPK(MainActivity.this, apkUrl, "alipay.apk", "正在下载", null);

    FileDownloader apkDownloader = new FileDownloader();
    apkDownloader.downloader(fileUrl, Environment.getExternalStorageDirectory().getPath(),
        "city.txt", null, new FileDownloader.APKDownloadListener() {
          @Override public void onProgress(int progress) {
            System.out.println(progress);
          }

          @Override public void onStart() {
            System.out.println("start");
          }

          @Override public void onEnd(String path) {
            System.out.println("onEnd:" + path);
            File file = new File(path);
            try {
              BufferedReader reader = new BufferedReader(new FileReader(file));
              String line = null;
              while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains(c)) {
                  line = line.replace(c, "");
                  line = line.replaceAll(a, "==");
                  line = line.replaceAll("，", ",");
                  line = line.replace(b, "");
                  line = line.replace(" ", "");
                  FileWriter fileWriter = new FileWriter(
                      new File(Environment.getExternalStorageDirectory().getPath(), "myCity.json"));
                  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                  bufferedWriter.write(line);
                  bufferedWriter.flush();
                  bufferedWriter.close();
                  reader.close();
                  return;
                }
              }
              reader.close();
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }

            //APKUtil.installAPK(MainActivity.this, path);
          }

          @Override public void onError(Exception e) {
            System.out.println("onError");
          }
        });
  }
}
