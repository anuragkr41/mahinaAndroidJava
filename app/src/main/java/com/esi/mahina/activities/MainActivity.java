package com.esi.mahina.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.esi.mahina.R;
import com.esi.mahina.activities.doctorActivities.DoctorOptions;
import com.esi.mahina.activities.motherActivities.MotherActivity;
import com.esi.mahina.activities.patientActivities.PatientActivity;

public class MainActivity extends AppCompatActivity {
//    ActivityMainBinding binding;

    private final String DOCTOR = "doctor";
    private final String PATIENT = "patient";
    private final String MOTHER = "mother";
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        CardView cardNavigateDoctorScreen = findViewById(R.id.doctorCard);
        cardNavigateDoctorScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoctorOptions.class);
                startActivity(intent);
                userType = DOCTOR;
            }
        });

        CardView cardNavigatePatientScreen = findViewById(R.id.patientCard);
        cardNavigatePatientScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                startActivity(intent);
                userType = PATIENT;
            }
        });


        CardView cardNavigateMotherScreen = findViewById(R.id.motherCard);
        cardNavigateMotherScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MotherActivity.class);
                startActivity(intent);
            }
        });

//        this.userType = String.valueOf(findViewById(R.id.doctorCard));


    }


}
