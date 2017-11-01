package com.wearapay.tagprogressbar.test;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lyz on 2017/10/31.
 */

public class TestClass {
  @IntDef({ RED, BLUE, YELLOW }) @Retention(RetentionPolicy.SOURCE) public @interface LightColor {
  }

  public static final int RED = 1;
  public static final int BLUE = 2;
  public static final int YELLOW = 3;


  public void setColor(@LightColor int color) {
  }
}
