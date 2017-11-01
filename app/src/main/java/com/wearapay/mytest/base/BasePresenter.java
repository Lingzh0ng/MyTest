package com.wearapay.mytest.base;

/**
 * Created by lyz on 2016/12/16.
 */
public abstract class BasePresenter<T extends BaseView> {
  protected T view;

  public void setView(T view){
    this.view = view;
  }

  public void clean(){
    this.view = null;
  }
}
