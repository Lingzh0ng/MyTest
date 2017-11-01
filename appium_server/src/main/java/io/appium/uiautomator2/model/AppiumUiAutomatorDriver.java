package io.appium.uiautomator2.model;


import org.json.JSONException;

import java.util.UUID;


public class AppiumUiAutomatorDriver {

    private Session session;

    public String initializeSession() throws JSONException {

        if (this.session != null) {
            session.getKnownElements().clear();
            return session.getSessionId();
        }
        this.session = new Session(UUID.randomUUID().toString());
        return session.getSessionId();
    }
}

