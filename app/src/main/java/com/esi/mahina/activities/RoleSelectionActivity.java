package com.esi.mahina.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.esi.mahina.R;
import com.esi.mahina.utils.AnimationUtils;

public class RoleSelectionActivity extends BaseActivity {

    private MaterialCardView cardDoctor;
    private MaterialCardView cardPatient;
    private TextView tvTitle;
    private TextView tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
        playEntranceAnimations();
    }

    private void initViews() {
        cardDoctor = findViewById(R.id.cardDoctor);
        cardPatient = findViewById(R.id.cardPatient);
        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
    }

    private void setupClickListeners() {
        // Add touch scale effects
        AnimationUtils.addTouchScaleEffect(cardDoctor);
        AnimationUtils.addTouchScaleEffect(cardPatient);

        cardDoctor.setOnClickListener(v -> {
            AnimationUtils.pulse(v);
            new Handler().postDelayed(() -> saveRoleAndNavigate(SplashActivity.ROLE_DOCTOR), 150);
        });

        cardPatient.setOnClickListener(v -> {
            AnimationUtils.pulse(v);
            new Handler().postDelayed(() -> saveRoleAndNavigate(SplashActivity.ROLE_PATIENT), 150);
        });
    }

    private void playEntranceAnimations() {
        // Hide views initially
        if (tvTitle != null) {
            tvTitle.setAlpha(0f);
            tvTitle.setTranslationY(-30f);
        }
        if (tvSubtitle != null) {
            tvSubtitle.setAlpha(0f);
        }
        cardDoctor.setAlpha(0f);
        cardDoctor.setTranslationY(60f);
        cardPatient.setAlpha(0f);
        cardPatient.setTranslationY(60f);

        // Animate title
        if (tvTitle != null) {
            tvTitle.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(500)
                    .setStartDelay(100)
                    .start();
        }

        // Animate subtitle
        if (tvSubtitle != null) {
            tvSubtitle.animate()
                    .alpha(1f)
                    .setDuration(400)
                    .setStartDelay(300)
                    .start();
        }

        // Animate cards with stagger
        cardDoctor.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(400)
                .setInterpolator(new android.view.animation.OvershootInterpolator(0.8f))
                .start();

        cardPatient.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(550)
                .setInterpolator(new android.view.animation.OvershootInterpolator(0.8f))
                .start();
    }

    private void saveRoleAndNavigate(String role) {
        // Save role to SharedPreferences
        SharedPreferences prefs = getSharedPreferences(SplashActivity.PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(SplashActivity.KEY_USER_ROLE, role).apply();

        // Navigate to appropriate home screen
        Intent intent;
        if (SplashActivity.ROLE_DOCTOR.equals(role)) {
            intent = new Intent(this, DoctorHomeActivity.class);
        } else {
            intent = new Intent(this, PatientHomeActivity.class);
        }

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
