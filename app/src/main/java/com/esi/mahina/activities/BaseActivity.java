package com.esi.mahina.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esi.mahina.R;

import java.util.Locale;

/**
 * Base activity that ensures locale is properly applied across all activities.
 * All activities should extend this class to maintain consistent language settings.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(updateLocale(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Apply locale again in onCreate to ensure it's set
        applyLocale();
    }

    /**
     * Start an activity with slide-in-right transition (forward navigation).
     */
    protected void startActivityWithTransition(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Finish activity with slide-out-right transition (back navigation).
     */
    protected void finishWithTransition() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Updates the locale for the given context.
     */
    private Context updateLocale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String languageCode = prefs.getString(SettingsActivity.KEY_LANGUAGE, "en");

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);

        return context.createConfigurationContext(config);
    }

    /**
     * Applies the saved locale to this activity's resources.
     */
    protected void applyLocale() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String languageCode = prefs.getString(SettingsActivity.KEY_LANGUAGE, "en");

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    /**
     * Gets the current locale based on saved language preference.
     */
    protected Locale getCurrentLocale() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String languageCode = prefs.getString(SettingsActivity.KEY_LANGUAGE, "en");
        return new Locale(languageCode);
    }
}
