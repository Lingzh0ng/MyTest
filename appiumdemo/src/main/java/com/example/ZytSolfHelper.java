package com.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyz on 2017/8/14.
 */

public class ZytSolfHelper {
  //private static final int password_height = 160;
  private static final int password_height = 175;
  private static final int password_wide = 108;
  private static final int password_height_half = password_height / 2;
  private static final int password_wide_half = password_wide / 2;
  //private static final int start_height = 1140 + password_height_half;
  private static final int start_height = 1220 + password_height_half;

  private static final int number_wide = 360;
  private static final int number_wide_half = number_wide / 2;

  public static class Point {
    public int X;
    public int Y;

    public Point() {
    }

    public Point(int x, int y) {
      X = x;
      Y = y;
    }

    public int getX() {
      return X;
    }

    public void setX(int x) {
      X = x;
    }

    public int getY() {
      return Y;
    }

    public void setY(int y) {
      Y = y;
    }

    @Override public String toString() {
      return "Point{" +
          "X=" + X +
          ", Y=" + Y +
          '}';
    }
  }

  public static Map<String, Point> zytMap = new HashMap<>();
  public static Map<String, Point> zytNumberMap = new HashMap<>();
  public static Map<String, Point> zytOhterMap = new HashMap<>();

  static {
    zytMap.put("q", new Point(password_wide_half + password_wide * 0, start_height));
    zytMap.put("w", new Point(password_wide_half + password_wide * 1, start_height));
    zytMap.put("e", new Point(password_wide_half + password_wide * 2, start_height));
    zytMap.put("r", new Point(password_wide_half + password_wide * 3, start_height));
    zytMap.put("t", new Point(password_wide_half + password_wide * 4, start_height));
    zytMap.put("y", new Point(password_wide_half + password_wide * 5, start_height));
    zytMap.put("u", new Point(password_wide_half + password_wide * 6, start_height));
    zytMap.put("i", new Point(password_wide_half + password_wide * 7, start_height));
    zytMap.put("o", new Point(password_wide_half + password_wide * 8, start_height));
    zytMap.put("p", new Point(password_wide_half + password_wide * 9, start_height));

    zytMap.put("a", new Point(password_wide * 1, start_height + password_height));
    zytMap.put("s", new Point(password_wide * 2, start_height + password_height));
    zytMap.put("d", new Point(password_wide * 3, start_height + password_height));
    zytMap.put("f", new Point(password_wide * 4, start_height + password_height));
    zytMap.put("g", new Point(password_wide * 5, start_height + password_height));
    zytMap.put("h", new Point(password_wide * 6, start_height + password_height));
    zytMap.put("j", new Point(password_wide * 7, start_height + password_height));
    zytMap.put("k", new Point(password_wide * 8, start_height + password_height));
    zytMap.put("l", new Point(password_wide * 9, start_height + password_height));

    zytMap.put("z", new Point(password_wide * 2, start_height + password_height * 2));
    zytMap.put("x", new Point(password_wide * 3, start_height + password_height * 2));
    zytMap.put("c", new Point(password_wide * 4, start_height + password_height * 2));
    zytMap.put("v", new Point(password_wide * 5, start_height + password_height * 2));
    zytMap.put("b", new Point(password_wide * 6, start_height + password_height * 2));
    zytMap.put("n", new Point(password_wide * 7, start_height + password_height * 2));
    zytMap.put("m", new Point(password_wide * 8, start_height + password_height * 2));

    zytMap.put("number",
        new Point(password_wide * 1 + password_wide_half, start_height + password_height * 3));
    zytMap.put("hide",
        new Point(password_wide * 9 + password_wide_half, start_height + password_height * 3));

    zytMap.put("1", new Point(password_wide_half + password_wide * 0, start_height));
    zytMap.put("2", new Point(password_wide_half + password_wide * 1, start_height));
    zytMap.put("3", new Point(password_wide_half + password_wide * 2, start_height));
    zytMap.put("4", new Point(password_wide_half + password_wide * 3, start_height));
    zytMap.put("5", new Point(password_wide_half + password_wide * 4, start_height));
    zytMap.put("6", new Point(password_wide_half + password_wide * 5, start_height));
    zytMap.put("7", new Point(password_wide_half + password_wide * 6, start_height));
    zytMap.put("8", new Point(password_wide_half + password_wide * 7, start_height));
    zytMap.put("9", new Point(password_wide_half + password_wide * 8, start_height));
    zytMap.put("0", new Point(password_wide_half + password_wide * 9, start_height));

    zytNumberMap.put("1", new Point(number_wide_half + number_wide * 0, start_height));
    zytNumberMap.put("2", new Point(number_wide_half + number_wide * 1, start_height));
    zytNumberMap.put("3", new Point(number_wide_half + number_wide * 2, start_height));
    zytNumberMap.put("4",
        new Point(number_wide_half + number_wide * 0, start_height + password_height));
    zytNumberMap.put("5",
        new Point(number_wide_half + number_wide * 1, start_height + password_height));
    zytNumberMap.put("6",
        new Point(number_wide_half + number_wide * 2, start_height + password_height));
    zytNumberMap.put("7",
        new Point(number_wide_half + number_wide * 0, start_height + password_height * 2));
    zytNumberMap.put("8",
        new Point(number_wide_half + number_wide * 1, start_height + password_height * 2));
    zytNumberMap.put("9",
        new Point(number_wide_half + number_wide * 2, start_height + password_height * 2));
    zytNumberMap.put("0",
        new Point(number_wide_half + number_wide * 1, start_height + password_height * 3));

    zytNumberMap.put("hide",
        new Point(number_wide_half + number_wide * 0, start_height + password_height * 3));

    zytOhterMap.put("btn_check_next", new Point(540, 1650));
    zytOhterMap.put("btn_zyt_charge", new Point(870, 585));
    zytOhterMap.put("ed_zyt_card", new Point(537, 315));
    zytOhterMap.put("ed_charge_card", new Point(610, 474));
    zytOhterMap.put("ed_charge_amount", new Point(807, 792));
    zytOhterMap.put("btn_charge_commit", new Point(540, 1323));
    zytOhterMap.put("btn_cancel", new Point(1000, 755));
  }
}
