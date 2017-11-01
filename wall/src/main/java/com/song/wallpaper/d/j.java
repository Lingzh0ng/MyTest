package com.song.wallpaper.d;

/**
 * Created by lyz on 2017/8/11.
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class j {
  public static String a(String paramString) {
    String str1;
    if (paramString == null) {
      boolean bool = "".equals(paramString);
      str1 = null;
      if (!bool) ;
    } else {
      str1 = "";
    }
    try {
      String str2 = b(a(paramString.getBytes("utf-8")));
      return str2;
    } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
      e.a(j.class.getSimpleName(), "编码出现错误");
    }
    return str1;
  }

  private static byte[] a(byte[] paramArrayOfByte) {
    try {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      return arrayOfByte;
    } catch (Exception localException) {
      e.a(j.class.getSimpleName(), "未知算法");
    }
    return null;
  }

  private static String b(byte[] paramArrayOfByte) {
    if (paramArrayOfByte == null) return "";
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < paramArrayOfByte.length) {
      String str = Integer.toHexString(0xFF & paramArrayOfByte[i]);
      if (str.length() != 2) localStringBuffer.append('0').append(str);
      while (true) {
        i++;
        break;
        localStringBuffer.append(str);
      }
    }
    return new String(localStringBuffer);
  }
}