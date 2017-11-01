package com.myapplication;

import android.content.Context;

import java.util.List;

/**
 * Created by yichao.hu on  2017/4/24 19:34
 */

public class UsersDiscussPopAdapter extends CommonBaseAdapter<String> {

    public UsersDiscussPopAdapter(Context context, List<String> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, String s) {

    }
}
