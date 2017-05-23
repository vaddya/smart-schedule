package com.vaddya.schedule.android.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.vaddya.schedule.android.R;

/**
 * com.vaddya.schedule.android.fragments at android
 *
 * @author vaddya
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.app_preferences);
    }

}