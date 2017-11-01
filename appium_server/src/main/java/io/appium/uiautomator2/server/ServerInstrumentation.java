package io.appium.uiautomator2.server;

import android.content.Context;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;

import io.appium.uiautomator2.common.exceptions.SessionRemovedException;
import io.appium.uiautomator2.utils.Logger;

import static io.appium.uiautomator2.utils.Device.getUiDevice;

public class ServerInstrumentation {
    private static ServerInstrumentation instance;
    private static Context context;
    private HttpdThread serverThread;
    private PowerManager.WakeLock wakeLock;
    private int serverPort;
    private  boolean isStopServer;

    private ServerInstrumentation(int serverPort) {
        this.serverPort = serverPort;

        if (!isValidPort(serverPort)) {
            throw new RuntimeException(("Invalid port: " + serverPort));
        }
    }

    public boolean isStopServer(){
        return isStopServer;
    }
    private static boolean isValidPort(int port) {
        return port >= 1024 && port <= 65535;
    }

    public static synchronized ServerInstrumentation getInstance(Context activityContext, int serverPort) {
        if (instance == null) {
            context = activityContext;
            instance = new ServerInstrumentation(serverPort);
        }
        return instance;
    }

    public void stopServer() {
        try {
            if (wakeLock != null) {
                try {
                    wakeLock.release();
                }catch(Exception e){/* ignore */}
                wakeLock = null;
            }
            stopServerThread();

        } finally {
            instance = null;
        }
    }


    public void startServer() throws InterruptedException, SessionRemovedException {
        if (serverThread != null && serverThread.isAlive()) {
            return;
        }

        if(serverThread == null && isStopServer){
            throw new SessionRemovedException("Delete Session has been invoked");
        }

        if (serverThread != null) {
            Logger.error("Stopping UiAutomator2 io.appium.uiautomator2.http io.appium.uiautomator2.server");
            stopServer();
        }

        serverThread = new HttpdThread(this, this.serverPort);
        serverThread.start();
        //client to wait for io.appium.uiautomator2.server to up
        Logger.info("io.appium.uiautomator2.server started:");
    }

    private void stopServerThread()  {
        if (serverThread == null) {
            return;
        }
        if (!serverThread.isAlive()) {
            serverThread = null;
            return;
        }

        Logger.info("Stopping uiautomator2 io.appium.uiautomator2.http io.appium.uiautomator2.server");
        serverThread.stopLooping();
        serverThread.interrupt();
        try {
            serverThread.join();
        } catch (InterruptedException ignored) {
        }
        serverThread = null;
        isStopServer = true;
    }

    private class HttpdThread extends Thread {

        private final AndroidServer server;
        private ServerInstrumentation instrumentation;
        private Looper looper;

        public HttpdThread(ServerInstrumentation instrumentation, int serverPort) {
            this.instrumentation = instrumentation;
            // Create the io.appium.uiautomator2.server but absolutely do not start it here
            server = new AndroidServer(serverPort);
        }

        @Override
        public void run() {
            Looper.prepare();
            looper = Looper.myLooper();
            startServer();
            Looper.loop();
        }

        @Override
        public void interrupt() {
            server.stop();
            super.interrupt();
        }

        public AndroidServer getServer() {
            return server;
        }

        private void startServer() {
            // Get a wake lock to stop the cpu going to sleep
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "UiAutomator2");
            try {
                wakeLock.acquire();
                getUiDevice().wakeUp();
            } catch (SecurityException e) {
                Logger.error("Security Exception", e);
            } catch (RemoteException e) {
                Logger.error("Remote Exception while waking up", e);
            }

            server.start();

            Logger.info("Started UiAutomator2 io.appium.uiautomator2.http io.appium.uiautomator2.server on port " + server.getPort());
        }

        public void stopLooping() {
            if (looper == null) {
                return;
            }
            looper.quit();
        }
    }
}
