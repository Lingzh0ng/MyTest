package com.wearapay.ocrdemo;

import java.io.BufferedOutputStream;
import java.io.PrintStream;

/**
 * Created by lyz on 2017/8/30.
 */

public class ScreenUtils {

  public static void screenshot(String path) {
        /*Environment.getExternalStorageDirectory()*/
    Process process = null;
    try {
      process = Runtime.getRuntime().exec("su");
      PrintStream outputStream = null;
      try {
        outputStream = new PrintStream(new BufferedOutputStream(process.getOutputStream(), 8192));
        outputStream.println("screencap -p " + path);
        outputStream.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (outputStream != null) {
          outputStream.close();
        }
      }
      process.waitFor();
      //out(process);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (process != null) {
        process.destroy();
      }
    }
  }
}
