package io.appium.uiautomator2.http;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public class AppiumResponse {
    private final int status;
    private final Object value;
    private final String sessionId;

    public AppiumResponse(String sessionId, WDStatus status, Object value) {
        this.sessionId = sessionId;
        this.status = status.code();
        this.value = value;
    }

    public AppiumResponse(String sessionId, WDStatus status, Throwable throwable) {
        this.sessionId = sessionId;
        this.status = status.code();
        this.value = Log.getStackTraceString(throwable);
    }

    public AppiumResponse(String sessionId, Object value) {
        this(sessionId, WDStatus.SUCCESS, value);
    }

    public AppiumResponse(String sessionId, WDStatus status) {
        this(sessionId, status, status.message());
    }

    public static AppiumResponse forCatchAllError(String sessionId, Throwable e) {
        return new AppiumResponse(sessionId, WDStatus.UNKNOWN_ERROR, e);
    }

    public String render() {
        JSONObject o = new JSONObject();
        try {
            if (sessionId != null) {
                o.put("sessionId", sessionId);
            }
            o.put("status", status);
            if (value != null) {
                o.put("value", value);
            }
        } catch (JSONException e) {
            Logger.error("Unable to create JSON Object:", e);
        }
        Logger.info("AppiumResponse: ", o.toString());
        return o.toString();
    }
}

