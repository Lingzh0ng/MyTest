package com.example.test;

import com.example.AdbUtils;
import com.example.VerifyCodeService;
import com.example.ZytSolfHelper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;

/**
 * Service of charging. Created by simon on 2017/2/8.
 */
public class ChargeService extends BaseService {
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public ChargeResult charge(AndroidDriver<MobileElement> wd, String cardNo, Long amount) {
    if (sHeartbeatThread != null && sHeartbeatThread.isAlive()) {
      sHeartbeatThread.interrupt();
      sHeartbeatThread = null;
    }

    boolean isCompleted = false;

    if (wd == null) {
      System.out.println("Initialize AppiumClient failed");
      isCompleted = false;
      return ChargeResult.FAIL;
    }
    try {
      System.out.println("currentActivity:" + wd.currentActivity());
      long l = System.currentTimeMillis();
      //System.out.println("start time:" +l);
      //List<MobileElement> pageType = wd.findElementsByClassName("android.widget.EditText");
      //System.out.println("end time:" + (System.currentTimeMillis() - l));
      if (wd.currentActivity().contains("HomeActivity")) { //首页
        System.out.println("currentActivity:" + wd.currentActivity());
        System.out.println("page on main");

        //wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //System.out.println("find tvCheck for 首页");
        //WebElement btnMainPage = findElementByText(wd, "首页");
        //System.out.println("by 首页" + btnMainPage);
        //if (btnMainPage == null) {
        //  return ChargeResult.FAIL;
        //}
        //btnMainPage.click();

        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println("find tvCheck for 查询");
        WebElement tvCheck = findElementByText(wd, "查询");
        System.out.println("by 查询" + tvCheck);
        if (tvCheck == null) {
          return ChargeResult.FAIL;
        }
        tvCheck.click();

        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        MobileElement edZytCard = wd.findElementByClassName("android.widget.EditText");
        System.out.println(" find edZytCard");
        if (edZytCard == null) {
          return ChargeResult.FAIL;
        }
        edZytCard.click();
        AdbUtils.runProcess("adb", "shell", "input", "text", yzCard);//TODO
        System.out.println("input zytCardNo");

        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println("find btnNext for 下一步");
        WebElement btnNext = findButtonElementByXpath(wd, "下一步");
        if (btnNext == null) {
          return ChargeResult.FAIL;
        }
        btnNext.click();

        Thread.sleep(2000);//等待键盘弹出

        String psw = "010203";//TODO
        for (int j = 0; j < psw.length(); j++) {
          char charAt = psw.charAt(j);
          ZytSolfHelper.Point point = ZytSolfHelper.zytNumberMap.get(charAt + "");
          tap(wd, point);
          System.out.println(charAt + " : " + point);
          Thread.sleep(100L);
        }
        System.out.println("input zytCardNo pwd");

        ZytSolfHelper.Point point = ZytSolfHelper.zytNumberMap.get("hide");
        tap(wd, point);
        System.out.println("hide softInput");

        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        System.out.println("find yes for 确定");
        WebElement yes = findElementByText(wd, "确定");
        if (yes == null) {
          return ChargeResult.FAIL;
        }
        yes.click();

        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("find btnCharge for 充值");
        WebElement btnCharge = findViewElementByXpath(wd, "充值");
        if (btnCharge == null) {
          return ChargeResult.FAIL;
        }
        btnCharge.click();
        System.out.println("充值界面");
      } else {//充值界面
        System.out.println("page on recharge");
      }

      wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      System.out.println("find btnChargeConfirm for 确认充值");
      WebElement btnChargeConfirm = findButtonElementByXpath(wd, "确认充值");
      if (btnChargeConfirm == null) {
        return ChargeResult.FAIL;
      }
      System.out.println("currentActivity:" + wd.currentActivity());
      System.out.println("开始充值...");

      List<MobileElement> eds = wd.findElementsByClassName("android.widget.EditText");
      System.out.println("find charge EditText size : " + (eds != null ? eds.size() : 0));
      if (eds == null || eds.size() != 3) {
        System.out.println("Cannot find EditText for 充值");
        return ChargeResult.FAIL;
      }
      for (int i = 0; i < eds.size(); i++) {
        eds.get(i).click();
        if (i == 0) {
          System.out.println("Found EditText : zytCardNo");
          AdbUtils.runProcess("adb", "shell", "input", "text", cardNo);//TODO!!
          System.out.println("input zytCardNo {}" + cardNo);
        } else if (i == 1) {
          Thread.sleep(2000);//会请求余额 睡一会等待
          System.out.println("Found EditText : chargeBankCard");
          AdbUtils.runProcess("adb", "shell", "input", "text", chargeCard);//TODO!!
        } else {
          System.out.println("Found EditText : amount");
          AdbUtils.runProcess("adb", "shell", "input", "text", String.valueOf(amount));//TODO!!
          System.out.println("input amount {}" + amount);
        }
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      }

      System.out.println("Input completed.");

      System.out.println("click btnChargeConfirm");

      isCompleted = true;
      if (btnChargeConfirm != null) {
        return ChargeResult.SUCCESS;
      }
      btnChargeConfirm.click();//点击确认充值

      wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      System.out.println("find btnVerifyCode for 获取验证码");

      WebElement btnVerifyCode = findButtonElementByXpath(wd, "获取验证码");
      if (btnVerifyCode == null) {
        return ChargeResult.FAIL;
      }//已经到获取验证码界面
      System.out.println("已经到获取验证码界面 点击获取验证码");
      btnVerifyCode.click();

      //获取验证码
      String verifyCode = VerifyCodeService.requestVerifyCode("", 15000L, "");

      // 无法获取验证码则充值失败。
      if (verifyCode == null || verifyCode.length() == 0) {
        System.out.println("Fail to get verify code...");
        return ChargeResult.FAIL_DEVICE_NEED_REBOOT;
      }
      System.out.println("requestVerifyCode success : {}" + verifyCode);

      wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      System.out.println("find edVerifyCode for 请输入验证码");
      WebElement edVerifyCode = findEditTextElementByXpath(wd, "请输入验证码");
      if (edVerifyCode == null) {
        return ChargeResult.FAIL;
      }
      edVerifyCode.click();
      AdbUtils.runProcess("adb", "shell", "input", "text", verifyCode);
      System.out.println("input verifyCode : {}" + verifyCode);

      wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      System.out.println("find btnConfirmCharge for 确认支付");
      WebElement btnConfirmCharge = findButtonElementByXpath(wd, "确认支付");
      if (btnConfirmCharge == null) {
        return ChargeResult.FAIL;
      }
      System.out.println("click btnConfirmCharge");
      btnConfirmCharge.click();

      wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      System.out.println("find btnCompleted for 完成操作");
      WebElement btnCompleted = findButtonElementByXpath(wd, "完成操作");
      if (btnCompleted == null) {
        return ChargeResult.FAIL;
      }
      System.out.println("click btnCompleted");
      btnCompleted.click();

      isCompleted = true;

      return ChargeResult.SUCCESS;
    } catch (Exception e) {
      System.out.println(e.getMessage() + e);
      e.printStackTrace();
      return ChargeResult.FAIL;
    } finally

    {
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
                  System.out.println("login will outTime ,so close App and relogin");
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
                System.out.println("Click on the page");
                List<MobileElement> eds =
                    finalWd.findElementsByClassName("android.widget.EditText");
                if (eds != null && eds.size() > 0) {
                  eds.get(0).click();
                } else {
                  closeApp(finalWd);
                  sHeartbeatThread.interrupt();
                  sHeartbeatThread = null;
                }
              } catch (Exception e) {
                System.out.println("Fail to click on recharge page" + e);
                closeApp(finalWd);
                sHeartbeatThread.interrupt();
                sHeartbeatThread = null;
              }
            }
          }
        });
        sHeartbeatThread.start();
      } else {
        System.out.println("Charge process is not completed.");
        closeApp(wd);
      }
    }
  }
}
