package com.song.wallpaper.d;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lyz on 2017/8/11.
 */

public class d
{
  public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static final SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat c = new SimpleDateFormat("yyyyMMddHHmmss");

  public static long a()
  {
    return System.currentTimeMillis();
  }

  public static String a(long paramLong)
  {
    return a(paramLong, a);
  }

  public static String a(long paramLong, SimpleDateFormat paramSimpleDateFormat)
  {
    return paramSimpleDateFormat.format(new Date(paramLong));
  }

  public static String b()
  {
    return a(a());
  }

  public static String c()
  {
    return a(a(), c);
  }
}