package com.example.crosswalkdemo.view.xwalk;

import android.net.http.SslError;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;

import com.example.crosswalkdemo.listener.OnDebugMessageListener;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

/**
 * Copyright (c) 2017, WM-Apps
 * <p>
 * Created on 31.01.17.
 */
public class CustomCrosswalkResourceClient extends XWalkResourceClient {
    @Nullable private OnDebugMessageListener mListener;

    public CustomCrosswalkResourceClient(XWalkView view) {
        super(view);
    }

    @Override
    public void onLoadStarted(XWalkView view, String url) {
        super.onLoadStarted(view, url);
        postMessage("onLoadStarted() with url = [" + url + "]");
    }

    @Override
    public void onLoadFinished(XWalkView view, String url) {
        super.onLoadFinished(view, url);
        postMessage("onLoadFinished() with url = [" + url + "]");
    }

    // Override this method to show progress of loading the website
    @Override
    public void onProgressChanged(XWalkView view, int progressInPercent) {
        super.onProgressChanged(view, progressInPercent);
        postMessage("onProgressChanged() with " + progressInPercent + "%");
    }

    // Override this method to prevent showing loading errors in the XWalkView
    @Override
    public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
        super.onReceivedLoadError(view, errorCode, description, failingUrl);
        postMessage("onReceivedLoadError() with errorCode = [" + errorCode + "], description = [" + description +
                    "], failingUrl = [" + failingUrl + "]");
    }

    // Override this method to prevent showing certificate errors in the XWalkView
    @Override
    public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
        super.onReceivedSslError(view, callback, error);
        postMessage("onReceivedSslError() with error = [" + error + "]");
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
