package com.wearapay.camerademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.wearapay.camerademo.Utils.createTempImage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private File tempFile;
  private Button button;
  private Button button1;
  private Button button2;
  private ViewPager vp;
  private ArrayList<String> selects = new ArrayList<>();
  private File photoFile;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    button = (Button) findViewById(R.id.btn);
    button1 = (Button) findViewById(R.id.btn1);
    button2 = (Button) findViewById(R.id.btn2);
    vp = (ViewPager) findViewById(R.id.vp);
    button.setOnClickListener(this);
    button1.setOnClickListener(this);
    button2.setOnClickListener(this);
    initViewPager();
  }

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

  private void initViewPager() {
    vp.setAdapter(adapter);
  }

  PagerAdapter adapter = new PagerAdapter() {
    @Override public int getCount() {
      return selects.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      final ImageView imageView = new ImageView(MainActivity.this);
      final String s = selects.get(position);
      //imageView.setImageBitmap();
      container.addView(imageView);
      AsyncTask asyncTask = new AsyncTask() {
        @Override protected Object doInBackground(Object[] params) {

          return Utils.decodeSampledBitmapFromFile(s,
              Utils.getAndroidScreen(MainActivity.this).widthPixels,
              Utils.getAndroidScreen(MainActivity.this).heightPixels);
        }

        @Override protected void onPostExecute(Object o) {
          imageView.setImageBitmap((Bitmap) o);
        }
      };
      asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
      return imageView;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }
  };

  private void startCamera() {
    Camera camera = null;
    try {
      camera = Camera.open();
    } catch (Exception e) {
      Toast.makeText(this, "没权限", Toast.LENGTH_SHORT).show();
      return;
    } finally {
      if (camera != null) {
        camera.release();
      }
    }
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
      File photoFile = null;
      try {
        photoFile = createTempImage(this, null, true);
        tempFile = photoFile;
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (photoFile != null) {
        Uri tempPhotoUri = Uri.fromFile(photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempPhotoUri);
        startActivityForResult(takePictureIntent, 1);
      }
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      //imageView.setImageBitmap(
      //    decodeSampledBitmapFromFile(tempFile, getAndroidScreen(this).widthPixels,
      //        getAndroidScreen(this).heightPixels));
      selects.add(tempFile.getAbsolutePath());
    } else if (requestCode == 2) {
      if (data == null) {
        return;
      }
      ArrayList<String> listExtra = data.getStringArrayListExtra("select");
      if (listExtra != null && listExtra.size() != 0) {
        selects.addAll(listExtra);
        //imageView.setImageBitmap(
        //    decodeSampledBitmapFromFile(selects.get(0), getAndroidScreen(this).widthPixels,
        //        getAndroidScreen(this).heightPixels));
      }
    } else if (requestCode == 3) {
      if (photoFile != null) {
        //selects.remove(vp.getCurrentItem());
        selects.add(photoFile.getAbsolutePath());
      }
    }
    adapter.notifyDataSetChanged();
  }

  private String getForegroundApp() {
    File[] files = new File("/proc").listFiles();
    String foregroundProcess = "";
    int i = 0;
    for (File file : files) {
      i++;
      //Log.d("brycegao", "proc file:" + file.getName() + ", loop:" + i);
      if (file.isFile()) {
        continue;
      }
      int pid;
      //Log.d("brycegao", "proc filename:" + file.getName());
      try {
        pid = Integer.parseInt(file.getName());
      } catch (NumberFormatException e) {
        continue;
      }
      try {
        //读取进程名称
        String cmdline = do_exec(String.format("cat /proc/%d/cmdline", pid));
        String oomAdj = do_exec(String.format("cat /proc/%d/oom_adj", pid));
        //Log.d("brycegao", "adj1111:" + oomAdj + ",pkg:" + cmdline);
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
        s += line;
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //text.setText(s);
    return s;
  }

  @Override public void onClick(View v) {
    if (v == button) {
      startCamera();
      //getForegroundApp();
    } else if (v == button1) {
      startActivityForResult(new Intent(MainActivity.this, ShowActivity.class), 2);
    } else if (v == button2) {
      startCrop();
    }
  }

  private void startCrop() {
    if (selects.size() > 0) {
      String s = selects.get(vp.getCurrentItem());
      if (!TextUtils.isEmpty(s)) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(s)), "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 640);
        intent.putExtra("aspectY", 640);
        intent.putExtra("return-data", false);
        try {
          photoFile = Utils.createTempImage(this, null, true);
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (photoFile != null) {
          Uri tempPhotoUri = Uri.fromFile(photoFile);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, tempPhotoUri);
          startActivityForResult(intent, 3);
        }
      }
    }
  }
}
