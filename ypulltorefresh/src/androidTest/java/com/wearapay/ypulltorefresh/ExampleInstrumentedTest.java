package com.wearapay.ypulltorefresh;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.wearapay.ypulltorefresh.Endecrypt.SPKEY;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class) public class ExampleInstrumentedTest {
  @Test public void useAppContext() throws Exception {
    // Context of the app under test.
    Endecrypt test = new Endecrypt();
    String oldString = "lingyang1218yj@";
    System.out.println("1、SPKEY为:  " + SPKEY);
    System.out.println("2、明文密码为:  " + oldString);
    String reValue = test.get3DESEncrypt(oldString, SPKEY);
    reValue = reValue.trim().intern();
    System.out.println("3、进行3-DES加密后的内容: " + reValue);
    String reValue2 = test.get3DESDecrypt(reValue, SPKEY);
    System.out.println("4、进行3-DES解密后的内容: " + reValue2);
  }
}
