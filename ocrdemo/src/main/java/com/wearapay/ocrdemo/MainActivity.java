package com.wearapay.ocrdemo;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private ImageView ivTarget;
  private ImageView ivSource;
  private Button button;
  private Button btnChinese;
  private Button btnScreen;
  private String mSavedPath;
  private Bitmap scanBitmap;

  private String picName = "htc.png";

  //训练数据路径，必须包含tesseract文件夹
  static final String TESSBASE_PATH = "/sdcard/tesseract/";
  //识别语言英文
  static final String DEFAULT_LANGUAGE = "eng";
  //识别语言简体中文
  static final String CHINESE_LANGUAGE = "chi_sim";
  private Bitmap cropBitmap;
  private TextView tvChinese;
  private TessBaseAPI baseApi;

  private Handler handler = new Handler(new Handler.Callback() {
    @Override public boolean handleMessage(Message msg) {
      tvChinese.setText((String) msg.obj);
      return false;
    }
  });

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    button = (Button) findViewById(R.id.btn);
    btnChinese = (Button) findViewById(R.id.btnChinese);
    btnScreen = (Button) findViewById(R.id.btnScreen);
    tvChinese = (TextView) findViewById(R.id.tvChinese);
    ivSource = (ImageView) findViewById(R.id.ivSource);
    ivTarget = (ImageView) findViewById(R.id.ivTarget);

    //ViewPager viewPager = new ViewPager(this);
    //viewPager.getCurrentItem();
    //viewPager.setCurrentItem(currentItme,false);
    requestPermissions(new String[] {
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    }, 1);

    button.setOnClickListener(this);
    btnChinese.setOnClickListener(this);
    btnScreen.setOnClickListener(this);
    baseApi = new TessBaseAPI();
    //初始化OCR的训练数据路径与语言
    baseApi.init(TESSBASE_PATH, CHINESE_LANGUAGE);
    //设置识别模式
    baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 1) {
      for (int i = 0; i < grantResults.length; i++) {
        if (grantResults[i] == PERMISSION_GRANTED) {
          showToast("权限通过");
        } else if (grantResults[i] == PERMISSION_DENIED) {
          showToast("权限拒绝");
        }
      }
    }
  }

  protected void showToast(String msg) {
    System.out.println(msg);
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }

  @Override public void onClick(View v) {
    if (v == button) {
      mSavedPath = "/sdcard" + File.separator + picName;
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true; // 先获取原大小
      scanBitmap = BitmapFactory.decodeFile(mSavedPath, options);
      options.inJustDecodeBounds = false; // 获取新的大小
      int sampleSize = 1/*(int) (options.outHeight / (float) 800)*/;
      if (sampleSize <= 0) sampleSize = 1;
      options.inSampleSize = sampleSize;
      scanBitmap = BitmapFactory.decodeFile(mSavedPath, options);
      ivSource.setImageBitmap(scanBitmap);

      cropBitmap = BitmapUtils.cropBitmap(scanBitmap);
      ivTarget.setImageBitmap(cropBitmap);
    } else if (v == btnChinese) {
      tvChinese.setText("1111111111");
      new Thread(new Runnable() {
        @Override public void run() {
          SimpleChineseOCR(cropBitmap);
        }
      }).start();
    } else if (v == btnScreen) {
      new Thread(new Runnable() {
        @Override public void run() {
          picName = "test.png";
          File file = new File("/sdcard" + File.separator + picName);
          if (file.exists()) {
            file.delete();
          }
          ScreenUtils.screenshot(file.getPath());
        }
      }).start();
    }
  }

  public void SimpleChineseOCR(Bitmap cropBitmap) {
    if (cropBitmap == null) {
      showToast(" crop bitmap is null");
      return;
    }
    long l = SystemClock.currentThreadTimeMillis();
    System.out.println(l);
    //设置要识别的图片
    baseApi.setImage(cropBitmap);
    String utf8Text = baseApi.getUTF8Text();
    Message message = Message.obtain();
    message.obj = utf8Text;
    handler.sendMessage(message);
    System.out.println(SystemClock.currentThreadTimeMillis() - l);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (baseApi != null) {
      baseApi.clear();
      baseApi.end();
    }
  }
}
