package io.appium.uiautomator2.handler;

import android.os.SystemClock;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import org.json.JSONException;
import org.json.JSONObject;

import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.core.InteractionController;
import io.appium.uiautomator2.core.UiAutomatorBridge;
import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public class LongPressKeyCode extends SafeRequestHandler {
    public Integer keyCode;
    public Integer metaState;

    public LongPressKeyCode(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        try {
            InteractionController interactionController = UiAutomatorBridge.getInstance().getInteractionController();

            JSONObject payload = getPayload(request);
            Object kc = payload.get("keycode");
            if (kc instanceof Integer) {
                keyCode = (Integer) kc;
            } else if (kc instanceof String) {
                keyCode = Integer.parseInt((String) kc);
            } else {
                return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, "Keycode of type " + kc.getClass() + "not supported.");
            }

            Object ms = payload.has("metastate") ? payload.get("metastate") : null;
            if (ms instanceof Integer) {
                metaState = (Integer) ms;
            } else if (ms instanceof String) {
                metaState = Integer.parseInt((String) ms);
            } else {
                metaState = 0;
            }
            final long eventTime = SystemClock.uptimeMillis();
            // Send an initial down event
            final KeyEvent downEvent = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0, metaState, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_KEYBOARD);
            if (interactionController.injectEventSync(downEvent)) {
                // Send a repeat event. This will cause the FLAG_LONG_PRESS to be set.
                final KeyEvent repeatEvent = KeyEvent.changeTimeRepeat(downEvent, eventTime, 1);
                interactionController.injectEventSync(repeatEvent);
                // Finally, send the up event
                final KeyEvent upEvent = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_UP, keyCode, 0, metaState, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_KEYBOARD);
                interactionController.injectEventSync(upEvent);
            }
            return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, true);
        } catch (JSONException e) {
            Logger.error("Exception while reading JSON: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.JSON_DECODER_ERROR, e);
        } catch (UiAutomator2Exception e) {
            Logger.error("Exception while performing LongPressKeyCode action: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }
    }
}
