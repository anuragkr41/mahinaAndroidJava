package com.esi.mahina.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatDelegate;

import com.esi.mahina.R;

import java.util.Locale;

public class SplashActivity extends BaseActivity {

    public static final String PREFS_NAME = "MahinaPrefs";
    public static final String KEY_USER_ROLE = "user_role";
    public static final String ROLE_DOCTOR = "doctor";
    public static final String ROLE_PATIENT = "patient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Apply saved language before setting content view
        applySavedLanguage();

        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNextScreen();
            }
        }, 1500);
    }

    private void navigateToNextScreen() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userRole = prefs.getString(KEY_USER_ROLE, null);

        Intent intent;
        if (userRole == null) {
            // First time user - show role selection
            intent = new Intent(SplashActivity.this, RoleSelectionActivity.class);
        } else if (ROLE_DOCTOR.equals(userRole)) {
            intent = new Intent(SplashActivity.this, DoctorHomeActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, PatientHomeActivity.class);
        }

        startActivity(intent);
        finish();
    }

    private void applySavedLanguage() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String languageCode = prefs.getString(SettingsActivity.KEY_LANGUAGE, "en");

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}