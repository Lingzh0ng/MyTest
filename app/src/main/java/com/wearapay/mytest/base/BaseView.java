package com.wearapay.mytest.base;

/**
 * Created by lyz on 2016/12/16.
 */
public interface BaseView {
  boolean isAlive();

  void showProgress(String message);

  void showProgress(int messageResourceId);

  void showProgress();

  void hideProgress();

  /**
   * @param messageId string name
   */
  void showMessage(String messageId);
}
