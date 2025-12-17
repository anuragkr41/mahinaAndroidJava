package com.esi.mahina.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.card.MaterialCardView;
import com.esi.mahina.R;

public class DoctorHomeActivity extends AppCompatActivity {

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
    }

    private void initViews() {
        cardUSG = findViewById(R.id.cardUSG);
        cardImmunization = findViewById(R.id.cardImmunization);
        btnSettings = findViewById(R.id.btnSettings);
    }

    private void setupClickListeners() {
        cardUSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomeActivity.this, DoctorUSGActivity.class);
                startActivity(intent);
            }
        });

        cardImmunization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomeActivity.this, DoctorImmunizationActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
