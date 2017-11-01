package com.wearapay.coordinatordemo;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private CoordinatorLayout coordinatorLayout;
  private FloatingActionButton floatingActionButton;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
    recyclerView = (RecyclerView) findViewById(R.id.rv);
    floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    init();
  }

  private void init() {
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "111111", Snackbar.LENGTH_SHORT);
        snackbar.setAction("know", new View.OnClickListener() {
          @Override public void onClick(View v) {
            snackbar.dismiss();
          }
        });
        snackbar.show();
      }
    });

    recyclerView.setLayoutManager(
        new FullyLinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
    recyclerView.setAdapter(new MyAdapter());
    recyclerView.setNestedScrollingEnabled(false);
  }

  private class MyAdapter extends RecyclerView.Adapter<MainActivity.MyViewHolder> {

    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new MyViewHolder(View.inflate(MainActivity.this, R.layout.item, null));
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
      holder.textView.setText(position + "");
    }

    @Override public int getItemCount() {
      return 20;
    }
  }

  private class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public MyViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.tv);
    }
  }
}
