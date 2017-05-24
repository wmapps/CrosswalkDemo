package example.com.crosswalkdemo;

import android.net.http.SslError;
import android.webkit.ValueCallback;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

/**
 * Copyright (c) 2017, WM-Apps
 * <p>
 * Created on 31.01.17.
 */
public class CustomCrosswalkResourceClient extends XWalkResourceClient {
    public CustomCrosswalkResourceClient(XWalkView view) {
        super(view);
    }

    // Override this method to show progress of loading the website
    @Override
    public void onProgressChanged(XWalkView view, int progressInPercent) {
        super.onProgressChanged(view, progressInPercent);
    }

    // Override this method to prevent showing loading errors in the XWalkView
    @Override
    public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
        super.onReceivedLoadError(view, errorCode, description, failingUrl);
    }

    // Override this method to prevent showing certificate errors in the XWalkView
    @Override
    public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
        super.onReceivedSslError(view, callback, error);
    }
}
