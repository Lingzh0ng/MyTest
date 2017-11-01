package io.appium.uiautomator2.handler.request;

import android.support.test.uiautomator.StaleObjectException;

import org.json.JSONArray;
import org.json.JSONException;

import io.appium.uiautomator2.common.exceptions.NoSuchContextException;
import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.model.AndroidElement;
import io.appium.uiautomator2.model.KnownElements;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public abstract class SafeRequestHandler extends BaseRequestHandler {

    protected final String ELEMENT_ID_KEY_NAME = "element";

    public SafeRequestHandler(String mappedUri) {
        super(mappedUri);
    }


    protected String getIdOfKnownElement(IHttpRequest request, AndroidElement element) {
        return KnownElements.getIdOfElement(element);
    }

    protected AndroidElement getElementFromCache(IHttpRequest request, String id) {

        return KnownElements.getElementFromCache(id);
    }


    protected String[] extractKeysToSendFromPayload(IHttpRequest request) throws JSONException, UiAutomator2Exception {
        JSONArray valueArr = getPayload(request).getJSONArray("value");
        if (valueArr == null || valueArr.length() == 0) {
            throw new UiAutomator2Exception("No key to send to an element was found.");
        }

        String[] toReturn = new String[valueArr.length()];

        for (int i = 0; i < valueArr.length(); i++) {
            toReturn[i] = valueArr.getString(i);
        }

        return toReturn;
    }

    public abstract AppiumResponse safeHandle(IHttpRequest request);

    @Override
    public final AppiumResponse handle(IHttpRequest request) {
        try {
            return safeHandle(request);
        } catch (NoSuchContextException e) {
            //TODO: update error code when w3c spec gets updated
            return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_WINDOW, new UiAutomator2Exception("Invalid window handle was used: only 'NATIVE_APP' and 'WEBVIEW' are supported."));
        } catch (StaleObjectException e) {
            Logger.error("Stale Element Reference Exception: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.STALE_ELEMENT_REFERENCE, e);
        } catch (NoClassDefFoundError e) {
            // This is a potentially interesting class path problem which should be returned to client.
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_COMMAND, e);
        } catch (Exception e) {
            // The advantage of catching general Exception here is that we can propagate the Exception to clients.
            Logger.error("Exception while handling action in: " + this.getClass().getName(), e);
            return AppiumResponse.forCatchAllError(getSessionId(request), e);
        } catch (Throwable e) {
            // Catching Errors seems like a bad idea in general but if we don't catch this, Netty will catch it anyway.
            // The advantage of catching it here is that we can propagate the Error to clients.
            Logger.error("Fatal error while handling action in: " + this.getClass().getName(), e);
            return AppiumResponse.forCatchAllError(getSessionId(request), e);
        }
    }
}
