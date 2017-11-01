package com.wearapay.selectpic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * .  -.--,       .--,
 * .  ( (  \.---./  ) )
 * .   '.__/o   o\__.'
 * .      {=  ^  =}
 * .       >  -  <
 * .     //       \\
 * .    "'\       /'"_.-~^`'-.
 * .       \  _  /--'         `
 * .    (((__) (__)))
 * -    高山仰止,景行行止.
 * -    虽不能至,心向往之.
 * Created by Van Liu on 2017/7/25.
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
