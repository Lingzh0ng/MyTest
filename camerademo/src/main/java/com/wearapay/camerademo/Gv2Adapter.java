package com.wearapay.camerademo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.List;

/**
 * Created by lyz on 2017/3/29.
 */

public class Gv2Adapter extends BaseAdapter {

  private Context context;
  private List<AlbumModel> list;
  private final int width;
  private LruCache<String, Bitmap> lruCache = new LruCache(30) {
    @Override protected int sizeOf(Object key, Object value) {
      return 1;
    }
  };

  private OnCheckListener onCheckListener;

  public interface OnCheckListener {
    void onCheck(boolean isCheck, String path);
  }

  public void setOnCheckListener(OnCheckListener onCheckListener) {
    this.onCheckListener = onCheckListener;
  }

  public Gv2Adapter(Context context, List<AlbumModel> list) {
    this.context = context;
    this.list = list;
    DisplayMetrics androidScreen = Utils.getAndroidScreen(context);
    width = androidScreen.widthPixels / 2;
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public Object getItem(int position) {
    return null;
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(final int position, View convertView, ViewGroup parent) {
    final AlbumModel albumModel = list.get(position);
    if (convertView == null) {
      convertView = View.inflate(context, R.layout.phone_item, null);
      convertView.setLayoutParams(new ViewGroup.LayoutParams(width, width));
    }
    convertView.findViewById(R.id.ivCheck).setVisibility(View.GONE);

    final ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
    final String originalPath = list.get(position).getRecent();
    AsyncTask asyncTask = new AsyncTask() {
      @Override protected Object doInBackground(Object[] params) {
        if (lruCache.get(originalPath) == null) {
          Bitmap bitmap = Utils.decodeSampledBitmapFromFile(originalPath, width, width);
          lruCache.put(originalPath, bitmap);
        }
        return lruCache.get(originalPath);
      }

      @Override protected void onPostExecute(Object o) {
        iv.setImageBitmap((Bitmap) o);
      }
    };
    asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    return convertView;
  }
}
