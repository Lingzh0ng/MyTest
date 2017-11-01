package com.wearapay.chatdemo;

/**
 * Created by Kindy on 2016/12/30.
 */
public class Message {
  private String phoneNumber;
  private String content;
  private boolean isIncoming;

  public Message() {
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isIncoming() {
    return isIncoming;
  }

  public void setIncoming(boolean incoming) {
    isIncoming = incoming;
  }

  public Message(String phoneNumber, String content, boolean isIncoming) {

    this.phoneNumber = phoneNumber;
    this.content = content;
    this.isIncoming = isIncoming;
  }
}
