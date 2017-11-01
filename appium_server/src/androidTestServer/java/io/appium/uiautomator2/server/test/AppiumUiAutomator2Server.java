package io.appium.uiautomator2.server.test;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.appium.uiautomator2.common.exceptions.SessionRemovedException;
import io.appium.uiautomator2.server.ServerConfig;
import io.appium.uiautomator2.server.ServerInstrumentation;
import io.appium.uiautomator2.utils.Logger;

@RunWith(AndroidJUnit4.class)
public class AppiumUiAutomator2Server {
    private static ServerInstrumentation serverInstrumentation;
    private Context ctx;


    /**
     * Starts the server on the device
     */
    @Test
    public void startServer() throws InterruptedException {
        if (serverInstrumentation == null) {
            ctx = InstrumentationRegistry.getInstrumentation().getContext();
            serverInstrumentation = ServerInstrumentation.getInstance(ctx, ServerConfig.getServerPort());
            Logger.info("[AppiumUiAutomator2Server]", " Starting Server");
            try {
                while (!serverInstrumentation.isStopServer()) {
                    SystemClock.sleep(1000);
                    serverInstrumentation.startServer();
                }
            }catch (SessionRemovedException e){
                //Ignoring SessionRemovedException
            }
        }
    }
}

