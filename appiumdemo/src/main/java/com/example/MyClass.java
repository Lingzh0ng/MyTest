package com.example;

import java.util.Scanner;

public class MyClass {

  public static void main(String[] args) {

    AdbUtils.runProcess("adb", "shell", "am", "startservice",
        "com.wearapay.verifycode/com.wearapay.verifycode.VerifyCodeService");

    AdbUtils.runProcess("adb", "forward", "tcp:8888", "tcp:8888");

    AppiumService appiumServie = new AppiumService();
    while (true) {
      try {
        System.out.println("请输入1(充值) or 2(修改密码) or 3(修改个人信息):");
        Scanner scanner = new Scanner(System.in);

        String next = scanner.next();
        boolean work = appiumServie.work(next);
        System.out.println("result:" + work);
      } catch (Exception e) {
        e.printStackTrace();
        appiumServie.destory();
      }
    }

    //try {
    //  appiumServie.setUp();
    //  boolean check = appiumServie.check();
    //  System.out.println("change:" + check);
    //} catch (Exception e) {
    //  e.printStackTrace();
    //  appiumServie.destory();
    //}
    //appiumServie.check();
  }
}
