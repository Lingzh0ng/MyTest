package com.song.wallpaper.d;

/**
 * Created by lyz on 2017/8/11.
 */
import android.content.Context;
import android.util.Log;

public class e
{
  public static boolean a = true;

  public static void a(Context paramContext, String paramString)
  {
    b(paramContext.getClass().getSimpleName(), paramString);
  }

  public static void a(String paramString1, String paramString2)
  {
    if (a)
      Log.e("Framework-" + paramString1, paramString2 + "  ");
  }

  public static void b(String paramString1, String paramString2)
  {
    if (a)
      Log.d("Framework-" + paramString1, paramString2 + "  ");
  }
}