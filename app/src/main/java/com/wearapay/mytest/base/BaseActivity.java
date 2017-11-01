package com.wearapay.mytest.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.wearapay.mytest.util.ToastUtil;

/**
 * Created by lyz on 2016/12/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
  private ProgressDialog progressDialog;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void onResume() {
    super.onResume();
    if (getPresenter() != null) {
      for (int i = 0; i < getPresenter().length; i++) {
        getPresenter()[i].setView(this);
      }
    }
  }

  private void createProgressDialog() {
    if (progressDialog == null) {
      progressDialog = ProgressDialog.show(this, "", "");
      progressDialog.setCancelable(false);
      progressDialog.setCanceledOnTouchOutside(false);
    }
  }

  protected void cleanPresenter() {
    BasePresenter[] presenter = getPresenter();
    if (presenter != null) {
      for (BasePresenter p : presenter) {
        p.clean();
      }
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    cleanPresenter();
    hideProgress();
  }

  protected abstract BasePresenter[] getPresenter();

  @Override public boolean isAlive() {
    return false;
  }

  @Override public void showProgress(String message) {
    hideProgress();
    createProgressDialog();
    progressDialog.setMessage(message);
    progressDialog.show();
  }

  @Override public void showProgress(int messageResourceId) {
    if (messageResourceId == 0) {
      showProgress();
    } else {
      showProgress(getString(messageResourceId));
    }
  }

  @Override public void showProgress() {
    showProgress("");
  }

  @Override public void hideProgress() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.hide();
    }
  }

  @Override public void showMessage(String messageId) {
    ToastUtil.show(messageId);
  }
}
