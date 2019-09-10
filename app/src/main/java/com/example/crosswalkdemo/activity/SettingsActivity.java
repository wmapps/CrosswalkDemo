package com.example.crosswalkdemo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.crosswalkdemo.R;

/**
 * Copyright (c) 2018, WM-Apps
 * <p/>
 * Created on 21.11.18.
 */
public class SettingsActivity extends PreferenceActivity {

    public static final String PREF_HOME_PAGE = "home_page";
    public static final String PREF_USER_AGENT = "user_agent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();

            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
                final Preference preference = getPreferenceScreen().getPreference(i);
                updatePreference(sharedPreferences, preference);
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            final Preference preference = findPreference(key);
            updatePreference(sharedPreferences, preference);
        }

        private void updatePreference(@Nullable SharedPreferences sharedPreferences, @Nullable Preference preference) {
            if (sharedPreferences != null && preference != null) {
                final String key = preference.getKey();
                if (PREF_HOME_PAGE.equals(key)) {
                    String homePage = sharedPreferences.getString(PREF_HOME_PAGE, null);
                    if (TextUtils.isEmpty(homePage)) {
                        homePage = "https://";
                        sharedPreferences.edit().putString(PREF_HOME_PAGE, homePage).apply();
                    }
                    preference.setSummary(homePage);
                } else if (PREF_USER_AGENT.equals(key)) {
                    String userAgent = sharedPreferences.getString(PREF_USER_AGENT, null);
                    if (TextUtils.isEmpty(userAgent)) {
                        userAgent = getString(R.string.default_user_agent);
                        sharedPreferences.edit().putString(PREF_USER_AGENT, userAgent).apply();
                    }
                    preference.setSummary(userAgent);
                }
            }
        }
    }
}
