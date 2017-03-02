package com.example.kif.kyrsu_11;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Kif on 29.12.2016.
 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }
}
