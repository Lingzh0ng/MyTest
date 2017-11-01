package com.wearapay.camerademo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.List;

/**
 * Created by lyz on 2017/3/29.
 */

public class GvAdapter extends BaseAdapter {

  private Context context;
  private List<PhotoModel> list;
  private final int width;
  private LruCache<String, Bitmap> lruCache = new LruCache(50) {
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

  public GvAdapter(Context context, List<PhotoModel> list) {
    this.context = context;
    this.list = list;
    DisplayMetrics androidScreen = Utils.getAndroidScreen(context);
    width = androidScreen.widthPixels / 3;
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
    final PhotoModel photoModel = list.get(position);
    if (convertView == null) {
      convertView = View.inflate(context, R.layout.phone_item, null);
      convertView.setLayoutParams(new ViewGroup.LayoutParams(width, width));
    }
    final ImageView ivCheck = (ImageView) convertView.findViewById(R.id.ivCheck);
    if (photoModel.isChecked()) {
      ivCheck.setImageResource(R.drawable.feedback_selected);
    } else {
      ivCheck.setImageResource(R.drawable.feedback_unselected);
    }

    ivCheck.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        photoModel.setChecked(!photoModel.isChecked());
        if (photoModel.isChecked()) {
          ivCheck.setImageResource(R.drawable.feedback_selected);
        } else {
          ivCheck.setImageResource(R.drawable.feedback_unselected);
        }
        if (onCheckListener != null) {
          onCheckListener.onCheck(photoModel.isChecked(), photoModel.getOriginalPath());
        }
      }
    });
    final ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
    final String originalPath = list.get(position).getOriginalPath();
    Glide.with(context).load(new File(originalPath)).centerCrop().animate(R.anim.abc_fade_in).placeholder(R.mipmap.ic_launcher).crossFade().into(iv);
    //AsyncTask asyncTask = new AsyncTask() {
    //  @Override protected Object doInBackground(Object[] params) {
    //    if (lruCache.get(originalPath) == null) {
    //      Bitmap bitmap = Utils.decodeSampledBitmapFromFile(originalPath, width, width);
    //      lruCache.put(originalPath, bitmap);
    //    }
    //    return lruCache.get(originalPath);
    //  }
    //
    //  @Override protected void onPostExecute(Object o) {
    //    iv.setImageBitmap((Bitmap) o);
    //  }
    //};
    //asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    //if (position < 40) {
    //} else {
    //  asyncTask.execute();
    //}

    return convertView;
  }
}
