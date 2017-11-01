package com.example;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.annotation.XmlAttribute;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 对应手机 Created by simon on 2017/2/8.
 */

public class MobileDevice {
  private String serialNo;
  private String desc;
  private String platform;
  private int verifyCodeServicePort;
  private String deviceName;

  private boolean isInitialized;
  private boolean isExecution;
  private AndroidDriver<MobileElement> androidDriver;

  public String getSerialNo() {
    return serialNo;
  }

  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @XmlAttribute
  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public int getVerifyCodeServicePort() {
    return verifyCodeServicePort;
  }

  public void setVerifyCodeServicePort(int vcp) {
    this.verifyCodeServicePort = vcp;
  }

  public void initialize(String appiumServer) throws MobileInitializationException {
    if (!initializeMobileDevice()) {
      throw new MobileInitializationException("Forward port failure.");
    }

    System.out.println("Device initialized.");

    if (!initializeAppium(appiumServer)) {
      throw new MobileInitializationException("Forward port failure.");
    }

    isInitialized = true;
    System.out.println("Appium client initialized.");
  }

  private boolean initializeAppium(String appiumServer) {
    String apkName = "";
    //String apkPath = ChargeService.class.getClassLoader()
    //    .getResource(apkName).getPath().substring(1);
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("appium-version", "1.0");
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("platformVersion", getPlatform());
    capabilities.setCapability("deviceName", getDeviceName());
    capabilities.setCapability("autoLaunch", false);
    //capabilities.setCapability("app", apkPath);
    capabilities.setCapability("appPackage", "com.zyt.wallet");
    try {
      androidDriver = new AndroidDriver<>(new URL(appiumServer), capabilities);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private boolean initializeMobileDevice() {
    AdbUtils.runProcess("adb", "shell", "am", "startservice",
        "com.wearapay.verifycode/com.wearapay.verifycode.VerifyCodeService");

    AdbUtils.runProcess("adb", "forward", "tcp:8888", "tcp:8888");

    setVerifyCodeServicePort(8888);
    return true;
  }

  @Override public String toString() {
    return "name: " + deviceName + ", version: " + getPlatform();
  }

  public boolean isInitialized() {
    return isInitialized;
  }

  public AndroidDriver<MobileElement> getAndroidDriver(String appiumServer)
      throws MobileInitializationException {
    if (androidDriver != null) {
      return androidDriver;
    }

    if (!initializeAppium(appiumServer)) {
      throw new MobileInitializationException("Initialize appium client failure.");
    }

    System.out.println("Appium client initialized.");
    return androidDriver;
  }

  public boolean isExecution() {
    return isExecution;
  }

  public void setIsExecution(boolean isExecution) {
    this.isExecution = isExecution;
  }

  public void destroyAppiumClient() {
    System.out.println("Destroy Appium client");

    androidDriver = null;
    isInitialized = false;
    isExecution = false;
    AdbUtils.runProcess("adb", "shell", "am", "force-stop",
        "com.wearapay.verifycode");

    AdbUtils.runProcess("adb", "forward", "--remove-all");
  }

  @XmlAttribute
  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }
}
