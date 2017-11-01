package io.appium.uiautomator2.handler;

import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObjectNotFoundException;

import java.text.MessageFormat;

import io.appium.uiautomator2.common.exceptions.NoAttributeFoundException;
import io.appium.uiautomator2.common.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.http.AppiumResponse;
import io.appium.uiautomator2.http.IHttpRequest;
import io.appium.uiautomator2.model.AndroidElement;
import io.appium.uiautomator2.model.KnownElements;
import io.appium.uiautomator2.server.WDStatus;
import io.appium.uiautomator2.utils.Logger;

public class GetElementAttribute extends SafeRequestHandler {

    public GetElementAttribute(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        Logger.info("get attribute of element command");
        String id = getElementId(request);
        String attributeName = getNameAttribute(request);
        AndroidElement element = KnownElements.getElementFromCache(id);
        if (element == null) {
            return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_ELEMENT);
        }
        try {
            if ("name".equals(attributeName) || "contentDescription".equals(attributeName)
                    || "text".equals(attributeName) || "className".equals(attributeName)
                    || "resourceId".equals(attributeName)) {
                String attribute = element.getStringAttribute(attributeName);
                return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, attribute);
            } else {
                Boolean boolAttribute = element.getBoolAttribute(attributeName);
                // The result should be of type string according to
                // https://w3c.github.io/webdriver/webdriver-spec.html#get-element-attribute
                return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS,
                        boolAttribute.toString());
            }

        } catch (UiObjectNotFoundException e) {
            Logger.error("Element not found: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_ELEMENT);
        } catch (NoAttributeFoundException e) {
            Logger.error(MessageFormat.format("Requested attribute {0} not supported.", attributeName), e);
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_COMMAND, e);
        } catch(StaleObjectException e){
            Logger.error("Stale Element Exception: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.STALE_ELEMENT_REFERENCE, e);
        } catch (UiAutomator2Exception e) {
            Logger.error(MessageFormat.format("Unable to retrive attribute {0}", attributeName), e);
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }

    }
}
