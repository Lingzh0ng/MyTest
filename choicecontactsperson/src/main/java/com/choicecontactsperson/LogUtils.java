package com.choicecontactsperson;

import android.util.Log;


/**
 * @author liugang.kendl
 */
public class LogUtils {
    public static boolean isOpenLog = false;
    public static void initLog(boolean isOpen){
        isOpenLog = isOpen;
    }
    /**
     * error
     * @param tag
     * @param message
     */
    public static void e(String tag, String message){
        if (isOpenLog && message != null) {
            Log.e(BaseConst.LOG_PREFIX + tag, message);
        }
    }

    /**
     * error
     * @param tag
     */
    public static void e(String tag, Throwable e){
        if (isOpenLog && e != null) {
            Log.e(BaseConst.LOG_PREFIX + tag, e.getMessage(), e);
        }
    }

    /**
     * error
     * @param tag
     * @param message
     */
    public static void e(String tag, String message, Throwable e) {
        if (isOpenLog && e != null) {
            Log.e(BaseConst.LOG_PREFIX + tag, message, e);
        }
    }

    /**
     * warn
     * @param tag
     * @param message
     */
    public static void w(String tag, String message) {
        if (isOpenLog && message != null) {
            Log.w(BaseConst.LOG_PREFIX + tag, message);
        }
    }

    /**
     * error
     *
     * @param tag
     */
    public static void w(String tag, Throwable e){
        if (isOpenLog && e != null) {
            Log.e(BaseConst.LOG_PREFIX + tag, e.getMessage(), e);
        }
    }

    /**
     * info
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        if (isOpenLog && message != null) {
            Log.i(BaseConst.LOG_PREFIX + tag, message);
        }
    }

    /**
     * i信息,打包发布后不输出日志
     *
     * @param tag
     * @param message
     */
    public static void ii(String tag, String message) {
        if (BuildConfig.DEBUG && message != null) {
            Log.i(BaseConst.LOG_PREFIX + tag, message);
        }
    }

    /**
     * Debug
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if (isOpenLog && message != null) {
            Log.d(BaseConst.LOG_PREFIX + tag, message);
        }
    }

    /**
     * Debug
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, byte[] message) {
        if (isOpenLog && message != null) {
            // Log.d(BaseConst.LOG_PREFIX + tag, message);
        }
    }
}
