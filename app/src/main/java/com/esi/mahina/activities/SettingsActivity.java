package com.esi.mahina.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.esi.mahina.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SettingsActivity extends BaseActivity {

    public static final String KEY_LANGUAGE = "app_language";

    private ImageButton btnBack;
    private TextView tvModeIcon;
    private TextView tvCurrentMode;
    private TextView tvModeDescription;
    private MaterialButton btnSwitchMode;

    private LinearLayout notificationSection;
    private MaterialSwitch switchNotifications;

    private LinearLayout savedDataSection;
    private TextView tvSavedLmp;
    private TextView tvSavedDob;
    private MaterialButton btnClearData;

    private MaterialCardView cardLanguage;
    private TextView tvCurrentLanguage;

    private boolean isPatientMode;

    private DateTimeFormatter getDisplayFormatter() {
        return DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault());
    }

    // Language codes and display names
    private static final String[] LANGUAGE_CODES = {"en", "hi", "bn", "ta", "te", "mr", "gu"};
    private static final String[] LANGUAGE_NAMES = {"English", "हिंदी", "বাংলা", "தமிழ்", "తెలుగు", "मराठी", "ગુજરાતી"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
        loadCurrentMode();
        loadCurrentLanguage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedData();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvModeIcon = findViewById(R.id.tvModeIcon);
        tvCurrentMode = findViewById(R.id.tvCurrentMode);
        tvModeDescription = findViewById(R.id.tvModeDescription);
        btnSwitchMode = findViewById(R.id.btnSwitchMode);

        notificationSection = findViewById(R.id.notificationSection);
        switchNotifications = findViewById(R.id.switchNotifications);

        savedDataSection = findViewById(R.id.savedDataSection);
        tvSavedLmp = findViewById(R.id.tvSavedLmp);
        tvSavedDob = findViewById(R.id.tvSavedDob);
        btnClearData = findViewById(R.id.btnClearData);

        cardLanguage = findViewById(R.id.cardLanguage);
        tvCurrentLanguage = findViewById(R.id.tvCurrentLanguage);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSwitchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSwitchModeConfirmation();
            }
        });

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putBoolean(PatientHomeActivity.KEY_NOTIFICATIONS_ENABLED, isChecked).apply();
            }
        });

        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearDataConfirmation();
            }
        });

        cardLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageSelector();
            }
        });
    }

    private void loadCurrentMode() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String userRole = prefs.getString(SplashActivity.KEY_USER_ROLE, SplashActivity.ROLE_DOCTOR);

        isPatientMode = SplashActivity.ROLE_PATIENT.equals(userRole);

        if (isPatientMode) {
            tvModeIcon.setText("\uD83E\uDD30"); // Pregnant woman emoji
            tvCurrentMode.setText("Patient Mode");
            tvModeDescription.setText("Data is saved with reminders");
            btnSwitchMode.setText("Switch to Doctor Mode");

            // Show patient-specific sections
            notificationSection.setVisibility(View.VISIBLE);
            savedDataSection.setVisibility(View.VISIBLE);

            // Load notification preference
            boolean notificationsEnabled = prefs.getBoolean(PatientHomeActivity.KEY_NOTIFICATIONS_ENABLED, true);
            switchNotifications.setChecked(notificationsEnabled);
        } else {
            tvModeIcon.setText("\uD83D\uDC68\u200D\u2695\uFE0F"); // Doctor emoji
            tvCurrentMode.setText("Doctor Mode");
            tvModeDescription.setText("Calculator mode - data is not saved");
            btnSwitchMode.setText("Switch to Patient Mode");

            // Hide patient-specific sections
            notificationSection.setVisibility(View.GONE);
            savedDataSection.setVisibility(View.GONE);
        }
    }

    private void loadCurrentLanguage() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String currentLang = prefs.getString(KEY_LANGUAGE, "en");

        for (int i = 0; i < LANGUAGE_CODES.length; i++) {
            if (LANGUAGE_CODES[i].equals(currentLang)) {
                tvCurrentLanguage.setText(LANGUAGE_NAMES[i]);
                break;
            }
        }
    }

    private void showLanguageSelector() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String currentLang = prefs.getString(KEY_LANGUAGE, "en");

        int selectedIndex = 0;
        for (int i = 0; i < LANGUAGE_CODES.length; i++) {
            if (LANGUAGE_CODES[i].equals(currentLang)) {
                selectedIndex = i;
                break;
            }
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.select_language)
                .setSingleChoiceItems(LANGUAGE_NAMES, selectedIndex, null)
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    setLanguage(LANGUAGE_CODES[selectedPosition]);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void setLanguage(String languageCode) {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply();

        // Update locale
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Restart the app to apply changes
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void loadSavedData() {
        if (!isPatientMode) return;

        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);

        // Load LMP
        String lmpDateStr = prefs.getString(PatientHomeActivity.KEY_LMP_DATE, null);
        if (lmpDateStr != null) {
            try {
                LocalDate lmpDate = LocalDate.parse(lmpDateStr);
                tvSavedLmp.setText(lmpDate.format(getDisplayFormatter()));
            } catch (Exception e) {
                tvSavedLmp.setText("Not set");
            }
        } else {
            tvSavedLmp.setText("Not set");
        }

        // Load Baby DOB
        String dobStr = prefs.getString(PatientHomeActivity.KEY_BABY_DOB, null);
        if (dobStr != null) {
            try {
                LocalDate dobDate = LocalDate.parse(dobStr);
                tvSavedDob.setText(dobDate.format(getDisplayFormatter()));
            } catch (Exception e) {
                tvSavedDob.setText("Not set");
            }
        } else {
            tvSavedDob.setText("Not set");
        }
    }

    private void showSwitchModeConfirmation() {
        String newMode = isPatientMode ? "Doctor" : "Patient";
        String message = isPatientMode
                ? "Switching to Doctor Mode will not save any data. Your saved data will remain but won't be accessible in Doctor Mode."
                : "Switching to Patient Mode allows you to save your LMP and baby's DOB with reminders.";

        new AlertDialog.Builder(this)
                .setTitle("Switch to " + newMode + " Mode?")
                .setMessage(message)
                .setPositiveButton("Switch", (dialog, which) -> switchMode())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void switchMode() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        String newRole = isPatientMode ? SplashActivity.ROLE_DOCTOR : SplashActivity.ROLE_PATIENT;
        prefs.edit().putString(SplashActivity.KEY_USER_ROLE, newRole).apply();

        // Navigate to appropriate home screen
        Intent intent;
        if (SplashActivity.ROLE_DOCTOR.equals(newRole)) {
            intent = new Intent(this, DoctorHomeActivity.class);
        } else {
            intent = new Intent(this, PatientHomeActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showClearDataConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Clear All Data?")
                .setMessage("This will permanently delete your saved LMP and baby's DOB. This action cannot be undone.")
                .setPositiveButton("Clear Data", (dialog, which) -> clearAllData())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void clearAllData() {
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PatientHomeActivity.KEY_LMP_DATE);
        editor.remove(PatientHomeActivity.KEY_BABY_DOB);
        editor.apply();

        // Refresh UI
        loadSavedData();
    }
}
