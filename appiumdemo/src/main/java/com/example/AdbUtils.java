package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 16/02/2017.
 */
public class AdbUtils {
  private AdbUtils() {
  }

  public static List<String> runProcess(String... cmds) {
    System.out.print("command to run: ");
    for (String s : cmds) {
      System.out.print(s + " ");
    }
    System.out.print("\n");
    try {
      ProcessBuilder pb = new ProcessBuilder(cmds);
      pb.redirectErrorStream(true);
      Process p = pb.start();
      p.waitFor();
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String _temp = null;
      List<String> line = new ArrayList<String>();
      while ((_temp = in.readLine()) != null) {
        System.out.println("temp line: " + _temp);
        line.add(_temp);
      }
      System.out.println("result after command: " + line);
      return line;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
