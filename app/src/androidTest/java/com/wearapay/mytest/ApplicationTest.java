package com.wearapay.mytest;

import android.app.Application;
import android.test.ApplicationTestCase;
import com.wearapay.mytest.util.Cn2py;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
  public ApplicationTest() {
    super(Application.class);
  }

  public void testJson(){
    String json = "{\"data\": null,\"errors\": [ {\"code\": \"error.missing.field\",\"message\": \"APP_KEY/APP_TOKEN/USER_TOKEN\"}],\"status\": \"error\"}";
    System.out.println(json);
    JSONObject jsonObject = null;
    try {
      jsonObject = new JSONObject(json);
      Object data = jsonObject.get("data");
      System.out.print(data == JSONObject.NULL);
      System.out.println(1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void testPinYin(){
    String pinyin = Cn2py.getPinyin("$&^%*^*(&*");
    System.out.println("------------------"+pinyin);
  }
}