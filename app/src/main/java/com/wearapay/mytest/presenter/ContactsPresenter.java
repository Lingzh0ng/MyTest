package com.wearapay.mytest.presenter;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.wearapay.mytest.APP;
import com.wearapay.mytest.ResultCallback;
import com.wearapay.mytest.base.BasePresenter;
import com.wearapay.mytest.bean.ContactBean;
import com.wearapay.mytest.view.ContactsView;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by lyz on 2016/12/16.
 */
public class ContactsPresenter extends BasePresenter<ContactsView> {
  private Context context;
  private List myContactBeans;

  public ContactsPresenter(Context context, List myContactBeans) {
    this.context = context;
    this.myContactBeans = myContactBeans;
  }

  public void getPerson(final ResultCallback<List> callback) {
    new Thread() {
      @Override public void run() {
        super.run();
        if (myContactBeans.size() > 0) {
          return;
        }
        //使用getContentResolver方法来读取联系人的表
        Cursor cursor = context.getContentResolver()
            .query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ContactBean bean = null;
        while (cursor.moveToNext()) {
          //联系人的ID
          String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
          //联系人的名称
          String name =
              cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
          //联系人的电话
          String number = "";

          //联系人是否有电话号码
          int isHas = Integer.parseInt(
              cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

          if (isHas > 0) {
            Cursor c = context.getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
            while (c.moveToNext()) {
              number += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                  + "  ";
            }
            c.close();
          }
          bean = new ContactBean(name, number);
          myContactBeans.add(bean);
        }
        cursor.close();
        APP.mHandler.post(new TimerTask() {
          @Override public void run() {
            callback.onResult(myContactBeans);
          }
        });
      }
    }.start();
  }

  private final String[] arr = new String[] {
      "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
      "S", "T", "U", "V", "W", "X", "Y", "Z"
  };

  public void sort(List<ContactBean> list) {
    List<Object> sortList = new ArrayList<>();
    for (String s : arr) {
      sortList.add(s);
      int count = 0;
      for (ContactBean bean : list) {
        char c = bean.getPy().toCharArray()[0];
        char c1 = s.toCharArray()[0];
        if (c == c1) {
          sortList.add(bean);
          count ++;
        } else {
          if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
            if (c1 == '#') {
              sortList.add(bean);
              count++;
            }
          }
        }
      }
      if (count == 0) {
        sortList.remove(s);
        System.out.println(s);
      }
    }
    view.setAdapterData(sortList);
  }
}