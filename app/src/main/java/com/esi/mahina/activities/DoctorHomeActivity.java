package com.esi.mahina.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.esi.mahina.R;
import com.esi.mahina.utils.AnimationUtils;

public class DoctorHomeActivity extends BaseActivity {

    private MaterialCardView cardUSG;
    private MaterialCardView cardImmunization;
    private ImageButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
        playEntranceAnimations();
    }

    private void initViews() {
        cardUSG = findViewById(R.id.cardUSG);
        cardImmunization = findViewById(R.id.cardImmunization);
        btnSettings = findViewById(R.id.btnSettings);
    }

    private void setupClickListeners() {
        // Add touch scale effects
        AnimationUtils.addTouchScaleEffect(cardUSG);
        AnimationUtils.addTouchScaleEffect(cardImmunization);

        cardUSG.setOnClickListener(v -> {
            AnimationUtils.pulse(v);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(DoctorHomeActivity.this, DoctorUSGActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_fade_in, R.anim.fade_out);
            }, 100);
        });

        cardImmunization.setOnClickListener(v -> {
            AnimationUtils.pulse(v);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(DoctorHomeActivity.this, DoctorImmunizationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_fade_in, R.anim.fade_out);
            }, 100);
        });

        btnSettings.setOnClickListener(v -> {
            AnimationUtils.rotate(v, 0, 90, 200);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(DoctorHomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }, 150);
        });
    }

    private void playEntranceAnimations() {
        // Hide cards initially
        cardUSG.setAlpha(0f);
        cardUSG.setTranslationY(80f);
        cardImmunization.setAlpha(0f);
        cardImmunization.setTranslationY(80f);

        // Staggered animation for cards
        cardUSG.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .setStartDelay(200)
                .setInterpolator(new android.view.animation.DecelerateInterpolator(2f))
                .start();

        cardImmunization.animate()
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
