package io.appium.uiautomator2.handler;

import org.json.JSONObject;

import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Device;
import io.appium.uiautomator2.utils.Logger;

import static io.appium.uiautomator2.utils.API.API_18;

public class CompressedLayoutHierarchy extends SafeRequestHandler {

    public CompressedLayoutHierarchy(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        boolean compressLayout;
        // setCompressedLayoutHeirarchy doesn't exist on API <= 17

        try {
            if (API_18) {
                JSONObject payload = getPayload(request);
                compressLayout = (Boolean) payload.get("compressLayout");
                Device.getUiDevice().setCompressedLayoutHeirarchy(compressLayout);
                Logger.info("Set the Compressed Layout Hierarchy");
            } else {
                Logger.info("SetCompressedLayoutHeirarchy doesn't exist on API <= 17");
                return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, "Unable" +
                        " to set Compressed Layout Hierarchy on device below API level 18");
            }

        } catch (Exception e) {
            Logger.error("error setting compressLayoutHierarchy " + e.getMessage());
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }

        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, compressLayout);
    }
}
