package com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;


import java.lang.reflect.Field;

public class DisplayUtils {

    /**
     * ?规???????杈ㄧ?浠?dp ???浣?杞??涓?px(???)
     */
    public static int dip2px(Context context, int dpValue) {
        float scale = 0;
        try {
            scale = context.getResources().getDisplayMetrics().density;
        } catch (Exception e) {
            return dpValue;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dip2px(Context context, double dpValue) {
        float scale = 0;
        try {
            scale = context.getResources().getDisplayMetrics().density;
        } catch (Exception e) {
            return (int) dpValue;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ?规???????杈ㄧ?浠?px(???) ???浣?杞??涓?dp
     */
    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, int spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }


    @SuppressWarnings("deprecation")
    public static int getDeviceFitLevel(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth();
        if (w <= 320)
            return 0;
        else if (w >= 480)
            return 1;
        else
            return 2;
    }

    public static int[] getDisplayWidHei(Context ctx) {
        WindowManager wm = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        //Point point = new Point();
        int wid = wm.getDefaultDisplay().getWidth();
        int hei = wm.getDefaultDisplay().getHeight();
        //wm.getDefaultDisplay().getSize(point);
        return new int[]{wid, hei};
    }

    public static int getDisplayHei(Context ctx) {
        WindowManager wm = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        //Point point = new Point();
        return wm.getDefaultDisplay().getHeight();
    }

    @SuppressWarnings("deprecation")
    public static int getDeviceFitSampleLevel(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth();
        if (w <= 320)
            return 256;
        else if (w >= 480)
            return 512;
        else
            return 128;
    }

    @SuppressWarnings("deprecation")
    public static int getStatusHei(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;  //获取状态栏高度
        return statusBarHeight;
    }

    public static int getDisplayHeight(Context context) {
        if (context == null) {
            return 1;
        } else {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
    }

    public static int getDisplayWidth(Context context) {
        if (context == null) {
            return 1;
        } else {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
    }

    // 获取手机状态栏高度
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
//            statusBarHeight = MyApplication.getInstance().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int px2dip(Context context, double pxValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    /**
     * 长的为宽度 <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int screenWidthPx(Context context) {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? widthPx : heightPx;
    }

    /**
     * 小的为高度
     *
     * @param context
     * @return
     */
    public static int screenHeightPx(Context context) {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? heightPx : widthPx;
    }

    public static int getViewWidth(Context context, int width) {
        return getDisplayWidth(context) * width / 640;
    }

    public static int getViewHeight(Context context, int height) {
        return getDisplayHeight(context) * height / 1136;
    }

    public static int getHeightByDisWidth(Context context, int height) {
        return getDisplayWidth(context) * height / 640;
    }

    public static int getNavigationBarHeight(Context ctx){
        int resid = ctx.getResources().getIdentifier("navigation_bar_height","dimen","android");
        return ctx.getResources().getDimensionPixelSize(resid);
    }

    public static boolean checkDeviceHasNavigationBar(Context ctx){
        boolean hasMenuKey = ViewConfiguration.get(ctx).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if(!hasMenuKey && !hasBackKey){
            return true;
        }
        return false;
    }
}
