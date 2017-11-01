package com.example.test;

import com.example.AdbUtils;
import com.example.AppiumService;
import com.example.ZytSolfHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by lyz on 2017/8/25.
 */

public abstract class BaseService {

  protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static Thread sHeartbeatThread;
  public static String zytCard = "6200480310000020";
  public static String zytCard2 = "6200480320200015";
  public static String chargeCard = "6212261001042526647";

  public static String ycCard = "6200485120388705";
  public static String yzCard = "6200485120388556";
  public static int sleepTime = 30000;
  public static int maxSleepTime = 20 * 2 * sleepTime;
  public static AtomicInteger outTime = new AtomicInteger(0);

  protected String ByName(String name) {
    return "text(\"" + name + "\")";
  }

  protected void closeApp(AndroidDriver<MobileElement> wd) {
    try {
      if (wd != null) {
        wd.closeApp();
        wd.quit();
        AppiumService.driver = null;
        System.out.println("closeApp.");
      }
    } catch (Throwable tw) {
      System.out.println(tw.getMessage() + tw);
    } finally {
      System.out.println("reset outTime : 0.");
      outTime.set(0);
    }
  }

  protected TouchAction tap(AndroidDriver driver, ZytSolfHelper.Point point) {
    TouchAction action = null;
    if (driver != null && point != null) {
      action = new TouchAction(driver).tap(point.X, point.Y).perform();
    }
    return action;
  }

  protected boolean login(AndroidDriver driver) throws Exception {
    System.out.println("find loginElement by DIY卡");
    WebElement forLogin = driver.findElement(By.name("DIY卡"));//TODO
    if (forLogin == null) {
      return false;
    }
    forLogin.click();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    List<MobileElement> eds = driver.findElementsByClassName("android.widget.EditText");
    System.out.println("find login EditText size : " + (eds != null ? eds.size() : 0));
    if (eds == null || eds.size() != 2) {
      System.out.println("Cannot find EditText");
      return false;
    }
    for (int i = 0; i < eds.size(); i++) {
      System.out.println(eds.size());
      if (i == 0) {
        MobileElement mobileElement = eds.get(i);
        mobileElement.click();
        AdbUtils.runProcess("adb", "shell", "input", "text", "18001910852");//TODO
        System.out.println("input account {}" + "18001910852");
      } else {
        MobileElement mobileElement = eds.get(i);
        mobileElement.click();
        Thread.sleep(1000L);
        String psw = "lyz540101";//TODO
        boolean type = true;
        for (int j = 0; j < psw.length(); j++) {
          char charAt = psw.charAt(j);
          if (Character.isDigit(charAt) && type) {
            ZytSolfHelper.Point point = ZytSolfHelper.zytMap.get("number");
            tap(driver, point);
            Thread.sleep(100L);
            type = false;
          }
          ZytSolfHelper.Point point = ZytSolfHelper.zytMap.get(charAt + "");
          tap(driver, point);
          System.out.println(charAt + " : " + point);
          Thread.sleep(100L);
        }
        System.out.println("input psw {}" + psw);

        ZytSolfHelper.Point point = ZytSolfHelper.zytMap.get("hide");
        tap(driver, point);
        System.out.println("hide softInput");
        Thread.sleep(100L);
      }
    }
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    System.out.println("find btnLogin");
    WebElement btnLogin = driver.findElementByClassName("android.widget.Button");
    if (btnLogin == null) {
      return false;
    }
    btnLogin.click();

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement tvCheck = driver.findElement(By.name("查询"));
    System.out.println("by 查询" + tvCheck);//TODO
    return tvCheck != null;
  }

  protected MobileElement getElementByClassNameWithText(AppiumDriver wd, String className,
      String text) {

    if (wd != null) {
      try {
        List<MobileElement> elements = wd.findElementsByClassName(className);

        for (MobileElement me : elements) {
          if (me != null && StringUtils.equalsIgnoreCase(me.getText(), text)) {
            return me;
          }
        }
      } catch (Exception e) {
        System.out.println(e.getMessage() + e);
      }
    }

    return null;
  }

  protected MobileElement findElementByXpath(AndroidDriver<MobileElement> wd, String viewType,
      String desc) {
    return wd.findElement(By.xpath("//" + viewType + "[@content-desc=\"" + desc + "\"]"));
  }
  protected MobileElement findEditTextElementByXpath(AndroidDriver<MobileElement> wd, String desc) {
    return findElementByXpath(wd, "android.widget.EditText", desc);
  }

  protected MobileElement findViewElementByXpath(AndroidDriver<MobileElement> wd, String desc) {
    return findElementByXpath(wd, "android.view.View", desc);
  }

  protected MobileElement findButtonElementByXpath(AndroidDriver<MobileElement> wd, String desc) {
    return findElementByXpath(wd, "android.widget.Button", desc);
  }

  protected MobileElement findElementByText(AndroidDriver<MobileElement> wd, String text) {
    return wd.findElementByAndroidUIAutomator(ByName(text));
  }

  protected MobileElement findElementByID(AndroidDriver<MobileElement> wd, String id) {
    return wd.findElement(By.id(id));
  }

  protected MobileElement findToastElement(AndroidDriver<MobileElement> wd) {
    return wd.findElement(By.xpath("//android.widget.Toast[1]"));
  }
}
