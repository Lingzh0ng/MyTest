package io.appium.uiautomator2.handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public class AppStrings extends SafeRequestHandler {
    public AppStrings(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        String msg;
        final String filePath = "/data/local/tmp/strings.json";
        JSONObject appStrings;
        try {
            final File jsonFile = new File(filePath);
            Logger.debug("Loading strings.json from file location: " + filePath);

            if (!jsonFile.exists()) {
                msg = "strings.json doesn't exist";
                Logger.error(msg);
                return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, msg);
            }

            DataInputStream dataInput = new DataInputStream(
                    new FileInputStream(jsonFile));
            byte[] jsonBytes = new byte[(int) jsonFile.length()];
            dataInput.readFully(jsonBytes);
            dataInput.close();

            appStrings = new JSONObject(new String(jsonBytes, "UTF-8"));
            Logger.debug("json loading complete ");

        } catch (IOException e) {
            Logger.error("Error loading json from " + filePath + " : " + e);
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        } catch (JSONException e) {
            Logger.error("Exception while reading JSON: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.JSON_DECODER_ERROR, e);
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, appStrings);
    }
}
