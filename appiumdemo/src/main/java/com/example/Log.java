package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lyz on 2017/8/11.
*/

public class Log {
  private static String file_path = "C:\\Users\\Administrator\\Desktop\\log";
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private  File file;
  private  FileOutputStream fileOutputStream;

  public Log() {
    file = new File(file_path,"log.txt");
    boolean exists = file.exists();
    //if (exists) {
    //  file.delete();
    //} else  {
    //  try {
    //    file.createNewFile();
    //  } catch (IOException e) {
    //    e.printStackTrace();
    //  }
    //}
    try {
      fileOutputStream = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void o(String str){
    synchronized (this) {
      try {
        String format = simpleDateFormat.format(new Date());
        str = format + str;
        byte[] bytes = str.getBytes("utf-8");
        fileOutputStream.write(bytes);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
