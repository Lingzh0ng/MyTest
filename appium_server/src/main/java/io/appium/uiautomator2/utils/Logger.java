package io.appium.uiautomator2.utils;

public class Logger {
    public static final String TAG = "appium";

    private static String getString(Object... args) {
        StringBuilder content = new StringBuilder();

        for (Object arg : args) {
            if (arg != null) {
                content.append(arg.toString());
            }
        }

        return content.toString();
    }

    /**
     * Logger error
     */
    public static void error(Object... messages) {
        android.util.Log.e(TAG, getString(messages));
    }

    /**
     * Logger error
     */
    public static void error(String message, Throwable throwable) {
        android.util.Log.e(TAG, getString(message), throwable);
    }

    /**
     * Logger info
     */
    public static void info(Object... messages) {
        android.util.Log.i(TAG, getString(messages));
    }

    /**
     * Logger debug
     */
    public static void debug(Object... messages) {
        android.util.Log.d(TAG, getString(messages));
    }
}
