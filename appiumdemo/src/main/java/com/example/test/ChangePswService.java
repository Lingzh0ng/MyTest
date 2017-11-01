package com.example.test;

import com.example.AdbUtils;
import com.example.ZytSolfHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;

/**
 * Service of charging. Created by simon on 2017/2/8.
 */
public class ChangePswService extends BaseService {

  public ChargeResult chargePsw(AndroidDriver<MobileElement> wd, String cardNo, String oldPsw,
      String newPsw) {
    if (sHeartbeatThread != null && sHeartbeatThread.isAlive()) {
      sHeartbeatThread.interrupt();
      sHeartbeatThread = null;
    }

    //log.debug("[s]chargePsw {} {} {}", cardNo, oldPsw, newPsw);

    boolean isCompleted = false;

    if (wd == null) {
      System.out.println("Initialize AppiumClient failed");
      isCompleted = false;
      return ChargeResult.FAIL;
    }
    try {
      boolean isHomeActivity = wd.currentActivity().contains("HomeActivity");
      if (isHomeActivity) { //首页
        System.out.println("page on main");
      } else {//充值界面 - > 首页
        System.out.println("page on recharge");

        while (!isHomeActivity) {
          wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
          MobileElement backIcon1 = findElementByID(wd, "iv_left");
          System.out.println("back");
          backIcon1.click();
          System.out.println("sleep ...");
          Thread.sleep(200L);
          System.out.println("currentActivity:" + wd.currentActivity());
          isHomeActivity = wd.currentActivity().contains("HomeActivity");
        }
      }

      wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      System.out.println("find btnChargeConfirm for 卡服务");
      WebElement btnChargeConfirm = findElementByText(wd, "卡服务");
      if (btnChargeConfirm == null) {
        return ChargeResult.FAIL;
      }
      btnChargeConfirm.click();
      wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      System.out.println("find btnCardPsw for 卡密码修改");
      WebElement btnCardPsw = findElementByText(wd, "卡密码修改");
      if (btnCardPsw == null) {
        return ChargeResult.FAIL;
      }
      btnCardPsw.click();

      System.out.println("currentActivity:" + wd.currentActivity());

      //判断是否到修改密码界面
      wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      System.out.println("find btnConfirm for 确认");
      WebElement btnConfirm = findElementByText(wd, "确认");
      if (btnConfirm == null) {
        return ChargeResult.FAIL;
      }

      List<MobileElement> psweds = wd.findElementsByClassName("android.widget.EditText");
      if (psweds == null || psweds.size() != 4) {
        return ChargeResult.FAIL;
      }

      for (int i = 0; i < psweds.size(); i++) {
        if (i == 0) {
          MobileElement mobileElement = psweds.get(i);
          mobileElement.click();
          Thread.sleep(300L);
          //mobileElement.setValue(cardNo);

          for (int j = 0; j < cardNo.length(); j = j+ 4) {
            AdbUtils.runProcess("adb", "shell", "input", "text", cardNo.substring(j, j + 4));
            //Thread.sleep(100L);
          }
        } else {
          String psw = oldPsw;
          if (i == 2 || i == 3) {
            psw = newPsw;
          }
          MobileElement mobileElement = psweds.get(i);
          mobileElement.click();
          Thread.sleep(1000L);
          System.out.println("onclick:" + i);
          for (int j = 0; j < psw.length(); j++) {
            char charAt = psw.charAt(j);
            ZytSolfHelper.Point point = ZytSolfHelper.zytNumberMap.get(charAt + "");
            tap(wd, point);
            System.out.println(charAt + " : " + point);
            Thread.sleep(100L);
          }
          ZytSolfHelper.Point point = ZytSolfHelper.zytNumberMap.get("hide");
          tap(wd, point);
          System.out.println("hide" + " : " + point);
          Thread.sleep(300L);
        }
      }

      //wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      //System.out.println("find btnConfirm for 确认");
      //btnConfirm = wd.findElementByAndroidUIAutomator(ByName("确认"));
      //if (btnConfirm == null) {
      //  return ChargeResult.FAIL;
      //}
      btnConfirm.click();

      ////final WebDriverWait wait = new WebDriverWait(driver, 3);
      ////
      ////AndroidElement toast = (AndroidElement) wait.until(
      ////    ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text, '密码')]")));
      //List<AndroidElement> elements = driver.findElements(By.xpath(".//*[contains(@text, '密码')]"));
      //
      //for (int i = 0; i < elements.size(); i++) {
      //  AndroidElement toast = elements.get(i);
      //  System.out.println(toast.getText()
      //      + "   "
      //      + toast.getAttribute("name")
      //      + "   "
      //      + toast.toString()
      //      + "  id:"
      //      + toast.getId()
      //      + "  map:"
      //      + toast.toJson());
      //}

      //Thread.sleep(500L);
      ////截图
      //String toastMsg = VerifyCodeService.requestVerifyCode("8888", 15000L, "b");
      //System.out.println("toastMsg:" + toastMsg);
      //long time = System.currentTimeMillis();
      //System.out.println("screenshot:" + dateFormat.format(time));
      //File screenshot = ScreenshotUtils.screenshot(wd);
      //System.out.println("cutImage:" + dateFormat.format(System.currentTimeMillis()));
      //File file = ScreenshotUtils.cutImage(screenshot);
      //System.out.println("recognizeText:" + dateFormat.format(System.currentTimeMillis()));
      //String s = ScreenshotUtils.recognizeText(file);
      //System.out.println("ok:" + dateFormat.format(System.currentTimeMillis()));
      //System.out.println((System.currentTimeMillis() - time) / 1000 / 60);
      //System.out.println(s);

      String toastMsg = "";
      //截图
      //String toastMsg = VerifyCodeService.requestVerifyCode(mobileDevice, 20000L, "b");
      try {
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println("finding toast ...");
        WebElement toastElement = findToastElement(wd);
        toastMsg = toastElement.getText();
      } catch (Exception e) {
        System.out.println("not find toast");
      }
      System.out.println("toastMsg:" + toastMsg);
      if (!(toastMsg == null || toastMsg.length() == 0)) {
        if (toastMsg.equals("卡密码修改成功")) {
          System.out.println("find toast by success");
          isCompleted = true;
          return ChargeResult.SUCCESS;
        }
        if (toastMsg.equals("卡密码不正确")) {
          return ChargeResult.FAIL_OLD_PWD_ERROR;
        }
        if (toastMsg.equals("累计密码错误次数超限")) {
          return ChargeResult.FAIL_PWD_MAX_ERROR;
        }
        if (toastMsg.contains("当日密码错误次数超限")) {
          return ChargeResult.FAIL_PWD_MAX_ERROR;
        }
      }
      wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      System.out.println("find btnMainPage 首页");
      WebElement btnMainPage = findElementByText(wd, "首页");
      if (btnMainPage == null) {
        return ChargeResult.FAIL;
      }
      btnMainPage.click();
      isCompleted = true;
      return ChargeResult.SUCCESS;
    } catch (Exception e) {
      System.out.println(e.getMessage() + e);
      return ChargeResult.FAIL;
    } finally {
      System.out.println("Return device");

      final AndroidDriver<MobileElement> finalWd = wd;
      if (isCompleted) {
        sHeartbeatThread = new Thread(new Runnable() {
          @Override public void run() {
            if (finalWd == null) {
              return;
            }
            System.out.println("Heartbeat thread is started");
            while (sHeartbeatThread != null && !sHeartbeatThread.isInterrupted()) {
              try {
                if (outTime.get() <= 12300000) {
                  Thread.sleep(sleepTime);
                  outTime.set(outTime.get() + sleepTime);
                  System.out.println("outTime : " + (outTime.get() / 1000f / 60f) + "min");
                } else {
                  System.out.println("login will outTime ,So close App and wait for relogin");
                  closeApp(finalWd);
                  sHeartbeatThread.interrupt();
                  sHeartbeatThread = null;
                  break;
                }
              } catch (InterruptedException e) {
                System.out.println("New request is coming, interrupt heartbeat thread");
                break;
              }

              try {
                System.out.println("Click on the main page");
                finalWd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                System.out.println("find btnCardServerForConfirm 中银通支付 icon");
                WebElement btnZTYIcon = findElementByID(finalWd, "iv_center");
                btnZTYIcon.click();
              } catch (Exception e) {
                System.out.println("Fail to click on main page" + e);
                closeApp(finalWd);
                sHeartbeatThread.interrupt();
                sHeartbeatThread = null;
                break;
              }
            }
          }
        });
        sHeartbeatThread.start();
      } else {
        System.out.println("changePsw process is not completed.");
        closeApp(wd);
      }
    }
  }
}
