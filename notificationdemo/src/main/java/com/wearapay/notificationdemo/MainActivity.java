package com.wearapay.notificationdemo;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final int OVERLAY_PERMISSION_REQ_CODE = 1;
  private Button btShow;
  private Button btCancel;
  private Button btTime;
  private Button btStop;
  private Button btTest;
  private NotificationManager notificationManager;
  private Handler mHandler = new Handler(new Handler.Callback() {
    @Override public boolean handleMessage(Message msg) {
      notificationManager.showNotification(MainActivity.this, "");
      getForegroundApp(MainActivity.this);
      mHandler.sendEmptyMessageDelayed(0, 5000);
      return false;
    }
  });

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    btShow = (Button) findViewById(R.id.btShow);
    btCancel = (Button) findViewById(R.id.btCancel);
    btTime = (Button) findViewById(R.id.btTime);
    btStop = (Button) findViewById(R.id.btStop);
    btTest = (Button) findViewById(R.id.btTest);
    btTest.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        req();
      }
    });
    btShow.setOnClickListener(this);
    btCancel.setOnClickListener(this);
    btTime.setOnClickListener(this);
    btStop.setOnClickListener(this);
    notificationManager = NotificationManager.getNotificationManager(this);
  }

  public boolean isOpen() {
    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
    for (ActivityManager.RunningAppProcessInfo runinfo : runningAppProcesses) {
      String pn = runinfo.processName;
      System.out.println(pn);
      //if (pn.equals(packageName)
      //    && runinfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
      //  return true;
    }
    return false;
  }

  private String getForegroundApp() {

    File[] files = new File("/proc").listFiles();
    String foregroundProcess = "";
    int i = 0;
    for (File file : files) {
      i++;
      Log.d("brycegao", "proc file:" + file.getName() + ", loop:" + i);
      if (file.isFile()) {
        continue;
      }
      int pid;
      Log.d("brycegao", "proc filename:" + file.getName());
      try {
        pid = Integer.parseInt(file.getName());
      } catch (NumberFormatException e) {
        continue;
      }
      try {
        //读取进程名称
        String cmdline = do_exec(String.format("cat /proc/%d/cmdline", pid));
        String oomAdj = do_exec(String.format("cat /proc/%d/oom_adj", pid));
        Log.d("brycegao", "adj1111:" + oomAdj + ",pkg:" + cmdline);
        if (oomAdj.equalsIgnoreCase("0")) {
          //前台进程
          Log.d("brycegao", "adj:" + oomAdj + ",pkg:" + cmdline);
        } else {
          continue;
        }
        if (cmdline.contains("systemui") || cmdline.contains("/")) {
          continue;
        }
        foregroundProcess = cmdline;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    Log.d("brycegao", "forgroud process:" + foregroundProcess);
    return foregroundProcess;
  }

  private String do_exec(String cmd) {
    String s = "/n";
    try {
      Process p = Runtime.getRuntime().exec(cmd);
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = null;
      while ((line = in.readLine()) != null) {
        s += line + "/n";
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //text.setText(s);
    return s;
  }

  //private String getForegroundApp() {
  //  File[] files = new File("/proc").listFiles();
  //  String foregroundProcess = null;
  //  int i = 0;
  //  for (File file : files) {
  //    i++;
  //    Log.d("brycegao", "proc file:" + file.getName() + ", loop:" + i);
  //    if (file.isFile()) {
  //      continue;
  //    }
  //    int pid;
  //    Log.d("brycegao", "proc filename:" + file.getName());
  //    try {
  //      pid = Integer.parseInt(file.getName());
  //    } catch (NumberFormatException e) {
  //      continue;
  //    }
  //    try {
  //      //读取进程名称
  //      String cmdline = read(String.format("/proc/%d/cmdline", pid));
  //      String oomAdj = read(String.format("/proc/%d/oom_score_adj", pid));
  //      Log.d("brycegao", "adj1111:" + oomAdj + ",pkg:" + cmdline);
  //      if (oomAdj.equalsIgnoreCase("0")) {
  //        //前台进程
  //        Log.d("brycegao", "adj:" + oomAdj + ",pkg:" + cmdline);
  //      } else {
  //        continue;
  //      }
  //      if (cmdline.contains("systemui") || cmdline.contains("/")) {
  //        continue;
  //      }
  //      foregroundProcess = cmdline;
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
  //
  //  return foregroundProcess;
  //}

  private String read(String path) throws IOException {
    StringBuilder output = new StringBuilder();
    BufferedReader reader = new BufferedReader(new FileReader(path));
    output.append(reader.readLine());
    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
      output.append('\n').append(line);
    }
    reader.close();

    return output.toString();
  }

  private void req() {
    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
    startActivityForResult(intent, 2);
  }

  private String getForegroundApp(Context context) {

    UsageStatsManager usageStatsManager =
        (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
    //long ts = System.currentTimeMillis();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    long endt = calendar.getTimeInMillis();//结束时间
    calendar.add(Calendar.DAY_OF_MONTH, -1);//时间间隔为一个月
    long statt = calendar.getTimeInMillis();//开始时间
    List<UsageStats> queryUsageStats =
        usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, statt, endt);
    UsageEvents usageEvents = usageStatsManager.queryEvents(statt, endt);
    if (usageEvents == null) {
      return null;
    }

    UsageEvents.Event event = new UsageEvents.Event();
    UsageEvents.Event lastEvent = null;
    while (usageEvents.getNextEvent(event)) {
      // if from notification bar, class name will be null
      if (event.getPackageName() == null || event.getClassName() == null) {
        continue;
      }

      if (lastEvent == null || lastEvent.getTimeStamp() < event.getTimeStamp()) {
        lastEvent = event;
      }
      //System.out.println("name: " + lastEvent.getPackageName());
    }

    if (lastEvent == null) {
      return null;
    }
    System.out.println(" lastEvent: " + lastEvent.getPackageName());
    Toast.makeText(context,lastEvent.getPackageName(),Toast.LENGTH_SHORT).show();
    return lastEvent.getPackageName();
  }

  @Override public void onClick(View v) {
    if (askForPermission()) {
      return;
    }

    if (btShow == v) {
      notificationManager.showNotification(this, "");
    } else if (btCancel == v) {
      notificationManager.removerView();
    } else if (btTime == v) {
      mHandler.sendEmptyMessageDelayed(0, 5000);
    } else if (btStop == v) {
      mHandler.removeCallbacksAndMessages(null);
    }
  }

  public boolean askForPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!Settings.canDrawOverlays(this)) {
        Toast.makeText(MainActivity.this, "当前无权限，请授权！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        return true;
      }
    }
    return false;
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (!Settings.canDrawOverlays(this)) {
          Toast.makeText(MainActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(MainActivity.this, "权限授予成功，可以开启悬浮窗", Toast.LENGTH_SHORT).show();
        }
      }
    } else if (requestCode == 2) {
      getForegroundApp(this);
    }
  }
}
