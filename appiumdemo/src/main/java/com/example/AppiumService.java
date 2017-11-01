package com.example;

import com.example.test.ChangePswService;
import com.example.test.ChargeResult;
import com.example.test.ChargeService;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppiumService {
  public static AndroidDriver driver;
  //private Log log = new Log();
  ChangePswService changePswService = new ChangePswService();
  ChargeService chargeService = new ChargeService();

  private static String zytCard = "6200480310000020";
  private static String zytCard2 = "6200480320200015";
  private static String chargeCard = "6212261001042526647";
  private static String ycCard = "6200485120388705";
  private static String yzCard = "6200485120388556";
  private Thread sHeartbeatThread;
  private TouchAction touchAction;

  public void setUp() throws Exception {
    File app = new File("C:\\Users\\Administrator\\Downloads\\cn.zyt.mobile_1.0.4_5.apk");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("device", "Android");
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
    //capabilities.setCapability(CapabilityType.VERSION, "6.1");
    capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
    capabilities.setCapability("deviceName", "9017e964");
    capabilities.setCapability("appium-version", "1.0");
    capabilities.setCapability("platformName", "Android");
    //capabilities.setCapability("app", app.getAbsolutePath());
    capabilities.setCapability("appPackage", "cn.zyt.mobile");
    capabilities.setCapability("appActivity", "com.csii.iap.ui.SplashActivity");
    //capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"----");
    //capabilities.setCapability("autoLaunch", true);
    capabilities.setCapability("sessionOverride", true);
    capabilities.setCapability("noReset", true);
    //capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,超时时间);
    capabilities.setCapability(AndroidMobileCapabilityType.RECREATE_CHROME_DRIVER_SESSIONS, true);
    //capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
    //    AutomationName.ANDROID_UIAUTOMATOR2);
    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    touchAction = new TouchAction(driver);
    //touchAction.
    //touchAction.press(0,0).waitAction(Duration.of(500,TimeUnit.MILLISECONDS))
  }

  public boolean work(String type) throws Exception {
    ChargeResult charge = null;
    if (driver == null) {
      setUp();
      //driver.launchApp();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

      WebElement element2 = driver.findElementByAndroidUIAutomator(ByName("充值"));

      System.out.println(element2.getText());
      //WebElement element = element2.findElement(By.xpath("//.."));
      //System.out.println(element.getClass());

      touchAction.press(element2.getLocation().x, element2.getLocation().y)
          .waitAction(Duration.ofMillis(500))
          .moveTo(element2.getLocation().x, element2.getLocation().y + 500)
          //.waitAction(Duration.ofMillis(500))
          .release()
          .perform();

      //JavascriptExecutor js =  driver;
      //HashMap<String, Double> swipeObject = new HashMap<String, Double>();
      //swipeObject.put("startX", 100d);
      //swipeObject.put("startY", 100d);
      //swipeObject.put("endX", 500d);
      //swipeObject.put("endY", 100d);
      //swipeObject.put("duration", 5d);
      ////swipeObject.put("element", Double.valueOf(((RemoteWebElement) element).getId()));
      //js.executeScript("mobile: swipe", swipeObject);

      //driver.getScreenshotAs()
    } else {
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

      WebElement element2 = driver.findElementByAndroidUIAutomator(ByName("充值"));

      System.out.println(element2.getText());
      //WebElement element = element2.findElement(By.xpath("//.."));
      //System.out.println(element.getClass());

      ScreenshotUtils.screenshot(element2);

      //int y = 500;
      //if (type.length() == 1) {
      //  y = -500;
      //}

      //touchAction.press(element2.getLocation().x, element2.getLocation().y)
      //    .waitAction(Duration.ofMillis(500))
      //    .moveTo(element2.getLocation().x, element2.getLocation().y + y)
      //    //.waitAction(Duration.ofMillis(500))
      //    .release()
      //    .perform();

      //JavascriptExecutor js =  driver;
      //HashMap<String, Double> swipeObject = new HashMap<String, Double>();
      //swipeObject.put("startX", 700d);
      //swipeObject.put("startY", 300d);
      //swipeObject.put("endX", 700d);
      //swipeObject.put("endY", 700d);
      //swipeObject.put("duration", 5d);
      ////swipeObject.put("element", Double.valueOf(((RemoteWebElement) element).getId()));
      //js.executeScript("mobile: swipe", swipeObject);
    }
    //  if (!login()) {
    //    return false;
    //  }
    //  if ("1".equals(type)) { //充值
    //    charge = chargeService.charge(driver, zytCard, 1000L);
    //  } else if ("2".equals(type)) {
    //    String oldPsw;
    //    String newPsw;
    //    if (type.contains("5")) {
    //      oldPsw = "540101";
    //      newPsw = "010203";
    //    } else {
    //      oldPsw = "010203";
    //      newPsw = "540101";
    //    }
    //    charge = changePswService.chargePsw(driver, yzCard, oldPsw, newPsw);
    //  } else if ("3".equals(type)) {
    //    fixInfo(driver);
    //    charge = ChargeResult.SUCCESS;
    //  }
    //} else {
    //  if ("1".equals(type)) { //充值
    //    charge = chargeService.charge(driver, yzCard, 1000L);
    //  } else if ("2".equals(type)) {
    //    String oldPsw;
    //    String newPsw;
    //    if (type.contains("5")) {
    //      oldPsw = "540101";
    //      newPsw = "010203";
    //    } else {
    //      oldPsw = "010203";
    //      newPsw = "540101";
    //    }
    //    charge = changePswService.chargePsw(driver, yzCard, oldPsw, newPsw);
    //  } else if ("3".equals(type)) {
    //    fixInfo(driver);
    //    charge = ChargeResult.SUCCESS;
    //  }
    //}
    //if (charge != null) {
    //  if (charge == ChargeResult.SUCCESS) {
    //    return true;
    //  } else {
    //    quit();
    //    return false;
    //  }

    //
    ////driver.pressKeyCode(KEYCODE_BACK);
    //quit();
    return false;
  }

  public void quit() {
    driver = null;
  }

  public void tearDown() throws Exception {
    driver.quit();
  }

  private WebElement findElement(By by) {
    return driver.findElement(by);
  }

  private void click(WebElement webElement) {
    touchAction.tap(webElement).perform();
  }

  public boolean fixInfo(AndroidDriver driver) throws InterruptedException {//id/tv_user_more
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement tvCheck = driver.findElementByAndroidUIAutomator(ByName("我的"));
    System.out.println("by 我的" + tvCheck);
    if (tvCheck == null) {
      return false;
    }
    tvCheck.click();

    //AndroidElement

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement tv_user_more = driver.findElementByAndroidUIAutomator(ByName("未实名认证"));
    System.out.println("by 未实名认证" + tv_user_more);
    if (tv_user_more == null) {
      return false;
    }
    tv_user_more.click();

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement input = driver.findElementByAccessibilityId("请输入兴趣");
    System.out.println("by 请输入兴趣" + input);
    if (input == null) {
      return false;
    }
    input.click();

    AdbUtils.runProcess("adb", "shell", "input", "text", "12344");
    Thread.sleep(1000L);
    ZytSolfHelper.Point point = ZytSolfHelper.zytOhterMap.get("btn_cancel");
    new TouchAction(driver).tap(point.X, point.Y).perform();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    //WebElement btnSave = driver.findElementByAndroidUIAutomator(ByName("保存"));
    WebElement btnSave = driver.findElementByAccessibilityId("保存");
    //driver.f
    System.out.println("by 保存" + btnSave);
    if (btnSave == null) {
      return false;
    }
    btnSave.click();

    //driver.
    //Thread.sleep(500);
    ////截图
    //String toastMsg = VerifyCodeService.requestVerifyCode("8888", 15000L, "b");
    //System.out.println("toastMsg:" + toastMsg);

    //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    //WebElement toast = driver.findElement(By.name("信息修改成功"));
    //System.out.println("by 信息修改成功" + btnSave);

    //findToast(driver, "信息修改成功");

    //driver.executeScript("env.automationName = 'UIAutomator2'");
    //
    //org.openqa.selenium.support.ui.ExpectedCondition<WebElement> webElementExpectedCondition =
    //    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@text, '信息修改成功')]"));
    ////webElementExpectedCondition.apply()
    //WebElement ac = wait.until(new ExpectedCondition<WebElement>() {
    //  @Nullable @Override public WebElement apply(@Nullable WebDriver input) {
    //    return null;
    //  }
    //});
    final WebDriverWait wait = new WebDriverWait(driver, 3);

    AndroidElement toast = (AndroidElement) wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text, '成功')]")));

    System.out.println(toast.getText()
        + "   "
        + toast.getAttribute("name")
        + "   "
        + toast.toString()
        + "  id:"
        + toast.getId()
        + "  map:"
        + toast.toJson());

    return false;
  }

  private void findToast(AndroidDriver driver, String toast) {
    try {
      final WebDriverWait wait = new WebDriverWait(driver, 2);
      Assert.assertNotNull(wait.until(ExpectedConditions.presenceOfElementLocated(
          By.xpath(".//*[contains(@text,'" + toast + "')]"))));
      System.out.println("找到了toast");
    } catch (Exception e) {
      throw new AssertionError("找不到" + toast);
    }
  }

  public void clickElement(WebElement element) {

    touchAction.tap(element).perform();
  }

  private boolean checkElement(MobileElement element) {
    return element == null;
  }

  public boolean check() throws Exception {
    //interruptHeartbeatThread();
    driver.launchApp();

    driver.unlockDevice();

    if (!login()) {
      return false;
    }

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement tvCheck = driver.findElement(By.name("查询"));
    System.out.println("by 查询" + tvCheck);
    if (tvCheck == null) {
      return false;
    }
    tvCheck.click();

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    MobileElement edZytCard =
        (MobileElement) driver.findElementByClassName("android.widget.EditText");
    if (edZytCard == null) {
      return false;
    }
    edZytCard.click();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebElement btnNext = driver.findElement(By.name("下一步"));
    if (btnNext == null) {
      return false;
    }
    btnNext.click();

    Thread.sleep(1000);

    String psw = "010203";
    for (int j = 0; j < psw.length(); j++) {
      char charAt = psw.charAt(j);
      ZytSolfHelper.Point point = ZytSolfHelper.zytNumberMap.get(charAt + "");
      new TouchAction(driver).tap(point.X, point.Y).perform();
      System.out.println(charAt + " : " + point);
      Thread.sleep(100L);
    }
    ZytSolfHelper.Point point = ZytSolfHelper.zytNumberMap.get("hide");
    new TouchAction(driver).tap(point.X, point.Y).perform();
    //driver.tap(1, point.X, point.Y, 0);
    System.out.println("hide" + " : " + point);

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebElement yes = driver.findElement(By.name("确定"));
    if (yes == null) {
      return false;
    }
    yes.click();

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    ////余额
    //String elementByClassNameWithBalance = getElementByClassNameWithBalance(driver);
    //System.out.println("balance : " + elementByClassNameWithBalance);

    WebElement btnCharge = driver.findElement(By.name("充值"));
    if (btnCharge == null) {
      return false;
    }
    btnCharge.click();

    if (!chargeCore()) {
      return false;
    }

    //startHeartbeatThread();
    return true;
  }

  private boolean chargeCore() throws InterruptedException {
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement btnChargeConfirm = driver.findElement(By.name("确认充值"));
    if (btnChargeConfirm == null) {
      return false;
    }
    //已经到充值界面了

    List<WebElement> eds = driver.findElementsByClassName("android.widget.EditText");
    if (eds == null || eds.size() != 3) {
      return false;
    }
    for (int i = 0; i < eds.size(); i++) {
      System.out.println(eds.get(i).getTagName());
      eds.get(i).click();
      if (i == 0) {
        AdbUtils.runProcess("adb", "shell", "input", "text", zytCard2);
        Thread.sleep(2000);//会请求余额 睡一会等待
      } else if (i == 1) {
        AdbUtils.runProcess("adb", "shell", "input", "text", chargeCard);
      } else {
        AdbUtils.runProcess("adb", "shell", "input", "text", "1.00");
      }
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    List<MobileElement> pageType = driver.findElementsByClassName("android.widget.EditText");
    if (pageType == null) { //首页
      System.out.println("page on main");
    } else {//充值界面
      System.out.println("page on recharge");
      System.out.println("back 1");
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      WebElement backIcon1 = driver.findElement(By.id("iv_left"));
      backIcon1.click();
      Thread.sleep(100L);
      backIcon1.click();
      System.out.println("sleep 5s");
      Thread.sleep(5000L);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      System.out.println("back 2");
      WebElement backIcon2 = driver.findElement(By.id("iv_left"));
      backIcon2.click();

      recharge();
    }

    if (true) return true;

    btnChargeConfirm.click();//点击确认充值

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement btnVerifyCode = driver.findElement(By.name("获取验证码"));
    if (btnVerifyCode == null) {
      return false;
    }//已经到获取验证码界面
    btnVerifyCode.click();
    //获取验证码
    String verifyCode = VerifyCodeService.requestVerifyCode("8888", 15000L, "a");

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebElement edVerifyCode = driver.findElement(By.name("请输入验证码"));
    if (edVerifyCode == null) {
      return false;
    }
    edVerifyCode.click();
    AdbUtils.runProcess("adb", "shell", "input", "text", verifyCode);

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebElement btnConfirmCharge = driver.findElement(By.name("确认支付"));
    if (btnConfirmCharge == null) {
      return false;
    }
    btnConfirmCharge.click();

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement btnCompleted = driver.findElement(By.name("完成操作"));
    if (btnCompleted == null) {
      return false;
    }
    btnCompleted.click();

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    btnConfirmCharge = driver.findElement(By.name("确认充值"));
    if (btnConfirmCharge == null) {
      return false;
    }
    return true;
  }

  private void interruptHeartbeatThread() {
    if (sHeartbeatThread != null && sHeartbeatThread.isAlive()) {
      sHeartbeatThread.interrupt();
      sHeartbeatThread = null;
    }
  }

  private void startHeartbeatThread() {
    sHeartbeatThread = new Thread(new Runnable() {
      @Override public void run() {
        if (driver == null) {
          return;
        }
        System.out.println("Heartbeat thread is started");
        while (!sHeartbeatThread.isInterrupted()) {
          try {
            Thread.sleep(30000);
          } catch (InterruptedException e) {
            System.out.println("New request is coming, interrupt heartbeat thread");
            break;
          }
          try {
            System.out.println("Click on the page");
            List<WebElement> eds = driver.findElementsByClassName("android.widget.EditText");
            eds.get(0).click();
          } catch (Exception e) {
            System.out.println("Fail to click on recharge page");
          }
        }
      }
    });
    sHeartbeatThread.start();
  }

  public boolean change() throws Exception {
    driver.launchApp();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    if (!login()) {
      return false;
    }
    return recharge();
  }

  private boolean recharge() throws InterruptedException {
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebElement btnCardServer = driver.findElement(By.name("卡服务"));
    System.out.println("by 卡服务" + btnCardServer);
    //MobileElement btnCardServer =
    //    getElementByClassNameWithText(driver, "android.widget.TextView", "卡服务");
    if (btnCardServer == null) {
      return false;
    }
    btnCardServer.click();

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebElement btnCardPsw = driver.findElement(By.name("卡密码修改"));
    System.out.println("by 卡密码修改" + btnCardPsw);
    if (btnCardPsw == null) {
      return false;
    }
    btnCardPsw.click();

    //判断是否到修改密码界面
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement btnConfirm = driver.findElement(By.name("确认"));
    System.out.println("by 确认" + btnConfirm);
    if (btnConfirm == null) {
      return false;
    }

    List<MobileElement> psweds = driver.findElementsByClassName("android.widget.EditText");
    if (psweds == null || psweds.size() != 4) {
      return false;
    }
    String oldPsw = "540101";
    String newPsw = "010203";
    for (int i = 0; i < psweds.size(); i++) {
      if (i == 0) {
        MobileElement mobileElement = psweds.get(i);
        mobileElement.click();
        AdbUtils.runProcess("adb", "shell", "input", "text", "6200485120388556");
        Thread.sleep(300L);
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
          new TouchAction(driver).tap(point.X, point.Y).perform();
          System.out.println(charAt + " : " + point);
          Thread.sleep(300L);
        }
        ZytSolfHelper.Point point = ZytSolfHelper.zytNumberMap.get("hide");
        new TouchAction(driver).tap(point.X, point.Y).perform();
        System.out.println("hide" + " : " + point);
        Thread.sleep(300L);
      }
    }

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebElement pasButton = driver.findElementByClassName("android.widget.Button");
    if (pasButton == null) {
      return false;
    }
    pasButton.click();

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement btnMain = driver.findElement(By.name("首页"));
    System.out.println("by 首页" + btnMain);
    btnMain.click();

    for (int i = 0; i < 10; i++) {
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      System.out.println("find btnZTYIcon 中银通支付 icon");
      WebElement btnZTYIcon = driver.findElement(By.id("iv_center"));
      btnZTYIcon.click();
      Thread.sleep(2000L);
    }
    return btnMain != null;
  }

  private String ByName(String name) {
    return "text(\"" + name + "\")";
  }

  private boolean login() throws Exception {
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    //Thread.sleep(10000);
    WebElement forLogin = driver.findElementByAndroidUIAutomator(ByName("DIY卡"));
    Thread.sleep(3000L);
    System.out.println("by DIY卡" + forLogin);
    System.out.println(driver);
    //forLogin.setParent(driver);
    if (forLogin == null) {
      return false;
    } else {
      System.out.println("by click");
      clickElement(forLogin);
      System.out.println("by clicked");
    }

    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    List<MobileElement> eds = driver.findElementsByClassName("android.widget.EditText");
    if (eds.size() != 2) {
      return false;
    }
    for (int i = 0; i < eds.size(); i++) {
      System.out.println(eds.size());
      if (i == 0) {
        MobileElement mobileElement = eds.get(i);
        clickElement(mobileElement);
        AdbUtils.runProcess("adb", "shell", "input", "text", "13824318176");
      } else {
        MobileElement mobileElement = eds.get(i);
        clickElement(mobileElement);
        Thread.sleep(1000L);
        System.out.println("else");
        String psw = "paypos2015";
        boolean type = true;
        for (int j = 0; j < psw.length(); j++) {
          char charAt = psw.charAt(j);
          if (Character.isDigit(charAt) && type) {
            ZytSolfHelper.Point point = ZytSolfHelper.zytMap.get("number");
            touchAction.tap(point.X, point.Y).perform();
            System.out.println("number" + " : " + point);
            Thread.sleep(100L);
            type = false;
          }
          ZytSolfHelper.Point point = ZytSolfHelper.zytMap.get(charAt + "");
          touchAction.tap(point.X, point.Y).perform();
          System.out.println(charAt + " : " + point);
          Thread.sleep(100L);
        }

        ZytSolfHelper.Point point = ZytSolfHelper.zytMap.get("hide");
        touchAction.tap(point.X, point.Y).perform();
        System.out.println("hide" + " : " + point);
        Thread.sleep(100L);
      }
    }
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement btnLogin = driver.findElementByAndroidUIAutomator(ByName("登录"));
    if (btnLogin == null) {
      return false;
    }
    clickElement(btnLogin);

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    System.out.println("find btnMainPage 首页");
    WebElement btnMainPage = driver.findElementByAndroidUIAutomator(ByName("首页"));
    if (btnMainPage == null) {
      return false;
    }
    clickElement(btnMainPage);
    return btnMainPage != null;
  }

  private MobileElement getElementByClassNameWithText(AppiumDriver wd, String className,
      String text) throws Exception {
    System.out.println("getElementByClassNameWithText:" + text);
    if (wd != null) {
      try {
        List<MobileElement> elements = wd.findElementsByClassName(className);
        for (MobileElement me : elements) {
          System.out.println("MobileElement" + me.getText());
          if (me != null && StringUtils.equalsIgnoreCase(me.getText(), text)) {
            return me;
          }
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    return null;
  }

  private MobileElement getElementByClassNameWithContentDesc(AppiumDriver wd, String className,
      String text) throws Exception {
    System.out.println("getElementByClassNameWithText:" + text);
    if (wd != null) {
      try {
        List<MobileElement> elements = wd.findElementsByClassName(className);
        for (MobileElement me : elements) {
          System.out.println("MobileElement" + me.getAttribute("name"));
          if (me != null && StringUtils.equalsIgnoreCase(me.getAttribute("name"), text)) {
            return me;
          }
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return null;
  }

  private String getElementByClassNameWithBalance(AppiumDriver wd) throws Exception {
    String text = null;
    if (wd != null) {
      try {
        WebElement elementById = wd.findElementById("cn.zyt.mobile:id/web_container");
        List<WebElement> elements = elementById.findElements(By.className("android.view.View"));
        System.out.println(elements.size());
        WebElement webElement = elements.get(4);
        for (int i = 0; i < elements.size(); i++) {
          System.out.println(elements.get(i).getAttribute("name") + "  position:" + i);
        }
        text = webElement.getAttribute("name");
        System.out.println(webElement.toString());
        System.out.println(webElement.getTagName());
        System.out.println(text);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    return text;
  }

  public void destory() {
    if (driver != null) {
      driver.closeApp();
      driver.quit();
      System.out.println("close and quit");
      driver = null;
    }
  }
}