package com.wearapay.ypulltorefresh;

import java.text.SimpleDateFormat;
import java.util.Locale;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    //assertEquals(4, 2 + 2);
    //  Endecrypt test = new Endecrypt();
    //  String oldString = "lingyang1218yj@";
    //  System.out.println("1、SPKEY为:  " + SPKEY);
    //  System.out.println("2、明文密码为:  " + oldString);
    //  String reValue = test.get3DESEncrypt(oldString, SPKEY);
    //  reValue = reValue.trim().intern();
    //  System.out.println("3、进行3-DES加密后的内容: " + reValue);
    //  String reValue2 = test.get3DESDecrypt(reValue, SPKEY);
    //  System.out.println("4、进行3-DES解密后的内容: " + reValue2);
    String stringFormatTime = getStringFormatTime(1413216000000L, "yyyy.MM.dd");
    String stringFormatTime1 = getStringFormatTime(1569600000000L, "yyyy.MM.dd");

    String format = "有效期：%1$s-%2$s";
    String format1 = String.format(Locale.CHINA,format, stringFormatTime, stringFormatTime1);
    String format2 = String.format(Locale.ENGLISH,format, stringFormatTime, stringFormatTime1);
    String format3 = String.format(Locale.CHINESE,format, stringFormatTime, stringFormatTime1);
    String format4 = String.format(Locale.getDefault(),format, stringFormatTime, stringFormatTime1);
    String format5 = String.format(format, stringFormatTime, stringFormatTime1);
    System.out.println(format1);
    System.out.println(format2);
    System.out.println(format3);
    System.out.println(format4);
    System.out.println(format5);

    System.out.println(stringFormatTime + "  --------------------- " + stringFormatTime1);
  }

  public static String getStringFormatTime(Long date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }
}