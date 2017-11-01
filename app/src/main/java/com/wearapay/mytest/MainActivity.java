package com.wearapay.mytest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import com.wearapay.mytest.adapter.MyListViewAdapter;
import com.wearapay.mytest.base.BaseActivity;
import com.wearapay.mytest.base.BasePresenter;
import com.wearapay.mytest.bean.ContactBean;
import com.wearapay.mytest.presenter.ContactsPresenter;
import com.wearapay.mytest.ui.TelPhoneView;
import com.wearapay.mytest.util.ToastUtil;
import com.wearapay.mytest.view.ContactsView;
import com.wearapay.pulltorefresh.widget.XListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity
    implements ContactsView, XListView.IXListViewListener {

  private XListView listView;
  private TelPhoneView telPhoneView;

  private ContactsPresenter presenter;

  List<ContactBean> myContactBeans = new ArrayList<>();
  private TextView tvName;
  private int tvNameHeight;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tel_layout);
    listView = (XListView) findViewById(R.id.list);
    listView.setPullRefreshEnable(true);
    listView.setPullLoadEnable(true);
    listView.setAutoLoadEnable(true);
    listView.setXListViewListener(this);
    listView.setRefreshTime(getTime());
    telPhoneView = (TelPhoneView) findViewById(R.id.telView);
    tvName = (TextView) findViewById(R.id.tvName);
    presenter = new ContactsPresenter(this, new ArrayList());
    showProgress();
  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onResume() {
    super.onResume();
    myRequestPermission();
  }

  @Override protected BasePresenter[] getPresenter() {
    return new BasePresenter[] { presenter };
  }

  private void myRequestPermission() {
    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
          new String[] { android.Manifest.permission.READ_CONTACTS }, 1);
    } else {
      presenter.getPerson(new ResultCallback<List>() {
        @Override public void onResult(List list) {
          presenter.sort(list);
        }
      });
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 1) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // Permission Granted
        presenter.getPerson(new ResultCallback<List>() {
          @Override public void onResult(List list) {
            presenter.sort(list);
          }
        });
      } else {
        // Permission Denied
        ToastUtil.show("权限被拒绝");
      }
    }
  }

  @Override public void setAdapterData(final List list) {

    listView.setAdapter(new MyListViewAdapter(this, list));

    tvNameHeight = tvName.getMeasuredHeight();

    telPhoneView.setTelPhoneChangeListener(new TelPhoneView.TelPhoneChangeListener() {
      @Override public void onClick(int index, String string) {
        clickIndex(string, list);
      }

      @Override public void onUp(int index, String string) {
        clickIndex(string, list);
      }
    });

    listView.setOnScrollListener(new AbsListView.OnScrollListener() {

      @Override public void onScrollStateChanged(AbsListView absListView, int i) {

      }

      @Override
      public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
        if (list.get(firstVisibleItem) instanceof String) {
          tvName.setText(list.get(firstVisibleItem).toString());
          tvName.setTranslationY(0);
        }

        if (visibleItemCount == 0) {
          tvName.setVisibility(View.INVISIBLE);
          return;
        }
        int firstVisibleItemTop = absListView.getChildAt(0).getTop();
        int firstVisibleItemBottom = absListView.getChildAt(0).getBottom();
        Log.d("onScroll", "firstVisibleItemTop : "
            + firstVisibleItemTop
            + "  firstVisibleItemBottom : "
            + firstVisibleItemBottom
            + "  firstVisibleItem : "
            + firstVisibleItem);
        if (firstVisibleItem == 0) {
          tvName.setVisibility(View.INVISIBLE);
          return;
        }
        if (list.get(firstVisibleItem - 1) instanceof ContactBean) {
          ContactBean bean = (ContactBean) list.get(firstVisibleItem - 1);
          String s = bean.getPy().substring(0, 1);
          if (s.toCharArray()[0] < 'A' || s.toCharArray()[0] > 'Z') {
            s = "#";
          }
          tvName.setText(s);
        }else {
          tvName.setText((String)list.get(firstVisibleItem - 1));
        }
        if (firstVisibleItem == 1) {
          tvName.setVisibility(View.VISIBLE);
          tvNameHeight = firstVisibleItemBottom - firstVisibleItemTop;
        } else {
          tvName.setVisibility(View.VISIBLE);
          //int nextIndex = firstVisibleItem + 2;
          if (list.get(firstVisibleItem) instanceof String) {
            if (firstVisibleItemBottom - firstVisibleItemTop > tvNameHeight) {
              int preBottom = firstVisibleItemBottom - tvNameHeight;
              if (preBottom <= 0) {
                tvName.setTranslationY(preBottom);
              }
            }
          } else {
            tvName.setTranslationY(0);
          }
        }
      }
    });
    hideProgress();
  }

  private void clickIndex(String string, List list) {
    int i = list.indexOf(string);
    if (i != -1) {
      listView.setSelection(i + 1);
      tvName.setText(string);
    }
  }

  @Override public void onRefresh() {
    APP.mHandler.postDelayed(new Runnable() {
      @Override public void run() {
        onLoad();
      }
    }, 2500L);
  }

  @Override public void onLoadMore() {
    APP.mHandler.postDelayed(new Runnable() {
      @Override public void run() {
        onLoad();
      }
    }, 2500L);
  }

  private void onLoad() {
    listView.stopRefresh();
    listView.stopLoadMore();
    listView.setRefreshTime(getTime());
  }

  private String getTime() {
    return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
  }

  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);

    if (hasFocus) {
      listView.autoRefresh();
    }
  }
}
