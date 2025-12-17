package com.esi.mahina.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.esi.mahina.R;

public class RoleSelectionActivity extends AppCompatActivity {

    private MaterialCardView cardDoctor;
    private MaterialCardView cardPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        cardDoctor = findViewById(R.id.cardDoctor);
        cardPatient = findViewById(R.id.cardPatient);
    }

    private void setupClickListeners() {
        cardDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoleAndNavigate(SplashActivity.ROLE_DOCTOR);
            }
        });

        cardPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoleAndNavigate(SplashActivity.ROLE_PATIENT);
            }
        });
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
        finish();
    }
}
