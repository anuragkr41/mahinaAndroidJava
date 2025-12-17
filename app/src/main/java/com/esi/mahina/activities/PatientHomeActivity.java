package com.esi.mahina.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.esi.mahina.R;

public class PatientHomeActivity extends AppCompatActivity {

    public static final String KEY_LMP_DATE = "lmp_date";
    public static final String KEY_BABY_DOB = "baby_dob";
    public static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";

    private MaterialCardView cardPregnancy;
    private MaterialCardView cardVaccination;
    private ImageButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        cardPregnancy = findViewById(R.id.cardPregnancy);
        cardVaccination = findViewById(R.id.cardVaccination);
        btnSettings = findViewById(R.id.btnSettings);
    }

    private void setupClickListeners() {
        cardPregnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this, PatientUSGActivity.class);
                startActivity(intent);
            }
        });

        cardVaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this, PatientVaccinationActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
