package com.song.wallpaper;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import java.io.IOException;

public class CameraLiveWallpaper extends WallpaperService {

  // 实现WallpaperService必须实现的抽象方法
  public Engine onCreateEngine() {
    // 返回自定义的CameraEngine
    return new CameraEngine();
  }

  class CameraEngine extends Engine implements Camera.PreviewCallback, Camera.AutoFocusCallback {
    private Camera camera;
    public Handler mhHandler = new Handler(new Handler.Callback() {
      @Override public boolean handleMessage(Message message) {
        if (camera != null) {
          camera.cancelAutoFocus();
          camera.autoFocus(CameraEngine.this);
        }
        return true;
      }
    });

    @Override public void onCreate(SurfaceHolder surfaceHolder) {
      super.onCreate(surfaceHolder);

      startPreview();
      // 设置处理触摸事件
      setTouchEventsEnabled(true);
    }

    @Override public void onTouchEvent(MotionEvent event) {
      super.onTouchEvent(event);
      // 时间处理:点击拍照,长按拍照
    }

    @Override public void onDestroy() {
      super.onDestroy();
      stopPreview();
    }

    @Override public void onVisibilityChanged(boolean visible) {
      if (visible) {
        startPreview();
      } else {
        stopPreview();
      }
    }

    /**
     * 开始预览
     */
    public void startPreview() {
      camera = Camera.open();
      camera.setDisplayOrientation(90);

      try {
        camera.setPreviewDisplay(getSurfaceHolder());
      } catch (IOException e) {
        e.printStackTrace();
      }
      camera.startPreview();
      camera.autoFocus(this);
    }

    /**
     * 停止预览
     */
    public void stopPreview() {
      if (camera != null) {
        try {
          camera.stopPreview();
          camera.setPreviewCallback(null);
          camera.cancelAutoFocus();
          // camera.lock();
          camera.release();
        } catch (Exception e) {
          e.printStackTrace();
        }
        camera = null;
      }
    }

    @Override public void onPreviewFrame(byte[] bytes, Camera camera) {
      camera.addCallbackBuffer(bytes);
    }

    @Override public void onAutoFocus(boolean b, Camera camera) {
      mhHandler.sendEmptyMessageDelayed(1, 1000);
    }
  }
}  