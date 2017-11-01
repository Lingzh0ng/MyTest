package io.appium.uiautomator2.handler;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.model.AppiumUiAutomatorDriver;
import io.appium.uiautomator2.model.Session;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public class NewSession extends SafeRequestHandler {

    public NewSession(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {

        String sessionID;
        try {
            Session.capabilities = getCapabilities(request);
            sessionID = new AppiumUiAutomatorDriver().initializeSession();
            Logger.info("Session Created with SessionID:" + sessionID);
        } catch (JSONException e) {
            Logger.error("Exception while reading JSON: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.JSON_DECODER_ERROR, e);
        }
        return new AppiumResponse(sessionID, WDStatus.SUCCESS, "Created Session");
    }


    public  Map<String, Object> getCapabilities(IHttpRequest request) throws JSONException {
        JSONObject caps = getPayload(request).getJSONObject("desiredCapabilities");
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = caps.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = caps.get(key);
            map.put(key, value);
        }
        return map;
    }
}
