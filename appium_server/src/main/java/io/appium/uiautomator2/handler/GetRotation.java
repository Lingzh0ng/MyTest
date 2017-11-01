package io.appium.uiautomator2.handler;

import org.json.JSONException;
import org.json.JSONObject;

import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.model.OrientationEnum;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Device;
import io.appium.uiautomator2.utils.Logger;

public class GetRotation extends SafeRequestHandler {

    public GetRotation(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        int rotation = Device.getUiDevice().getDisplayRotation();
        try {
            return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, getOrientationMap(rotation));
        } catch (JSONException e) {
            Logger.error("Exception while creating Orientation Map: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.JSON_DECODER_ERROR, e);
        }
    }

    private JSONObject getOrientationMap(int orientation) throws JSONException {
        JSONObject orientationMap = new JSONObject().put("x", 0).put("y", 0);
        OrientationEnum orientationEnum = OrientationEnum.fromInteger(orientation);
        switch (orientationEnum){
            case ROTATION_0:
                orientationMap.put("z", 0);
                break;
            case ROTATION_90:
                orientationMap.put("z", 90);
                break;
            case ROTATION_180:
                orientationMap.put("z", 180);
                break;
            case ROTATION_270:
                orientationMap.put("z", 270);
                break;
        }
        return orientationMap;
    }
}
