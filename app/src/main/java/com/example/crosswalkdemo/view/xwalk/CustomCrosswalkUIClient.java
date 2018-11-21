package com.example.crosswalkdemo.view.xwalk;

import android.support.annotation.Nullable;

import com.example.crosswalkdemo.listener.OnDebugMessageListener;

import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

/**
 * Copyright (c) 2017, WM-Apps
 * <p>
 * Created on 31.01.17.
 */
public class CustomCrosswalkUIClient extends XWalkUIClient {
    @SuppressWarnings("all") private static final String TAG = CustomCrosswalkUIClient.class.getSimpleName();
    @Nullable private OnDebugMessageListener mListener;

    public CustomCrosswalkUIClient(XWalkView view) {
        super(view);
    }

    // To prevent alerts from the websites
    @Override
    public boolean onJavascriptModalDialog(XWalkView view, JavascriptMessageType type, String url, String message,
                                           String defaultValue, XWalkJavascriptResult result) {
        postMessage("onJavascriptModalDialog() with type = [" + type + "], url = [" + url + "], message = [" + message +
                    "], defaultValue = [" + defaultValue + "], result = [" + result + "]");
        return super.onJavascriptModalDialog(view, type, url, message, defaultValue, result);
    }

    // To see console messages in the logcat
    @Override
    public boolean onConsoleMessage(XWalkView view, String message, int lineNumber, String sourceId,
                                    ConsoleMessageType messageType) {

        postMessage("onConsoleMessage() with message = [" + message + "], lineNumber = [" + lineNumber + "], sourceId = [" +
                    sourceId + "], messageType = [" + messageType + "]");

        return super.onConsoleMessage(view, message, lineNumber, sourceId, messageType);
    }

    // Calls if website starts to load
    @Override
    public void onPageLoadStarted(XWalkView view, String url) {
        super.onPageLoadStarted(view, url);

        postMessage("onPageLoadStarted() with url = [" + url + "]");
    }

    // Calls if website finishs to load
    @Override
    public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
        super.onPageLoadStopped(view, url, status);

        postMessage("onPageLoadStopped() with  url = [" + url + "], status = [" + status + "]");
    }

    @Override
    public boolean onJsAlert(XWalkView view, String url, String message, XWalkJavascriptResult result) {
        postMessage("onJsAlert() with url = [" + url + "], message = [" + message + "], result = [" + result + "]");
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(XWalkView view, String url, String message, XWalkJavascriptResult result) {
        postMessage(
                "onJsConfirm() with called with: url = [" + url + "], message = [" + message + "], result = [" + result + "]");
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(XWalkView view, String url, String message, String defaultValue, XWalkJavascriptResult result) {
        postMessage("onJsPrompt() with url = [" + url + "], message = [" + message + "], defaultValue = [" + defaultValue +
                    "], result = [" + result + "]");
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    public void setOnDebugMessageListener(@Nullable OnDebugMessageListener listener) {
        mListener = listener;
    }

    private void postMessage(@Nullable String message) {
        if (mListener != null) {
            mListener.onMessage(message);
        }
    }
}
