package com.example.crosswalkdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.crosswalkdemo.listener.OnDebugMessageListener;
import com.example.crosswalkdemo.view.xwalk.CustomCrosswalkResourceClient;
import com.example.crosswalkdemo.view.xwalk.CustomCrosswalkUIClient;

import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkSettingsInternal;
import org.xwalk.core.internal.XWalkViewBridge;

import java.lang.reflect.Method;

/**
 * Copyright (c) 2017, WM-Apps
 * <p>
 * Created on 31.01.17.
 */
public class CustomCrosswalkWebView extends XWalkView {

    private static final String TAG = CustomCrosswalkWebView.class.getSimpleName();
    private CustomCrosswalkResourceClient mResourceClient;
    private CustomCrosswalkUIClient mUIClient;

    public CustomCrosswalkWebView(Context context) {
        super(context);

        init();
    }

    public CustomCrosswalkWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    /**
     * Inits all needed settings for the CustomCrosswalkWebView.
     */
    private void init() {
        // Enable remote debugging if in debug build
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);

        // Set custom resource client
        mResourceClient = new CustomCrosswalkResourceClient(this);
        setResourceClient(mResourceClient);
        // Set custom UI Client
        mUIClient = new CustomCrosswalkUIClient(this);
        setUIClient(mUIClient);
    }

    /**
     * Sets custom user agent for crosswalk view.
     *
     * @param view      Crosswalk view
     * @param userAgent Custom user agent
     */
    public void setUserAgent(@Nullable XWalkView view, @Nullable String userAgent) {
        try {
            if (view != null && !TextUtils.isEmpty(userAgent)) {
                final Method getBridgeMethod = XWalkView.class.getDeclaredMethod("getBridge");
                getBridgeMethod.setAccessible(true);
                final XWalkViewBridge xWalkViewBridge = (XWalkViewBridge) getBridgeMethod.invoke(view);
                final XWalkSettingsInternal xWalkSettingsInternal = xWalkViewBridge.getSettings();
                xWalkSettingsInternal.setUserAgentString(userAgent);
                xWalkSettingsInternal.setAllowUniversalAccessFromFileURLs(true);
            }
        } catch (Exception ignored) {

        }
    }

    public void setOnDebugMessageListener(@Nullable OnDebugMessageListener listener) {
        mResourceClient.setOnDebugMessageListener(listener);
        mUIClient.setOnDebugMessageListener(listener);
    }

    /**
     * Goes to previous page.
     */
    public void goBack() {
        if (getNavigationHistory().canGoBack()) {
            getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
        }
    }

    /**
     * Goes to next page.
     */
    public void goForward() {
        if (getNavigationHistory().canGoForward()) {
            getNavigationHistory().navigate(XWalkNavigationHistory.Direction.FORWARD, 1);
        }
    }

    /**
     * Reloads the website in normal mode.
     */
    public void reload() {
        reload(RELOAD_NORMAL);
    }

    @Override
    public void loadUrl(String url) {
        // Add http:// at first if the url does not contains
        if (url != null && !url.isEmpty() && !url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        super.loadUrl(url);
    }
}
