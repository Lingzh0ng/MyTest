package io.appium.uiautomator2.handler;

import android.os.SystemClock;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.core.InteractionController;
import io.appium.uiautomator2.core.UiAutomatorBridge;
import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.model.AndroidElement;
import io.appium.uiautomator2.model.KnownElements;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public class TouchLongClick extends TouchEvent {

    public TouchLongClick(String mappedUri) {
        super(mappedUri);
    }

    protected static boolean correctLongClick(final int x, final int y, final int duration) {
        try {
            /*
             * bridge.getClass() returns ShellUiAutomatorBridge on API 18/19 so use
             * the super class.
             */

            InteractionController interactionController = UiAutomatorBridge.getInstance().getInteractionController();

            if (interactionController.touchDown(x, y)) {
                SystemClock.sleep(duration);
                if (interactionController.touchUp(x, y)) {
                    return true;
                }
            }
            return false;

        } catch (final Exception e) {
            Logger.debug("Problem invoking correct long click: " + e);
            return false;
        }
    }

    @Override
    protected boolean executeTouchEvent() throws UiObjectNotFoundException, UiAutomator2Exception, JSONException {

        int duration =  params.has("duration") ? Integer.parseInt(params.getString("duration")) : 2000;

        printEventDebugLine("TouchLongClick", duration);
        if (correctLongClick(clickX, clickY, duration)) {
            return true;
        }
        // if correctLongClick failed and we have an element
        // then uiautomator's longClick is used as a fallback.
        if (params.has(ELEMENT_ID_KEY_NAME)) {
            Logger.debug("Falling back to broken longClick");
            return element.longClick();
        }
        return false;
    }
}
