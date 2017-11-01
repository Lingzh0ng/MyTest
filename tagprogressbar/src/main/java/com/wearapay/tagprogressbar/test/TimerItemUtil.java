package com.wearapay.tagprogressbar.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Author :leilei on 2017/3/21 1422.
 */

public class TimerItemUtil {
  public static List<TimerItem> getTimerItemList() {
    List<TimerItem> lstTimerItems = new ArrayList<>();
    lstTimerItems.add(new TimerItem("A", 110 * 1001110L));
    lstTimerItems.add(new TimerItem("B", 220 * 10020L));
    lstTimerItems.add(new TimerItem("C", 260 * 10020L));
    lstTimerItems.add(new TimerItem("D", 330 * 10030L));
    lstTimerItems.add(new TimerItem("E", 240 * 10040L));
    lstTimerItems.add(new TimerItem("F", 980 * 10500L));
    lstTimerItems.add(new TimerItem("G", 140 * 15000L));
    lstTimerItems.add(new TimerItem("H", 306 * 10050L));
    lstTimerItems.add(new TimerItem("I", 58 * 10050L));
    lstTimerItems.add(new TimerItem("J", 47 * 10050L));
    lstTimerItems.add(new TimerItem("K", 66 * 10050L));
    lstTimerItems.add(new TimerItem("L", 550 * 10050L));
    lstTimerItems.add(new TimerItem("M", 620 * 10500L));
    lstTimerItems.add(new TimerItem("N", 45 * 10500L));
    lstTimerItems.add(new TimerItem("O", 14 * 10500L));
    lstTimerItems.add(new TimerItem("ae", 14 * 10500L));
    lstTimerItems.add(new TimerItem("Oss", 14 * 10500L));
    lstTimerItems.add(new TimerItem("Oas", 14 * 10500L));
    lstTimerItems.add(new TimerItem("asO", 14 * 1050000L));

    return lstTimerItems;
  }
}
