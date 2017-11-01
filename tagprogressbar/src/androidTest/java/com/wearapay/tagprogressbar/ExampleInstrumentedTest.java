package com.wearapay.tagprogressbar;

import android.support.test.runner.AndroidJUnit4;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class) public class ExampleInstrumentedTest {
  @Test public void useAppContext() throws Exception {
    // Context of the app under test.
    conn();
  }

  public static String calPIN(String input) throws NoSuchAlgorithmException {
    byte[] md5 = getMD5(input);
    StringBuilder sb = new StringBuilder();
    for (int i = 2; i <= 12; i += 2) {
      sb.append("" + byte2short(md5[i]) % 10);
    }
    return sb.toString();
  }

  public static byte[] getMD5(String str) throws NoSuchAlgorithmException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(str.getBytes());
    return md5.digest();//加密
  }

  public static short byte2short(byte b) {
    if (b < 0) return (short) (256 + (short) b);
    return (short) b;
  }

  private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
  private static final String PREFIX = "--";
  private static final String LINE_END = "\r\n";
  private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

  public  void conn() {
    String url = "http://store.hnxind.com/demos/app/factory.php";
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("POST");
      connection.setUseCaches(false);
      connection.setRequestProperty("Charset", "UTF-8");
      connection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
      connection.setRequestProperty("enctype", "multipart/form-data");
      connection.setConnectTimeout(60000);
      //当文件不为空，把文件包装并且上传
      DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
      parms(dos, "action", "check_imformation");
      parms(dos, "user_id", "1");

      dos.flush();

      connection.connect();

      int responseCode = connection.getResponseCode();
      System.out.println("responseCode:" + responseCode);
      if (responseCode == 200) {
        System.out.println("success");
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          sb.append(line);
        }

        String rawResponse = sb.toString();
        System.out.println( "rawResponse = " + rawResponse);
        JSONObject jsonObject = new JSONObject(rawResponse);
        System.out.println(jsonObject.toString());
      } else {
        System.out.println("fail");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private  void parms(DataOutputStream dos, String key, String value) throws IOException {
    StringBuffer sb = new StringBuffer();

    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
    sb.append("Content-Disposition: form-data; name=\"")
        .append(key)
        .append("\"")
        .append(LINE_END)
        .append(LINE_END);
    sb.append(value).append(LINE_END);
    String params = sb.toString();
    dos.write(params.getBytes("UTF-8"));
  }
}
