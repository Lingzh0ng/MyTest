package com.wearapay.chatdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  private ListView listView;
  private TextView tvSend;
  private EditText etMessage;
  private List<Message> mList = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initData();
    initView();
    initListener();
  }

  private String getCurrentTime() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss     ");
    Date curDate = new Date(System.currentTimeMillis());
    return formatter.format(curDate);
  }

  private void initListener() {

  }

  private void initData() {
    for (int i = 0; i <= 20; i++) {
      int type = new Random().nextInt(2);
      mList.add(new Message("11111111111", "你吃饭了吗" + i, new Random().nextBoolean()));
    }
  }

  private void initView() {
    listView = (ListView) findViewById(R.id.listView);
    tvSend = (TextView) findViewById(R.id.tvSend);
    etMessage = (EditText) findViewById(R.id.etMessage);
    listView.setAdapter(new WPChatAdapter(this, mList));
  }

  @Override protected void onStart() {
    super.onStart();
    listView.setSelection(mList.size());
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(MainActivity.this, "P:" + i);
        startActivity(new Intent(MainActivity.this,TextActivity.class));
      }
    });
  }
}
