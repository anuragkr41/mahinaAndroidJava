package com.esi.mahina.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.esi.mahina.R;
import com.esi.mahina.utils.AnimationUtils;

public class PatientHomeActivity extends BaseActivity {

    public static final String KEY_LMP_DATE = "lmp_date";
    public static final String KEY_BABY_DOB = "baby_dob";
    public static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String KEY_NOTIFICATION_PERMISSION_ASKED = "notification_permission_asked";

    private MaterialCardView cardPregnancy;
    private MaterialCardView cardVaccination;
    private ImageButton btnSettings;
    private View headerSection;

    // Permission request launcher
    private final ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                // Permission result handled - user can still use app without notifications
                if (isGranted) {
                    // Permission granted - notifications will work
                    getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE)
                            .edit()
                            .putBoolean(KEY_NOTIFICATIONS_ENABLED, true)
                            .apply();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
        playEntranceAnimations();

        // Request notification permission for Android 13+
        requestNotificationPermission();
    }

    private void requestNotificationPermission() {
        // Only needed for Android 13 (API 33) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if permission is already granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Check if we should show rationale (user previously denied)
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // Show explanation dialog
                    showNotificationPermissionRationale();
                } else {
                    // Request permission directly
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                }
            }
        }
    }

    private void showNotificationPermissionRationale() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Enable Notifications")
                .setMessage("Mahina needs notification permission to remind you about your USG appointments and baby vaccination schedules. Without this, you won't receive important reminders.")
                .setPositiveButton("Allow", (dialog, which) -> {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                })
                .setNegativeButton("Not Now", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    private void initViews() {
        cardPregnancy = findViewById(R.id.cardPregnancy);
        cardVaccination = findViewById(R.id.cardVaccination);
        btnSettings = findViewById(R.id.btnSettings);
    }

    private void setupClickListeners() {
        // Add touch scale effects for better feedback
        AnimationUtils.addTouchScaleEffect(cardPregnancy);
        AnimationUtils.addTouchScaleEffect(cardVaccination);

        cardPregnancy.setOnClickListener(v -> {
            AnimationUtils.pulse(v);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(PatientHomeActivity.this, PatientUSGActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_fade_in, R.anim.fade_out);
            }, 100);
        });

        cardVaccination.setOnClickListener(v -> {
            AnimationUtils.pulse(v);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(PatientHomeActivity.this, PatientVaccinationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_fade_in, R.anim.fade_out);
            }, 100);
        });

        btnSettings.setOnClickListener(v -> {
            AnimationUtils.rotate(v, 0, 90, 200);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(PatientHomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }, 150);
        });
    }

    private void playEntranceAnimations() {
        // Hide cards initially
        cardPregnancy.setAlpha(0f);
        cardPregnancy.setTranslationY(80f);
        cardVaccination.setAlpha(0f);
        cardVaccination.setTranslationY(80f);

        // Staggered animation for cards
        cardPregnancy.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(200)
                .setInterpolator(new android.view.animation.DecelerateInterpolator(2f))
                .start();

        cardVaccination.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(350)
                .setInterpolator(new android.view.animation.DecelerateInterpolator(2f))
                .start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reset settings button rotation
        if (btnSettings != null) {
            btnSettings.setRotation(0);
        }
    }
}
