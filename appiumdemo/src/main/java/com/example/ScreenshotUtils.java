package com.example;

import io.appium.java_client.android.AndroidDriver;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

/**
 * Created by lyz on 2017/9/4.
 */

public class ScreenshotUtils {

  private static final String LANG_OPTION = "-l";
  private static final String EOL = System.getProperty("line.separator");
  private static final String tessPath = "D:\\Program Files (x86)\\Tesseract-OCR";

  public static File screenshot(WebElement element) {
    File file = new File("screenshot.png");
    if (file.exists()) {
      file.delete();
    }
    System.out.println(file.getAbsolutePath());
    File screenshotAs = element.getScreenshotAs(OutputType.FILE);

    try {
      FileUtils.copyFile(screenshotAs, file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file;
  }

  /**
   * 文件位置我防止在，项目同一路径
   */
  //private static String tessPath = new File("tesseract").getAbsolutePath();
  public static File screenshot(AndroidDriver driver) {
    File file = new File("screenshot.png");
    if (file.exists()) {
      file.delete();
    }
    System.out.println(file.getAbsolutePath());
    File screenshotAs = driver.getScreenshotAs(OutputType.FILE);

    try {
      FileUtils.copyFile(screenshotAs, file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file;
  }

  public static String recognizeText(File imageFile) throws Exception {
    /**
     * 设置输出文件的保存的文件目录
     */
    File outputFile = new File(imageFile.getParentFile(), "output");

    System.out.println(outputFile.getAbsolutePath());

    StringBuffer strB = new StringBuffer();
    List<String> cmd = new ArrayList<>();

    cmd.add(tessPath + "\\tesseract.exe");

    cmd.add("");
    cmd.add(outputFile.getName());
    cmd.add(LANG_OPTION);
    cmd.add("chi_sim");
    //cmd.add("eng");

    ProcessBuilder pb = new ProcessBuilder();
    /**
     *Sets this process builder's working directory.
     */
    pb.directory(imageFile.getParentFile());
    cmd.set(1, imageFile.getName());
    pb.command(cmd);
    pb.redirectErrorStream(true);
    Process process = pb.start();
    // tesseract.exe 1.jpg 1 -l chi_sim
    // Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
    /**
     * the exit value of the process. By convention, 0 indicates normal
     * termination.
     */
    //      System.out.println(cmd.toString());
    int w = process.waitFor();
    if (w == 0)// 0代表正常退出
    {
      BufferedReader in = new BufferedReader(
          new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
              "UTF-8"));
      String str;

      while ((str = in.readLine()) != null) {
        strB.append(str).append(EOL);
      }
      in.close();
    } else {
      String msg;
      switch (w) {
        case 1:
          msg = "Errors accessing files. There may be spaces in your image's filename.";
          break;
        case 29:
          msg = "Cannot recognize the image or its selected region.";
          break;
        case 31:
          msg = "Unsupported image format.";
          break;
        default:
          msg = "Errors occurred.";
      }
      throw new RuntimeException(msg);
    }
    new File(outputFile.getAbsolutePath() + ".txt").delete();
    return strB.toString().replaceAll("\\s*", "");
  }

      /*
     * 图片裁剪通用接口
     */

  public static File cutImage(File src) throws IOException {
    File outputFile =
        new File(src.getParentFile(), src.getName().split("\\.")[0] + "_cutImage.png");
    System.out.println(outputFile.getAbsolutePath());
    BufferedImage bufferedImg = ImageIO.read(src);
    int imgWidth = bufferedImg.getWidth();
    int imgHeight = bufferedImg.getHeight();
    int startHeight = (int) ((1620f / 1920f) * imgHeight);
    int endHeight = (int) ((1680f / 1920f) * imgHeight);

    System.out.println(
        "imgHeight:" + imgHeight + " startHeight:" + startHeight + " endHeight:" + endHeight);

    Iterator iterator = ImageIO.getImageReadersByFormatName("png");
    ImageReader reader = (ImageReader) iterator.next();
    InputStream in = new FileInputStream(src);
    ImageInputStream iis = ImageIO.createImageInputStream(in);

    reader.setInput(iis, true);
    ImageReadParam param = reader.getDefaultReadParam();
    Rectangle rect = new Rectangle(0, startHeight, imgWidth, endHeight - startHeight);
    param.setSourceRegion(rect);
    BufferedImage bi = reader.read(0, param);
    ImageIO.write(bi, "png", outputFile);
    in.close();
    iis.close();
    return outputFile;
  }
}
