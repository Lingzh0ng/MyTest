package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 用于获取手机验证码的服务 Created by mike on 2017/2/10.
 */
public class VerifyCodeService {
  //private static Logger log = LoggerFactory.getLogger("com.wearapay.VerifyCodeService");

  public static String requestVerifyCode(final String port, long timeout, final String type) {
    final VerifyCode verifyCode = new VerifyCode();
    System.out.println("Wait for VerifyCode");
    Thread thread = new Thread(new Runnable() {
      @Override public void run() {
        BufferedReader reader = null;
        PrintWriter os = null;
        try {
          Socket socket = new Socket();
          System.out.println("Connecting VerifyCodeService by localhost:" + port);
          socket.connect(new InetSocketAddress("localhost", new Integer(port)));
          System.out.println("Connecting VerifyCodeService connect:" + port);
          reader = new BufferedReader(
              new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
          os = new PrintWriter(
              new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
          os.print(type + "\n");
          os.flush();

          System.out.println("Connecting VerifyCodeService reader:" + port);
          verifyCode.setValue(reader.readLine());
          System.out.println("Verify code is received...");
        } catch (IOException e) {
          e.printStackTrace();
          System.out.println(e.getMessage());
        } catch (Exception e) {
          System.out.println(e.getMessage());
        } catch (Throwable error) {
          System.out.println(error.getMessage());
        } finally {
          System.out.println("finally in VerifyCodeThread...");
          if (reader != null) {
            try {
              reader.close();
            } catch (IOException e) {
              System.out.println(e.getMessage());
            }
          }
          if (os != null) {
            try {
              os.close();
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
          }
        }
      }
    });

    try {
      thread.start();
      thread.join(timeout);
      if (thread.isAlive()) {
        System.out.println("VerifyCodeService seems not working..");
        thread.interrupt();
      } else {
        System.out.println(" verify code..");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return verifyCode.getValue();
  }
}