package com.example.crosswalkdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crosswalkdemo.R;
import com.example.crosswalkdemo.listener.OnDebugMessageListener;
import com.example.crosswalkdemo.view.CustomCrosswalkWebView;

/**
 * Copyright (c) 2017, WM-Apps
 * <p>
 * Created on 31.01.17.
 */
public class MainActivity extends AppCompatActivity implements OnDebugMessageListener {

    private SharedPreferences mPreferences;
    private EditText mAddressInput;
    private TextView mDebugOutput;
    private CustomCrosswalkWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mAddressInput = findViewById(R.id.main_address_edit_text);
        mDebugOutput = findViewById(R.id.main_debug_text);
        mWebView = findViewById(R.id.main_crosswalk_webview);

        mAddressInput.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ||
                 actionId == EditorInfo.IME_ACTION_GO)) {
                hideKeyboard();
                mWebView.loadUrl(v.getText().toString());
            }

            return false;
        });
        mDebugOutput.setMovementMethod(new ScrollingMovementMethod());
        mWebView.setOnDebugMessageListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        home(null);
        mWebView.setUserAgent(mWebView, mPreferences.getString(SettingsActivity.PREF_USER_AGENT, null));
        mDebugOutput.setVisibility(mPreferences.getBoolean("show_debug", false) ? View.VISIBLE : View.GONE);
    }

    public void goBack(View view) {
        mWebView.goBack();
    }

    public void goForward(View view) {
        mWebView.goForward();
    }

    public void reload(View view) {
        mWebView.reload();
    }

    public void home(View view) {
        String homePage = mPreferences.getString(SettingsActivity.PREF_HOME_PAGE, null);
        if (homePage != null && Patterns.WEB_URL.matcher(homePage).matches()) {
            mWebView.loadUrl(homePage);
        } else {
            homePage = "https://";
            mPreferences.edit().putString(SettingsActivity.PREF_HOME_PAGE, homePage).apply();
            Toast.makeText(this, homePage + " is not valid", Toast.LENGTH_SHORT).show();
        }

        mAddressInput.setText(homePage);
        mAddressInput.setSelection(mAddressInput.getText().length());
    }

    public void settings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void hideKeyboard() {
        // Check if no view has focus
        final View view = this.getCurrentFocus();
        if (view != null) {
            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onMessage(@Nullable String message) {
        if (!TextUtils.isEmpty(message)) {
            mDebugOutput.append(message);
            mDebugOutput.append("\n");
        }
    }
}
