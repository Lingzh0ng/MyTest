package com.wearapay.chatdemo;

/**
 * Created by lyz on 2017/1/4.
 */
public class MsgBean {
  private String message;
  /**
   * 0为接收
   * 1为发送
   */
  private int type;

  private String time;

  public MsgBean(String message, int type, String time) {
    this.message = message;
    this.type = type;
    this.time = time;
  }

  public String getMessage() {
    return message;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override public String toString() {
    return "MsgBean{" +
        "message='" + message + '\'' +
        ", type=" + type +
        '}';
  }
}
